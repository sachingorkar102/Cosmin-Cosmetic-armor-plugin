package me.sachin.ceicommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.sachin.cosmin;
import me.sachin.utils.ConfigurationUtils;
import me.sachin.utils.ItemSettings;
import me.sachin.utils.PlayerUtils;
import me.sachin.utils.itemserializer;

public class cei extends Command {

    private static List<Player> cosminInventoryViewers = new ArrayList<>();

    private static HashMap<Player, List<ItemStack>> cosminInvContents = new HashMap<>();

    public static HashMap<Player, List<ItemStack>> getCosminInvContents() {
        return cosminInvContents;
    }

    public static List<Player> getCosminInventoryViewers() {
        return cosminInventoryViewers;
    }

    public static ItemStack itemCreator(String itemName) {
        ItemSettings setting = new ItemSettings(itemName);
        String display = setting.getDisplay();
        List<String> lore = setting.getLore();
        int model = setting.getModel();
        Material id = setting.getId();
        ItemStack item = new ItemStack(id);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(cosmin.getPlugin(), itemName);
        data.set(key, PersistentDataType.STRING, "");
        meta.setDisplayName(display);
        if (lore != null) {
            meta.setLore(lore);
        }
        if (model != 0) {
            meta.setCustomModelData(model);
        }
        item.setItemMeta(meta);
        return item;
    }

    public cei() {
        super("ceiCommand");
        setAliases(ConfigurationUtils.getCommandAlias());
        setDescription("&aopens cosmetic equipment gui");
        setPermission("cosmin.command.fakeequip");
        setUsage("/"+getAliases().get(0));

    }

    @Override
    public boolean execute(CommandSender sender, String arg, String[] args) {
        Player p = (Player) sender;     
        if(!p.hasPermission("cosmin.command.fakeequip")){
            p.sendMessage(ConfigurationUtils.noPermMessage());
            return true;
        }
        String title = ConfigurationUtils.getInvTitle();
        Inventory cosminInv = Bukkit.createInventory(p, 18, title);
        if(PlayerUtils.hasCosminInv(p)){
            ArrayList<ItemStack> list = itemserializer.getItems(p);
            Collections.replaceAll(list, list.get(0), cosmin.getBlockedSlotItem());
            ItemStack[] items = list.toArray(new ItemStack[0]);
            cosminInv.setContents(items);
            p.openInventory(cosminInv);
        }else{
            Inventory defaultInv = Bukkit.createInventory(p, 18,title);
            for(int i = 0;i<18;i++){
                if(Arrays.asList(0,1,9,10,7,8,16,17).contains(i)){
                    defaultInv.setItem(i, cosmin.getBlockedSlotItem());
                }else if(Arrays.asList(2,3,4,5,6).contains(i)){
                    defaultInv.setItem(i, cosmin.getEnabledSlotItem());
                }
                else{
                    defaultInv.setItem(i, null);
                }
            }
            p.openInventory(defaultInv);
        }
        getCosminInventoryViewers().add(p);
        return true;
    }
    
}
