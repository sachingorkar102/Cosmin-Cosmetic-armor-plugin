package me.sachin.commands;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;



public class TabComplete implements TabCompleter {


    CommandManager manager = new CommandManager();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> arguments = new ArrayList<>();
        if(args.length == 1){
            manager.getSubcommands().forEach(s -> arguments.add(s.getName()));
            List<String> betterArgs = new ArrayList<>();
            arguments.forEach(s -> {
                if(s.startsWith(args[0])){
                    betterArgs.add(s);
                }
            });
            if(betterArgs.isEmpty()){
                return arguments;
            }
            else{
                return betterArgs;
            }
        }
        return null;
    }



}    

