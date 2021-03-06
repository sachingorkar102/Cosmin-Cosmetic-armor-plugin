package me.sachin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.EnumWrappers.ItemSlot;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.sachin.Cosmin;

public class PlayerUtils {

    private static HashMap<Player,List<ItemStack>> backupFakeEquipment = new HashMap<>(); 
    private static HashMap<Player,List<Pair<ItemSlot,ItemStack>>> backuparmor = new HashMap<>();
    private static List<Player> openedInv = new ArrayList<>();
    private static HashMap<Player,Player> currentlyEditedInventory = new HashMap<>();

    public static HashMap<Player,Player> getCurrentlyEditedInventory() {
        return currentlyEditedInventory;
        
    }


    public static HashMap<Player, List<Pair<ItemSlot, ItemStack>>> getBackuparmor() {
        return backuparmor;
    }

    
    

    public static List<Player> getOnlinePlayerList() {
        List<Player> list = Bukkit.getOnlinePlayers().stream().collect(Collectors.toList());
        return list;
    }
    

    public static List<Player> getOpenedInv() {
        return openedInv;
    }




    public static HashMap<Player, List<ItemStack>> getBackupFakeEquipment() {
        return backupFakeEquipment;
    }


    public static boolean hasCosminInv(Player p){
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Cosmin.getPlugin(), ItemSerializer.getKeyName());
        return data.has(key, PersistentDataType.STRING);
    }

    public static boolean hasHashMap(Player p){
        return getBackuparmor().keySet().contains(p);
    }
    
    public static boolean isOnline(String name){
        List<String> nameList = new ArrayList<>();
        PlayerUtils.getOnlinePlayerList().forEach(player -> nameList.add(player.getName()));
        return nameList.contains(name);

    }
}
