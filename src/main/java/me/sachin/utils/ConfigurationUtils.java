package me.sachin.utils;

import java.util.Arrays;
import java.util.List;

import me.sachin.cosmin;
import net.md_5.bungee.api.ChatColor;

public class ConfigurationUtils {



    public static String getInvTitle() {
        try {
            return ChatColor.translateAlternateColorCodes('&', cosmin.getPlugin().getConfig().getString("Title"));
        } catch (Exception e) {
            return "";
        }
    }

    public static List<String> getCommandAlias(){
        List<String> backupList = Arrays.asList("cei"); 
        try {
            List<String> list = cosmin.getPlugin().getConfig().getStringList("Aliases");
            if(list.isEmpty() || list == null){
                return backupList;
            }
            return list;
        } catch (Exception e) {
            return backupList;
        }
    }

    public static String noPermMessage(){
        try {
            return ChatColor.translateAlternateColorCodes('&', cosmin.getPlugin().getConfig().getString("noPermissionMessage"));
        } catch (Exception e) {
            return ChatColor.translateAlternateColorCodes('&', "&cYou dont have permission to run the command");
        }
    }
    
}
