package me.sachin.listners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.sachin.Cosmin;
import me.sachin.utils.PlayerUtils;

public class PlayerJoinQuitEvent implements Listener{

    @EventHandler
    public void onplayerjoin(PlayerJoinEvent e){
        new BukkitRunnable(){
            @Override
            public void run() {
                Player p = e.getPlayer();
                if(!p.hasPermission("cosmin.command.fakeequip")) return;
                if(PlayerUtils.hasCosminInv(p)){
                    OnInventoryCloseEvent.fakeArmor(p, false);
                }
            }
        }.runTaskLater(Cosmin.getPlugin(), 30);
    }


    @EventHandler
    public void onplayerleave(PlayerQuitEvent e){
        new BukkitRunnable(){
            @Override
            public void run() {
                Player p = e.getPlayer();
                if(PlayerUtils.hasCosminInv(p)){
                    PlayerUtils.getBackuparmor().remove(p);
                } 
            }
        }.runTaskLater(Cosmin.getPlugin(), 20);
    }
}
