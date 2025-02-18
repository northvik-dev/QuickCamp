package com.viktor.quickCamp.utils;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class SafeCheck {

    List<Location> base;
    List<Location> place;
     boolean isAreaSafe;
     boolean isBaseSafe;

    public void areaCheck(List<Location> base, List<Location> place ){
        this.base = base;
        this.place = place;

        for (Location location : base) {
            Material type = location.getBlock().getType();

            if (type.isAir() || type.equals(Material.WATER) || type.equals(Material.LAVA) || type.equals(Material.ICE)) {
                isBaseSafe = false;
                break;
            } else {
                isBaseSafe = true;
            }
        }
        for (Location location : place){
            Material type = location.getBlock().getType();
            if (type.isBlock() && !type.isAir()) {

                isAreaSafe = false;
                break;
            } else {
                isAreaSafe = true;
            }
        }
    }
    public  boolean isSafe(){
        return isAreaSafe && isBaseSafe;
    }

}
