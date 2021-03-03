package me.sachin.utils;

import java.util.Arrays;
import java.util.List;

import me.sachin.Cosmin;
import net.md_5.bungee.api.ChatColor;

public class ConfigurationUtils {



    public static String getInvTitle() {
        try {
            return ChatColor.translateAlternateColorCodes('&', Cosmin.getPlugin().getConfig().getString("Title"));
        } catch (Exception e) {
            return "";
        }
    }

    public static List<String> getCommandAlias(){
        List<String> backupList = Arrays.asList("cei"); 
        try {
            List<String> list = Cosmin.getPlugin().getConfig().getStringList("Aliases");
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
            return ChatColor.translateAlternateColorCodes('&', Cosmin.getPlugin().getConfig().getString("noPermissionMessage"));
        } catch (Exception e) {
            return ChatColor.translateAlternateColorCodes('&', "&cYou dont have permission to run the command");
        }
    }

    public static boolean keepArmorOnDeath(){
        try {
            return Cosmin.getPlugin().getConfig().getBoolean("keepArmorOnDeath");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean stopSounds(){
        try{
            return Cosmin.getPlugin().getConfig().getBoolean("stopSounds");
        
        }catch(Exception e){
            return true;
        }
    }


    
}
