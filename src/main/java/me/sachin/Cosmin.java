package me.sachin;

import java.io.File;
import java.nio.file.Files;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.sachin.ceicommand.CM;
import me.sachin.ceicommand.FakeEquip;
import me.sachin.commands.CommandManager;
import me.sachin.commands.TabComplete;
import me.sachin.listners.PlayerDamageEvent;
import me.sachin.listners.PlayerDieEvent;
import me.sachin.listners.CosminInventoryClickEvent;
import me.sachin.listners.GameModeChangeEvent;
import me.sachin.listners.ItemDamageEvent;
import me.sachin.listners.OnInventoryCloseEvent;
import me.sachin.listners.ItemMendEvent;
import me.sachin.listners.PlayerJoinQuitEvent;
import me.sachin.utils.ConsoleUtils;



public class Cosmin extends JavaPlugin{


    private static Cosmin plugin;
    private static ItemStack enabledSlotItem;
    private static ItemStack disabledSlotItem;
    private static ItemStack blockedSlotItem;

    private static String version;
    
    public static String getVersion() {
        return version;
    }

    public static Cosmin getPlugin() {
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
        ConsoleUtils console = new ConsoleUtils();
        if(!checkVersions()){
            Bukkit.getPluginManager().disablePlugin(this);
            console.sendConsoleMessage('&', "&cRunning uncompatible server version, disabling cosmin");
            return;
        }
        if(!getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
            console.sendConsoleMessage('&',"&cCosmin requires ProtocolLib to run! please install it from here &6https://www.spigotmc.org/resources/1997/");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        PluginManager pluginManager = this.getServer().getPluginManager();
        reloadConfigs();
        pluginManager.registerEvents(new OnInventoryCloseEvent(), this);
        pluginManager.registerEvents(new CosminInventoryClickEvent(), this);
        pluginManager.registerEvents(new PlayerDamageEvent(), this);
        pluginManager.registerEvents(new ItemMendEvent(), this);
        pluginManager.registerEvents(new PlayerDieEvent(), this);
        pluginManager.registerEvents(new PlayerJoinQuitEvent(), this);
        pluginManager.registerEvents(new GameModeChangeEvent(), this);
        pluginManager.registerEvents(new ItemDamageEvent(), this);
        getCommand("cosmin").setExecutor(new CommandManager());
        getCommand("cosmin").setTabCompleter(new TabComplete());



        // registering cei command
        CM mng = new CM("cei");
        mng.register(new FakeEquip());



        

    }


    public static boolean checkVersions(){
        String serverVersion = Cosmin.getPlugin().getServer().getClass().getPackage().getName();
        String Mainversion = serverVersion.substring(serverVersion.lastIndexOf(".") + 1);
        version = Mainversion;
        if(Mainversion.contains("v1_16") || Mainversion.contains("v1_15") || Mainversion.contains("v1_14")){
            return true;
        }else{
            return false;
        }
    }


    public static void reloadConfigs(){
        getPlugin().saveDefaultConfig();
        getPlugin().reloadConfig();
        File rpConfig = new File(getPlugin().getDataFolder(), "config.yml.for_resource_pack");
        if(!rpConfig.exists()){
            getPlugin().saveResource("config.yml.for_resource_pack", false);
        }
        enabledSlotItem = FakeEquip.itemCreator("enabledSlotItem");
        disabledSlotItem = FakeEquip.itemCreator("disabledSlotItem");
        blockedSlotItem = FakeEquip.itemCreator("blockedSlotItem");
        new ConsoleUtils().sendConsoleMessage('&', "Cosmin plugin loaded");
    }
    




}
