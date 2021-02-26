package me.sachin.utils;


import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.sachin.Cosmin;

public class TempItemCreator {

    public ItemStack tempItem(){
        ItemStack tempItem = new ItemStack(Material.STICK);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName("This is temporary Item");
        tempItem.setItemMeta(meta);
        return tempItem;
    }

    public static ItemStack dummyItem(){
        ItemStack item = new ItemStack(Material.REDSTONE);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Cosmin.getPlugin(), "dummyItem");
        data.set(key, PersistentDataType.STRING, "dummyItem");
        item.setItemMeta(meta);
        return item;
    }

    
    
}
