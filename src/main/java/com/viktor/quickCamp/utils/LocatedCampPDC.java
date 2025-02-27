package com.viktor.quickCamp.utils;

import com.viktor.quickCamp.QuickCamp;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class LocatedCampPDC {
    Player player;
    Location RawLocation;
    String campLocation;
    QuickCamp plugin;
    PersistentDataContainer data;
    NamespacedKey keyIsCamping;
    NamespacedKey keyWorld;
    NamespacedKey keyX;
    NamespacedKey keyY;
    NamespacedKey keyZ;
    public LocatedCampPDC(Player player, Location RawLocation, QuickCamp plugin){
        this.player = player;
        this.RawLocation = RawLocation;
        this.plugin = plugin;

        this.keyIsCamping = new NamespacedKey(plugin, "camp");
        this.keyWorld = new NamespacedKey(plugin,"keyWorld");
        this.keyX = new NamespacedKey(plugin,"keyX");
        this.keyY = new NamespacedKey(plugin,"keyY");
        this.keyZ = new NamespacedKey(plugin,"keyZ");
        this.data = player.getPersistentDataContainer();

    }
    public void setCamp(){
        data.set(keyWorld, PersistentDataType.STRING, RawLocation.getWorld().getName());
        data.set(keyX,PersistentDataType.INTEGER, (int) RawLocation.getX());
        data.set(keyY,PersistentDataType.INTEGER, (int) RawLocation.getY());
        data.set(keyZ,PersistentDataType.INTEGER, (int) RawLocation.getZ());
        data.set(keyIsCamping,PersistentDataType.BOOLEAN, true);
    }

    public String getCampLocation(){
        campLocation = data.get(keyX,PersistentDataType.INTEGER) + " " +
                data.get(keyY,PersistentDataType.INTEGER) + " " +
                data.get(keyZ,PersistentDataType.INTEGER);
        return campLocation;
    }

    public boolean isCamping(){

        return data.has(keyIsCamping,PersistentDataType.BOOLEAN) && Boolean.TRUE.equals(data.get(keyIsCamping, PersistentDataType.BOOLEAN));

    }
    public void removeCamping(){
        data.remove(keyIsCamping);
        data.remove(keyWorld);
        data.remove(keyX);
        data.remove(keyY);
        data.remove(keyZ);
    }

}
