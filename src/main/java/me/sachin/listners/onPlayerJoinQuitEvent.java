package me.sachin.listners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.sachin.cosmin;
import me.sachin.utils.PlayerUtils;

public class onPlayerJoinQuitEvent implements Listener{

    @EventHandler
    public void onplayerjoin(PlayerJoinEvent e){
        new BukkitRunnable(){
            @Override
            public void run() {
                Player p = e.getPlayer();
                if(!p.hasPermission("cosmin.command.fakeequip")) return;
                if(PlayerUtils.hasCosminInv(p)){
                    onInventoryCloseEvent.fakeArmor(p, false);
                }
            }
        }.runTaskLater(cosmin.getPlugin(), 30);
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
        }.runTaskLater(cosmin.getPlugin(), 20);
    }
}
