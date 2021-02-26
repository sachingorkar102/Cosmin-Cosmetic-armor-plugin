package me.sachin.listners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import me.sachin.utils.PlayerUtils;

public class PlayerDamageEvent implements Listener{

    @EventHandler
    public void onplayerdamageevent(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(!p.hasPermission("cosmin.command.fakeequip")) return;
            if(PlayerUtils.hasCosminInv(p)){
                if(PlayerUtils.hasHashMap(p)){
                    OnInventoryCloseEvent.fakeEquipOnEvents(p);
                }else{
                    OnInventoryCloseEvent.fakeArmor(p, false);
                }
            }
        }
    }
    
}
