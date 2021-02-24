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

import me.sachin.cosmin;
import me.sachin.ceicommand.cei;

public class onCosminInventoryClickEvent implements Listener{



    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        int clickedSlot = e.getSlot();
        if(cei.getCosminInventoryViewers().contains(p)){
            if(!Arrays.asList(11,12,13,14,15).contains(clickedSlot) && !(e.getClickedInventory() instanceof PlayerInventory)){
                e.setCancelled(true);
                
                if(Arrays.asList(2,3,4,5,6).contains(clickedSlot)){
                    ItemStack item = e.getCurrentItem();
                    ItemMeta meta = item.getItemMeta();
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    if(data.has(new NamespacedKey(cosmin.getPlugin(), "enabledSlotItem"), PersistentDataType.STRING)){
                        e.setCurrentItem(cosmin.getDisabledSlotItem());
                        item.setItemMeta(meta);
                    }
                    else{
                        e.setCurrentItem(cosmin.getEnabledSlotItem());

                    }
                }
            }
        }
    }



}
