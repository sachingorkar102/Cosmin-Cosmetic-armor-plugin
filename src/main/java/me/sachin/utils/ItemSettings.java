package me.sachin.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import me.sachin.Cosmin;
import net.md_5.bungee.api.ChatColor;

public class ItemSettings {

    private ConfigurationSection settings;
    private String itemName; 
    public static ConsoleUtils console = new ConsoleUtils();


    public ItemSettings(String itemName){
        this.itemName = itemName;
        try {
            settings = Cosmin.getPlugin().getConfig().getConfigurationSection("inventoryItems").getConfigurationSection(itemName);
        } catch (Exception e) {
            console.sendConsoleMessage('&', "Could not Load Item Settings");
        }
    }

    public Material getId(){
        try {
            String id = settings.getString("Id");
            return Material.matchMaterial(id);
        } catch (Exception e) {
            if(itemName.equals("enabledSlotItem")){
                return Material.LIME_STAINED_GLASS_PANE;
            }
            else if(itemName.equals("disabledSlotItem")){
                return Material.RED_STAINED_GLASS_PANE;
            }
            else if(itemName.equals("blockedSlotItem")){
                return Material.LIGHT_GRAY_STAINED_GLASS_PANE;
            }
            else{
                return Material.GREEN_STAINED_GLASS_PANE;
            }
        }
    }

    public int getModel(){
        try {
            return settings.getInt("Model");
        } catch (Exception e) {
            return 0;
        }
    }

    public String getDisplay(){
        try {
            String display = settings.getString("Display");
            if(display.equals("") || display.equals(null)){
                return " ";
            }
            return ChatColor.translateAlternateColorCodes('&', display);
            
        } catch (Exception e) {
            return " ";
        }
    }

    public List<String> getLore(){
        try {
            List<String> uncoloredLore = settings.getStringList("Lore");
            List<String> coloredLore = new ArrayList<>();
            if(uncoloredLore.isEmpty()){
                return null;
            }
            for (String string : uncoloredLore) {
                coloredLore.add(ChatColor.translateAlternateColorCodes('&', string));
            }
            return coloredLore;
        } catch (Exception e) {
            return null;
        }

    }

    public boolean showToolTip(){
        try {
            return settings.getBoolean("showToolTip");
        } catch (Exception e) {
            return false;
        }
    }






    
}
