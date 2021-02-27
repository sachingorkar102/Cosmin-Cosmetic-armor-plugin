package me.sachin.listners;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.MinecraftKey;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.EnumWrappers.ItemSlot;

import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import me.sachin.Cosmin;
import me.sachin.ceicommand.FakeEquip;
import me.sachin.utils.PlayerUtils;
import net.md_5.bungee.api.ChatColor;
import me.sachin.utils.ItemSerializer;

public class OnInventoryCloseEvent implements Listener {

    @EventHandler
    public void event(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        List<ItemStack> list = Arrays.asList(e.getInventory().getContents());
        if (!p.hasPermission("cosmin.command.fakeequip") || !p.hasPermission("cosmin.command.open")){
            return;
        }
        else if(PlayerUtils.getCurrentlyEditedInventory().containsKey(p)){
            Player controlledPlayer = PlayerUtils.getCurrentlyEditedInventory().get(p);
            PlayerUtils.getCurrentlyEditedInventory().remove(p);
            ItemSerializer.storeItems(list,controlledPlayer);
            fakeArmor(controlledPlayer, false);
            p.sendMessage(ChatColor.GOLD+controlledPlayer.getName()+"'s "+ChatColor.YELLOW+"cosmin inventory edited successfully");
            return;
        }
        else if (e.getInventory().getType().equals(InventoryType.CRAFTING) && !p.getGameMode().equals(GameMode.CREATIVE)) {
            if (PlayerUtils.hasCosminInv(p) && !PlayerUtils.getCurrentlyEditedInventory().containsValue(p)) {
                fakeArmor(p, false);
            }
            return;
        }
        else if (FakeEquip.getCosminInventoryViewers().contains(p)) {
            FakeEquip.getCosminInventoryViewers().remove(p);
            ItemSerializer.storeItems(list, p);
            fakeArmor(p, false);

        }

    }

    public static void stopSounds(Player p) {
        new BukkitRunnable(){
            @Override
            public void run() {
                ProtocolManager manager = ProtocolLibrary.getProtocolManager();
                PacketContainer packet = manager.createPacket(PacketType.Play.Server.STOP_SOUND);
                packet.getMinecraftKeys().write(0, new MinecraftKey("minecraft","item.armor.equip_netherite"));
                try {
                    manager.sendServerPacket(p, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }   
        }.runTaskLater(Cosmin.getPlugin(), 1);
    }


    public static void fakeArmor(Player p,boolean ismending){
        List<ItemStack> itemlist = ItemSerializer.getItems(p);
        List<Pair<ItemSlot,ItemStack>> pairs = new ArrayList<>();
        for(int i =2;i<7;i++){
            // 2  3  4  5  6
            // 11 12 13 14 15
            // 39 38 37 36 45
            int slotId = i+9;
            int orignalSlots = 0;
            
            ItemStack toggleItem = itemlist.get(i);
            ItemSlot slot = null;
            ItemStack armor = itemlist.get(slotId);

            switch(slotId){
                case 11:
                    slot = ItemSlot.HEAD;
                    orignalSlots = 39;
                    break;
                case 12:
                    slot = ItemSlot.CHEST;
                    orignalSlots = 38;
                    if(!armor.getType().name().endsWith("CHESTPLATE") && !armor.getType().name().equals("AIR")){
                        continue;
                    }
                    break;
                case 13:
                    slot = ItemSlot.LEGS; 
                    orignalSlots = 37;  
                    if(!armor.getType().name().endsWith("LEGGINGS") && !armor.getType().name().equals("AIR")){
                        continue;
                    }
                    break;
                case 14:
                    slot = ItemSlot.FEET;
                    orignalSlots = 36;
                    if(!armor.getType().name().endsWith("BOOTS") && !armor.getType().name().equals("AIR")){
                        continue;
                    }
                    break;
                case 15:
                    slot = ItemSlot.OFFHAND;
                    orignalSlots = 40;
                    if(ismending){
                        continue;
                    }
                    break;             
            }
            if(isEnabled(toggleItem)){
                pairs.add(new Pair<>(slot,armor));
            }
            else{
                pairs.add(new Pair<>(slot,p.getInventory().getItem(orignalSlots)));
            }
        }
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, p.getEntityId());
        packet.getSlotStackPairLists().write(0, pairs);
        PlayerUtils.getBackuparmor().put(p, pairs);
        
        PlayerUtils.getOnlinePlayerList().forEach(player -> {
            try {
                manager.sendServerPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static void fakeEquipOnEvents(Player p){
        new BukkitRunnable(){
            @Override
            public void run() {
                ProtocolManager manager = ProtocolLibrary.getProtocolManager();
                PacketContainer packet = manager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
                packet.getIntegers().write(0, p.getEntityId());
                List<Pair<ItemSlot,ItemStack>> pair = PlayerUtils.getBackuparmor().get(p);
                packet.getSlotStackPairLists().write(0, pair);
                PlayerUtils.getOnlinePlayerList().forEach(player -> {
                    try {
                        manager.sendServerPacket(player, packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            };
        }.runTaskLater(Cosmin.getPlugin(), 2);
        
    }


    public static boolean isEnabled(ItemStack toggleItem){
        ItemMeta meta = toggleItem.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Cosmin.getPlugin(),"enabledSlotItem");
        if(data.has(key, PersistentDataType.STRING)){
            return true;
        }else{
            return false;
        }
    }



// for gamemode change event
    public static void equipOrignalArmor(Player p){
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        List<Pair<ItemSlot,ItemStack>> orignalPairs = new ArrayList<>();
        PlayerInventory playeInventory = p.getInventory();
        packet.getIntegers().write(0, p.getEntityId());
        orignalPairs.add(new Pair<ItemSlot,ItemStack>(ItemSlot.HEAD, playeInventory.getHelmet()));
        orignalPairs.add(new Pair<ItemSlot,ItemStack>(ItemSlot.CHEST, playeInventory.getChestplate()));
        orignalPairs.add(new Pair<ItemSlot,ItemStack>(ItemSlot.LEGS, playeInventory.getLeggings()));
        orignalPairs.add(new Pair<ItemSlot,ItemStack>(ItemSlot.FEET, playeInventory.getBoots()));
        orignalPairs.add(new Pair<ItemSlot,ItemStack>(ItemSlot.OFFHAND, playeInventory.getItemInOffHand()));
        packet.getSlotStackPairLists().write(0, orignalPairs);
        PlayerUtils.getOnlinePlayerList().forEach(player -> {
            try {
                manager.sendServerPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // for(int i=0;i<5;i++){
        //     PacketContainer packet = manager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        //     PlayerUtils.getOnlinePlayerList(p).forEach(player -> {
        //         try {
        //             manager.sendServerPacket(player, packet);
        //         } catch (InvocationTargetException e) {
        //             e.printStackTrace();
        //         }
        //     });
        // }
    }
}
