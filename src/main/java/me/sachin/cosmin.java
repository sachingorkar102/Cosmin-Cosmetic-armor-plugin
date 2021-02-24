package me.sachin;

import java.io.File;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.sachin.ceicommand.CM;
import me.sachin.ceicommand.cei;
import me.sachin.commands.CommandManager;
import me.sachin.commands.TabComplete;
import me.sachin.listners.PlayerDamageEvent;
import me.sachin.listners.onCosminInventoryClickEvent;
import me.sachin.listners.onInventoryCloseEvent;
import me.sachin.listners.onItemMendEvent;
import me.sachin.listners.onPlayerJoinQuitEvent;

public class cosmin extends JavaPlugin{


    private static cosmin plugin;
    private static ItemStack enabledSlotItem;
    private static ItemStack disabledSlotItem;
    private static ItemStack blockedSlotItem;

    public static cosmin getPlugin() {
        return plugin;
    }

    public static ItemStack getBlockedSlotItem() {
        return blockedSlotItem;
    }
    public static ItemStack getDisabledSlotItem() {
        return disabledSlotItem;
    }
    public static ItemStack getEnabledSlotItem() {
        return enabledSlotItem;
    }


    @Override
    public void onEnable() {
        plugin = this;
        reloadConfigs();
        this.getServer().getPluginManager().registerEvents(new onInventoryCloseEvent(), this);
        this.getServer().getPluginManager().registerEvents(new onCosminInventoryClickEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), this);
        this.getServer().getPluginManager().registerEvents(new onItemMendEvent(), this);
        this.getServer().getPluginManager().registerEvents(new onPlayerJoinQuitEvent(), this);
        getCommand("cosmin").setExecutor(new CommandManager());
        getCommand("cosmin").setTabCompleter(new TabComplete());



        // registering cei command
        CM manager = new CM("cei");
        manager.register(new cei());

        

    }


    public static void reloadConfigs(){
        getPlugin().saveDefaultConfig();
        getPlugin().reloadConfig();
        File rpConfig = new File(getPlugin().getDataFolder(), "config.yml.for_resource_pack");
        if(!rpConfig.exists()){
            getPlugin().saveResource("config.yml.for_resource_pack", false);
        }
        enabledSlotItem = cei.itemCreator("enabledSlotItem");
        disabledSlotItem = cei.itemCreator("disabledSlotItem");
        blockedSlotItem = cei.itemCreator("blockedSlotItem");
        System.out.println("configs reloaded");
    }
    




}
