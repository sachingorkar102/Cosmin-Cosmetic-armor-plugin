package me.sachin.commands;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

import me.sachin.Cosmin;
import me.sachin.utils.ConfigurationUtils;
import me.sachin.utils.ConsoleUtils;
import me.sachin.utils.ItemSerializer;
import me.sachin.utils.PlayerUtils;
import net.md_5.bungee.api.ChatColor;

public class ResetPlayerDataCommand extends SubCommands{

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public String getPermission() {
        return "cosmin.command.reset";
    }

    @Override
    public String getUsage() {
        return "/cosmin reset [player_name]";
    }

    @Override
    public String getDescription() {
        return "resets the player's cosmin inventory data";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            new ConsoleUtils().sendConsoleMessage('&', "&cRequires Player to execute the command");
            return;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("cosmin.command.reset")) {
            p.sendMessage(ConfigurationUtils.noPermMessage());
            return;
        }
        if(args.length < 2) {
            p.sendMessage(ChatColor.RED+"Specify a correct player to execeute the command");
            return;
        }
        if(args[1].isEmpty() || !PlayerUtils.isOnline(args[1])){
            p.sendMessage(ChatColor.RED+"Specify a correct player to execeute the command");
            return;
        }
        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if(!PlayerUtils.hasCosminInv(targetPlayer)){
            p.sendMessage(ChatColor.GOLD+args[1]+ChatColor.YELLOW+" dosn't have a cosmin inventory");
            return;
        }
        PersistentDataContainer data = targetPlayer.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Cosmin.getPlugin(), ItemSerializer.getKeyName());
        data.remove(key);
        if(PlayerUtils.getBackupFakeEquipment().containsKey(targetPlayer)){
            PlayerUtils.getBackupFakeEquipment().remove(targetPlayer); 
        }
        
        p.sendMessage(ChatColor.YELLOW+" cosmin inventory data removed successfully from "+ChatColor.GOLD+args[1]);
        
    }
    
}
