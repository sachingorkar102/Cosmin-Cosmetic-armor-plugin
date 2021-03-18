package me.sachin.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sachin.utils.ConsoleUtils;
import net.md_5.bungee.api.ChatColor;

public class VersionCommand extends SubCommands {

    @Override
    public String getName() {
        return "version";
    }

    @Override
    public String getPermission() {
        return "cosmin.command.version";
    }

    @Override
    public String getUsage() {
        return "/cosmin version";
    }

    @Override
    public String getDescription() {
        return "displays plugin version";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if((sender instanceof Player)){
            Player p = (Player) sender;
            p.sendMessage(ChatColor.YELLOW+"Cosim version: "+ChatColor.GOLD+"1.2");
        }
        else{
            new ConsoleUtils().sendConsoleMessage('&', "&eCosmin version: &61.2");
        }

    }
    
}
