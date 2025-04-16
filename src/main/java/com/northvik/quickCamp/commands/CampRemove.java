package com.northvik.quickCamp.commands;

import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.managers.ClaimHandler;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.managers.LocatedCampPDC;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        ClaimHandler claimHandler = new ClaimHandler();
        List<Map<?,?>> locationList = ci.getCampLocationConfig().getMapList(path);
        lc.removeCamping();
        if (locationList.isEmpty()){

            player.sendMessage(plugin.getMsgConfig().msgBuilder("no_camp"));
        }else {

            for (Map<?, ?> map : locationList) {
                Location loc = Location.deserialize((Map<String, Object>) map);

                if(loc.getBlock().getState() instanceof Chest){
                    Chest chest = (Chest) loc.getBlock().getState();

                    for(ItemStack item : chest.getInventory().getContents()){
                        if(item != null){
                            loc.getWorld().dropItemNaturally(loc, item);
                        }
                    }
                }

                loc.getBlock().setType(Material.AIR, false);
            }
            ci.getCampLocationConfig().set(path, null);
            ci.saveConfig(ci.getCampLocationConfig(), ci.getLocationFile());

            claimHandler.removeClaim(player);

            player.sendMessage(plugin.getMsgConfig().msgBuilder("camp_removed"));
        }
    }

}
