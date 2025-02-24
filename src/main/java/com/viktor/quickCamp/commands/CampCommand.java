package com.viktor.quickCamp.commands;

import com.viktor.quickCamp.QuickCamp;
import com.viktor.quickCamp.utils.BlocksLocationList;
import com.viktor.quickCamp.utils.ConfigsInitialize;
import com.viktor.quickCamp.utils.SafeCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CampCommand {
    Player player;
    QuickCamp plugin;

    public CampCommand(Player player, QuickCamp plugin){
        this.player = player;
        this.plugin = plugin;
    }

    public void campPlace(){
        ConfigsInitialize ci = new ConfigsInitialize(plugin);
        BlocksLocationList bll = new BlocksLocationList();
        SafeCheck safeCheck = new SafeCheck();
        bll.blockLocations(player);

        safeCheck.areaCheck(bll.getBasementAreaList(), bll.getPlacingAreaList());

        if (safeCheck.isSafe()) {
            HashMap<Integer,String> campBlueprint = new HashMap<>(ci.getCampBlueprint());
            List<Integer> placingArea = new ArrayList<>(ci.getSlotsIndexes());

            //Getting all slots from gui for indexing block placement on correct location
            for (int i = 0; i < placingArea.size(); i++) {
                for (var entry: campBlueprint.entrySet()) {
                    if (placingArea.get(i).equals(entry.getKey())){
                        Block block =  bll.getPlacingAreaList().get(i).getBlock(); // Current block (foot of the bed)
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


                        } else {
                            // Place any other block normally
                            block.setType(mat);

                        }
                    }
                }
            }

        } else {
            player.sendMessage(ChatColor.RED + "You can not place camp on air | water | lava | ice. Make sure that basement is strong and 5x5 flat are!");

        }


    }
}
