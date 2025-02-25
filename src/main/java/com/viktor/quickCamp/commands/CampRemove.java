package com.viktor.quickCamp.commands;

import com.viktor.quickCamp.QuickCamp;
import com.viktor.quickCamp.utils.LocatedCamp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class CampRemove {
    Player player;
    Location camplocation;
    QuickCamp plugin;
    public CampRemove( Player player, Location camplocation, QuickCamp plugin){
        this.player = player;
        this.camplocation = camplocation;
        this.plugin = plugin;
    }
    public void removeCamp(){
        LocatedCamp lc = new LocatedCamp(player,camplocation,plugin);
        lc.getData().set(lc.getKey(), PersistentDataType.BOOLEAN, false);

    }

}
