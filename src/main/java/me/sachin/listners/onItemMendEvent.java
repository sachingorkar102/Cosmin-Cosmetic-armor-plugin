package me.sachin.listners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemMendEvent;


import me.sachin.utils.PlayerUtils;

public class onItemMendEvent implements Listener{


    @EventHandler
    public void onitemmend(PlayerItemMendEvent e){
        Player p = e.getPlayer();
        if(!p.hasPermission("cosmin.command.fakeequip")) return;
        if(PlayerUtils.hasCosminInv(p)){
            if(PlayerUtils.hasHashMap(p)){
                onInventoryCloseEvent.fakeEquipOnEvents(p);
            }else{
                onInventoryCloseEvent.fakeArmor(p, true);
            }
        }
    }
    
}
