package com.viktor.quickCamp.commands;

import com.viktor.quickCamp.QuickCamp;
import com.viktor.quickCamp.utils.ConfigsInitialize;
import com.viktor.quickCamp.utils.LocatedCampPDC;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class CampRemove {
    Player player;
    Location camplocation;
    QuickCamp plugin;
    ConfigsInitialize ci;
    public CampRemove(Player player, Location camplocation, QuickCamp plugin, ConfigsInitialize ci){
        this.player = player;
        this.camplocation = camplocation;
        this.plugin = plugin;
        this.ci = ci;
    }
    public void removeCamp(){

        String path = player.getUniqueId().toString();
        LocatedCampPDC lc = new LocatedCampPDC(player,camplocation,plugin);

        List<Map<?,?>> locationList = ci.getCampLocationConfig().getMapList(path);

        if (locationList.isEmpty()){
            player.sendMessage(ChatColor.RED + "You don't have any camp to remove!");
        }else {

            for (Map<?, ?> map : locationList) {
                Location block = Location.deserialize((Map<String, Object>) map);
                block.getBlock().setType(Material.AIR);
            }

            ci.getCampLocationConfig().set(path, null);
            ci.saveConfig(ci.getCampLocationConfig(), ci.getLocationFile());
            lc.removeCamping();


            player.sendMessage(ChatColor.GREEN + "Your camp was removed!");
        }
    }

}
