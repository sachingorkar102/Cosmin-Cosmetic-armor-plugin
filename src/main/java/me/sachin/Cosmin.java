package me.sachin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.sachin.ceicommand.CM;
import me.sachin.ceicommand.FakeEquip;
import me.sachin.commands.CommandManager;
import me.sachin.commands.TabComplete;
import me.sachin.listners.PlayerDamageEvent;
import me.sachin.listners.PlayerDieEvent;
import me.sachin.listners.CosminInventoryClickEvent;
import me.sachin.listners.OnInventoryCloseEvent;
import me.sachin.listners.ItemMendEvent;
import me.sachin.listners.PlayerJoinQuitEvent;
import me.sachin.utils.ConsoleUtils;



public class Cosmin extends JavaPlugin{


    private static Cosmin plugin;
    private static ItemStack enabledSlotItem;
    private static ItemStack disabledSlotItem;
    private static ItemStack blockedSlotItem;

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
        if(!getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
            getLogger().severe(getDescription().getName() + " requires ProtocolLib to run!");
            getServer().getPluginManager().disablePlugin(this);
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
        getCommand("cosmin").setExecutor(new CommandManager());
        getCommand("cosmin").setTabCompleter(new TabComplete());



        // registering cei command
        CM mng = new CM("cei");
        mng.register(new FakeEquip());



        

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
        new ConsoleUtils().sendConsoleMessage('&', "&aCosmin plugin reloaded");
    }
    




}
