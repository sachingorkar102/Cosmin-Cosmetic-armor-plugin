package me.sachin.listners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import me.sachin.utils.PlayerUtils;

public class GameModeChangeEvent implements Listener{


    @EventHandler
    public void onEvent(PlayerGameModeChangeEvent e){
        Player p = e.getPlayer();
        if(!PlayerUtils.hasCosminInv(p)) return;
        if(e.getNewGameMode().equals(GameMode.CREATIVE)){
            OnInventoryCloseEvent.equipOrignalArmor(p);
        }
        else{
            OnInventoryCloseEvent.fakeArmor(p, false);
        }
    }
}