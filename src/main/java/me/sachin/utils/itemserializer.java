package me.sachin.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import me.sachin.cosmin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class itemserializer {
    public static ItemStack tempItem = new tempItemCreator().tempItem();

    public static String getKeyName() {
        return "20";
    }

    public static void storeItems(List<ItemStack> items, Player p){
        

        PersistentDataContainer data = p.getPersistentDataContainer();

        if (items.size() == 0){
            data.set(new NamespacedKey(cosmin.getPlugin(), getKeyName()), PersistentDataType.STRING, "");
        }else{

            try{

                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);

                os.writeInt(items.size());
                for (ItemStack i : items) {
                    if(i == null){
                        i = tempItem;
                    }
                    os.writeObject(i);
                }

                os.flush();

                byte[] rawData = io.toByteArray();

                String encodedData = Base64.getEncoder().encodeToString(rawData);

                data.set(new NamespacedKey(cosmin.getPlugin(), getKeyName()), PersistentDataType.STRING, encodedData);

                os.close();

            }catch (IOException ex){
                System.out.println(ex);
            }

        }

    }

    public static ArrayList<ItemStack> getItems(Player p){

        PersistentDataContainer data = p.getPersistentDataContainer();

        ArrayList<ItemStack> items = new ArrayList<>();
        ArrayList<ItemStack> pitems = new ArrayList<>();

        String encodedItems = data.get(new NamespacedKey(cosmin.getPlugin(), getKeyName()), PersistentDataType.STRING);
        // System.out.println(encodedItems);
        byte[] rawData = Base64.getDecoder().decode(encodedItems);

        try{

            ByteArrayInputStream io = new ByteArrayInputStream(rawData);
            BukkitObjectInputStream in = new BukkitObjectInputStream(io);

            int itemsCount = in.readInt();
//                ItemStack mainitem;
            for (int i = 0; i < itemsCount; i++){
                items.add((ItemStack) in.readObject());
            }
            ItemStack newi =new ItemStack(Material.AIR);
            items.forEach(i -> {
                if(i.isSimilar(tempItem)){
                    pitems.add(newi);
                }
                else{
                    pitems.add(i);
                }
            });

            in.close();

        }catch (IOException | ClassNotFoundException ex){
            System.out.println(ex);
        }
        return pitems;
    }

}
