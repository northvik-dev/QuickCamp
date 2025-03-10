package com.northvik.quickCamp.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BlocksLocationList {
    //blocks coordinates around player
    List<Location> placingArea = new ArrayList<>();
    //blocks under a player
    List<Location> basementBlockArea = new ArrayList<>();
    Location playerLoc;

    public void blockLocations(Player player) {
        playerLoc = player.getLocation();
        World world = playerLoc.getWorld();

        //adding coordinates of blocks
        if (world != null) {
            for (int x = -2; x < 3; x++) {
                for (int z = -2; z < 3; z++) {
                    Location zeroLvlBlock = playerLoc.clone().add(x, 0, z);
                    Location basementLvl = playerLoc.clone().add(x, -1, z);
                    placingArea.add(zeroLvlBlock);
                    basementBlockArea.add(basementLvl);
                }
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

}
