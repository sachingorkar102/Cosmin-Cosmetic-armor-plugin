package me.sachin.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sachin.ceicommand.CM;
import net.md_5.bungee.api.ChatColor;

public class HelpCommand extends SubCommands {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "cosmin.command.help";
    }

    @Override
    public String getUsage() {
        return "/cosmin help";
    }

    @Override
    public String getDescription() {
        return "shows list of all commands in cosmin plugin";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        List<SubCommands> commands = new CommandManager().getSubcommands();
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.translateAlternateColorCodes('&', "&2-----------==&aCosmin&2==-----------")+"\n");
        for (SubCommands sub : commands) {
            builder.append(ChatColor.translateAlternateColorCodes('&', "&f"+sub.getUsage()+"&a: "+sub.getDescription()+"\n&e"+sub.getPermission())+"\n");
        }
        CM mng = new CM("ceiCommand");
        builder.append(ChatColor.translateAlternateColorCodes('&',"&f"+mng.getUsage()+"&a: "+mng.getDescription()+"\n&e"+mng.getPermission())+"\n");
        builder.append(ChatColor.translateAlternateColorCodes('&', "&2--------------------------------"));
        p.sendMessage(builder.toString());

    }

}