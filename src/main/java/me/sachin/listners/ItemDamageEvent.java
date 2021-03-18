package me.sachin.listners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import me.sachin.utils.PlayerUtils;

public class ItemDamageEvent implements Listener{

    @EventHandler
    public void onItemBreak(PlayerItemDamageEvent e){
        Player p = e.getPlayer();
        if(!p.hasPermission("cosmin.command.fakeequip")) return;
        if(PlayerUtils.hasCosminInv(p) && e.getItem().getType().name().equals("ELYTRA")){
            if(PlayerUtils.hasHashMap(p)){
                OnInventoryCloseEvent.fakeEquipOnEvents(p);
            }else{
                OnInventoryCloseEvent.fakeArmor(p, false);
            }
        }
    }

    
}
