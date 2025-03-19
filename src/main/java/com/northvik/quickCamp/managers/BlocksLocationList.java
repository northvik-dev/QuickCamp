package com.northvik.quickCamp.managers;

import com.northvik.quickCamp.QuickCamp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlocksLocationList {
    //blocks coordinates around player
    List<Location> placingArea = new ArrayList<>();
    //blocks under a player
    List<Location> basementBlockArea = new ArrayList<>();
    Location playerLoc;
    ConfigsInitialize ci;

    int campSize;
    int min = 0;
    int max = 0;

    public void blockLocations(Player player, QuickCamp plugin, int campSize) {
        playerLoc = player.getLocation();
        World world = playerLoc.getWorld();
        ci = new ConfigsInitialize(plugin);
        this.campSize = campSize;
        if (world != null) {
            addLocations(playerLoc, campSize);

        }
    }

    public void addLocations(Location location, int campSize){

        switch (campSize){
            case 1 -> {
                min = -1;
                max = 2;
            }
            case 2 -> {
                min = -2;
                max = 2;
            }
            case 3 -> {
                min = -2;
                max = 3;
            }
            case 4 -> {
                min = -3;
                max = 3;
            }
        }

        for (int x = min; x < max; x++) {
            for (int z = min; z < max; z++) {
                Location zeroLvlBlock = location.clone().add(x, 0, z);
                Location basementLvl = location.clone().add(x, -1, z);
                placingArea.add(zeroLvlBlock);
                basementBlockArea.add(basementLvl);
            }
        }
    }

    public Location getCampLocation(){
        return playerLoc;
    }

    public List<Location> getPlacingAreaList (){
        return placingArea.reversed();
    }

    public List<Location> getBasementAreaList (){
        return basementBlockArea;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
}
