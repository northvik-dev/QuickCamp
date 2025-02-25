package com.viktor.quickCamp.utils;

import com.viktor.quickCamp.QuickCamp;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class LocatedCamp {
    Player player;
    Location camplocation;
    String camplocationConverted;
    QuickCamp plugin;
    PersistentDataContainer data;
    NamespacedKey key;
    public LocatedCamp (Player player, Location camplocation, QuickCamp plugin){
        this.player = player;
        this.camplocation = camplocation;
        this.plugin = plugin;
    }
    public void setCamp(){
        key = new NamespacedKey(plugin, "camp");
        data = player.getPersistentDataContainer();
        data.set(key, PersistentDataType.STRING,getCampLocation());
        data.set(key,PersistentDataType.BOOLEAN, true);
    }

    public PersistentDataContainer getData() {
        data = player.getPersistentDataContainer();
        return data;
    }

    public NamespacedKey getKey(){
        key = new NamespacedKey(plugin, "camp");

        return key;
    }
    public String getCampLocation(){
        int x = (int) camplocation.getX();
        int y = (int) camplocation.getY();
        int z = (int) camplocation.getZ();
        camplocationConverted = x + " " + y + " " + z;
        return camplocationConverted;
    }
}
