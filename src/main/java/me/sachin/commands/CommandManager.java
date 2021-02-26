package me.sachin.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sachin.utils.ConfigurationUtils;
import me.sachin.utils.ConsoleUtils;

public class CommandManager implements CommandExecutor {


    private ArrayList<SubCommands> subcommands = new ArrayList<>();
    
    public ConsoleUtils console = new ConsoleUtils();

    public CommandManager(){
        subcommands.add(new HelpCommand());
        subcommands.add(new ReloadCommand());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0){
            for (int i = 0; i < getSubcommands().size(); i++){
                if(args[0].equals(getSubcommands().get(i).getName())){
                    if((sender instanceof Player)){
                        Player p = (Player) sender;
                        SubCommands sub = getSubcommands().get(i);
                        if(p.hasPermission(sub.getPermission())){
                            sub.perform(sender, args);
                        }else{
                            p.sendMessage(ConfigurationUtils.noPermMessage());
                        }
                    }
                    else{
                        getSubcommands().get(i).perform(sender, args);
                    }
                    
                }
                // if(!getSubcommands().get(i).requiresPlayer() && args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                // }else{
                // }
            }
        }
        else{
        }
        
        return true;
    }

    public ArrayList<SubCommands> getSubcommands(){
        return subcommands;
    }

    
}
