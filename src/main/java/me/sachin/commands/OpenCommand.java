package me.sachin.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.sachin.Cosmin;
import me.sachin.utils.ConfigurationUtils;
import me.sachin.utils.ConsoleUtils;
import me.sachin.utils.ItemSerializer;
import me.sachin.utils.PlayerUtils;
import net.md_5.bungee.api.ChatColor;

public class OpenCommand extends SubCommands {

    @Override
    public String getName() {
        return "open";
    }

    @Override
    public String getPermission() {
        return "cosmin.command.open";
    }

    @Override
    public String getUsage() {
        return "/cosmin open [player_name]";
    }

    @Override
    public String getDescription() {
        return "opens the specified player's cosmin inventory";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            new ConsoleUtils().sendConsoleMessage('&', "&cRequires Player to execute the command");
            return;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("cosmin.command.open")) {
            p.sendMessage(ConfigurationUtils.noPermMessage());
            return;
        }
        if(args.length < 2) {
            p.sendMessage(ChatColor.RED+"Specify a correct player to execeute the command");
            return;
        }
        if(args[1].isEmpty() || !isOnline(args[1])){
            p.sendMessage(ChatColor.RED+"Specify a correct player to execeute the command");
            return;
        }
        Player controlledPlayer = Bukkit.getPlayer(args[1]);
        String title = ConfigurationUtils.getInvTitle();
        Inventory cosminInv = Bukkit.createInventory(p, 18, title);
        p.sendMessage(ChatColor.YELLOW + "editing " + ChatColor.GOLD+args[1] + "'s " + ChatColor.YELLOW+ "cosmin Inventory");
        if (PlayerUtils.hasCosminInv(controlledPlayer)) {
            ArrayList<ItemStack> list = ItemSerializer.getItems(controlledPlayer);
            ItemStack blockedItem = Cosmin.getBlockedSlotItem();
            Collections.replaceAll(list,list.get(0),blockedItem);
            ItemStack[] items = list.toArray(new ItemStack[0]);
            cosminInv.setContents(items);
            p.openInventory(cosminInv);
        }else{
            Inventory defaultInv = Bukkit.createInventory(p, 18,title);
            for(int i = 0;i<18;i++){
                if(Arrays.asList(0,1,9,10,7,8,16,17).contains(i)){
                    defaultInv.setItem(i, Cosmin.getBlockedSlotItem());
                }else if(Arrays.asList(2,3,4,5,6).contains(i)){
                    defaultInv.setItem(i, Cosmin.getDisabledSlotItem());
                }
                else{
                    defaultInv.setItem(i, null);
                }
            }
            p.openInventory(defaultInv);
        }
        PlayerUtils.getCurrentlyEditedInventory().put(p,controlledPlayer);
        
    }

    public boolean isOnline(String name){
        List<String> nameList = new ArrayList<>();
        PlayerUtils.getOnlinePlayerList().forEach(player -> nameList.add(player.getName()));
        return nameList.contains(name);

    }
    
}
