package me.sachin.utils;

import me.sachin.cosmin;
import net.md_5.bungee.api.ChatColor;

public class ConsoleUtils {



    public void sendConsoleMessage(char sym,String Message){
        cosmin.getPlugin().getServer().getConsoleSender()
                .sendMessage(ChatColor.translateAlternateColorCodes(sym,Message));
    }
    
}
