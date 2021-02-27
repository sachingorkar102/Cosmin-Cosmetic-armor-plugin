package me.sachin.listners;

import java.util.Arrays;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.sachin.Cosmin;
import me.sachin.ceicommand.FakeEquip;
import me.sachin.utils.ItemSettings;
import me.sachin.utils.PlayerUtils;

public class CosminInventoryClickEvent implements Listener{



    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        int clickedSlot = e.getSlot();
        if(FakeEquip.getCosminInventoryViewers().contains(p) || PlayerUtils.getCurrentlyEditedInventory().containsKey(p)){
            if(!Arrays.asList(11,12,13,14,15).contains(clickedSlot) && !(e.getClickedInventory() instanceof PlayerInventory)){
                e.setCancelled(true);
                
                if(Arrays.asList(2,3,4,5,6).contains(clickedSlot)){
                    ItemStack item = e.getCurrentItem();
                    ItemMeta meta = item.getItemMeta();
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    String itemId = data.get(new NamespacedKey(Cosmin.getPlugin(), "itemId"), PersistentDataType.STRING);
                    if(data.has(new NamespacedKey(Cosmin.getPlugin(),"enabledSlotItem"), PersistentDataType.STRING)){
                        ItemStack disabledItem = Cosmin.getDisabledSlotItem();
                        if(showToolTip(disabledItem)){
                            e.setCurrentItem(setToolTip(disabledItem, clickedSlot,itemId));
                            return;
                        }
                        e.setCurrentItem(Cosmin.getDisabledSlotItem());
                    }
                    else{
                        ItemStack enabledItem = Cosmin.getEnabledSlotItem();
                        if(showToolTip(enabledItem)){
                            e.setCurrentItem(setToolTip(enabledItem, clickedSlot,itemId));
                            return;
                        }
                        e.setCurrentItem(Cosmin.getEnabledSlotItem());

                    }
                }
            }
        }
    }

    public ItemStack setToolTip(ItemStack item, int slotId,String itemId){
        ItemMeta meta = item.getItemMeta();
        String tootipDisplay = "";
        switch(slotId){
            case 2:
                tootipDisplay = "[HELMET]";
                break;
            case 3:
                tootipDisplay = "[CHESTPLATE]";
                break;
            case 4:
                tootipDisplay = "[LEGGINGS]";
                break;
            case 5:
                tootipDisplay = "[BOOTS]";
                break;
            case 6:
                tootipDisplay = "[OFFHAND]";
                break;          
        }
        String newDisplay = "";
        String newId = "";
        // enabledSlotItem
        // disabledSlotItem
        if(itemId.equals("enabledSlotItem")){
            newId = "disabledSlotItem";
        }else{
            newId = "enabledSlotItem";
        }
        ItemSettings setting = new ItemSettings(newId);
        newDisplay = setting.getDisplay() + " " + tootipDisplay;
        meta.setDisplayName(newDisplay);
        item.setItemMeta(meta);
        return item;
    }

    public boolean showToolTip(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Cosmin.getPlugin(), "showTootip");
        String value = data.get(key, PersistentDataType.STRING);
        if(value.equals("true")){
            return true;
        }
        else{
            return false;
        }
    }
}
