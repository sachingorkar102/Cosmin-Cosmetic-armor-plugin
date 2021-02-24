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
        if(!(sender instanceof Player)){
            console.sendConsoleMessage('&', "&cRequires a player to execute command");
            return true;
        }
        if(args.length > 0){
            Player p = (Player) sender;
            for (int i = 0; i < getSubcommands().size(); i++){
                if(!p.hasPermission(getSubcommands().get(i).getPermission())){
                    p.sendMessage(ConfigurationUtils.noPermMessage());
                    return true;
                }
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                    getSubcommands().get(i).perform(sender, args);
                }
            }
        }
        else{
            //TODO send help message
        }
        
        return true;
    }

    public ArrayList<SubCommands> getSubcommands(){
        return subcommands;
    }

    
}
