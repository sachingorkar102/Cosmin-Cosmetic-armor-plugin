package me.sachin.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sachin.Cosmin;
import net.md_5.bungee.api.ChatColor;

public class ReloadCommand extends SubCommands {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "cosmin.command.reload";
    }

    @Override
    public String getUsage() {
        return "/cosmin reload";
    }

    @Override
    public String getDescription() {
        return "reloads the cosmin config";
    }


    @Override
    public void perform(CommandSender sender, String[] args) {
        Cosmin.reloadConfigs();
        if((sender instanceof Player)){
            Player p = (Player) sender;
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloaded &eCosmin &asuccessfully"));
        }
    }



}