package com.northvik.quickCamp.commands;

import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;

import java.util.*;

public class CampCommand {
    Player player;
    QuickCamp plugin;
    BlocksLocationList bll = new BlocksLocationList();
    ConfigsInitialize ci;
    LocatedCampPDC lc;
    public CampCommand(Player player, QuickCamp plugin, ConfigsInitialize ci){
        this.player = player;
        this.plugin = plugin;
        this.ci = ci;
    }

    public void campPlace(){
        SafeCheck safeCheck = new SafeCheck();
        bll.blockLocations(player);
        LocatedCampPDC locatedCampPDC = new LocatedCampPDC(player, bll.getCampLocation(), plugin);
        safeCheck.areaCheck(bll.getBasementAreaList(), bll.getPlacingAreaList(), player);

        Location pos1 = player.getLocation().clone().add(-3,-1,3);
        Location pos2 = player.getLocation().clone().add(3,3,-3);
        ClaimHandler claimHandler = new ClaimHandler();

        if (!locatedCampPDC.isCamping()){
            if (safeCheck.isSafe()) {
                if(claimHandler.claimLand(player,pos1,pos2)) {
                    claimHandler.setRegionFlags(player);
                    setCamp();
                    locatedCampPDC.setCamp();
                    player.sendMessage(ChatColor.GREEN + "You have set camp at: " + locatedCampPDC.getCampLocation());
                }
            } else {
                player.sendMessage(ChatColor.RED + "You cannot place camp here.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You cannot place more than one camp!");
        }
    }
    public void setCamp(){
        HashMap<Integer, String> campBlueprint = new HashMap<>(ci.getCampBlueprint());
        List<Integer> placingArea = new ArrayList<>(ci.getSlotsIndexes());
//        CampCustomBlockData campCBD = new CampCustomBlockData(plugin, player);
        //Getting all slots from gui for indexing block placement on correct location
        for (int i = 0; i < placingArea.size(); i++) {
            for (var entry : campBlueprint.entrySet()) {
                if (placingArea.get(i).equals(entry.getKey())) {
                    Block block = bll.getPlacingAreaList().get(i).getBlock(); // Current block (foot of the bed)
                    String value = entry.getValue();
                    Material mat = Material.valueOf(value);

                    if (value.endsWith("_BED")) { // Check if it's a bed
                        block.setType(mat);
                        Bed bedData = (Bed) block.getBlockData();
                        bedData.setPart(Bed.Part.HEAD);
                        bedData.setFacing(BlockFace.WEST);
                        block.setBlockData(bedData);

                        Block footBedBlock = block.getRelative(bedData.getFacing().getOppositeFace());

                        footBedBlock.setType(mat);
                        Bed footBedData = (Bed) footBedBlock.getBlockData();
                        footBedData.setPart(Bed.Part.FOOT);
                        footBedData.setFacing(BlockFace.WEST);
                        footBedBlock.setBlockData(footBedData);

//                        campCBD.setCustomBlockData(block);
//                        campCBD.setCustomBlockData(footBedBlock);


                    } else {
                        block.setType(mat);
//
//                        for (Location location: campCBD.expandClaim(block)){
//                            campCBD.setCustomBlockData(location.getBlock());
//                        }
                    }
                }
            }
        }
        addPlayerCampLocation();
    }

///// Camp area blocks location to configs
    public void addPlayerCampLocation(){

        String path = player.getUniqueId().toString();

        List<Map<String,Object>> blockLocations = new ArrayList<>();

        for(Location block : bll.getPlacingAreaList()){
           blockLocations.add(block.serialize());
        }

        ci.getCampLocationConfig().set(path, blockLocations);
        ci.saveConfig(ci.getCampLocationConfig(),ci.getLocationFile());
    }


}
