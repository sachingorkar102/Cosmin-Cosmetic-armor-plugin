package me.sachin.listners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemMendEvent;


import me.sachin.utils.PlayerUtils;

public class ItemMendEvent implements Listener{


    @EventHandler
    public void onitemmend(PlayerItemMendEvent e){
        Player p = e.getPlayer();
        if(!p.hasPermission("cosmin.command.fakeequip")) return;
        if(PlayerUtils.hasCosminInv(p)){
            if(PlayerUtils.hasHashMap(p)){
                OnInventoryCloseEvent.fakeEquipOnEvents(p);
            }else{
                OnInventoryCloseEvent.fakeArmor(p, true);
            }
        }
    }
    
}
