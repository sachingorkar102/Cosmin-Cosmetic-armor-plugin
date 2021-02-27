package me.sachin.listners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.sachin.utils.ConfigurationUtils;
import me.sachin.utils.ItemSerializer;
import me.sachin.utils.PlayerUtils;

public class PlayerDieEvent implements Listener{

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        if(PlayerUtils.hasCosminInv(p) && !e.getKeepInventory() && !ConfigurationUtils.keepArmorOnDeath()){
            List<ItemStack> itemlist = ItemSerializer.getItems(p);
            for(int i =11;i<16;i++){
                e.getDrops().add(itemlist.get(i));
                itemlist.set(i, null);
            }
            ItemSerializer.storeItems(itemlist, p);
        }
    }
}
