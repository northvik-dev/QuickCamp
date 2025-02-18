package com.viktor.quickCamp.commands;

import com.viktor.quickCamp.utils.BlocksLocationList;
import com.viktor.quickCamp.utils.SafeCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CampCommand {

    public void campPlace(Player player){

        BlocksLocationList bll = new BlocksLocationList();
        SafeCheck safeCheck = new SafeCheck();
        bll.blockLocations(player);

        safeCheck.areaCheck(bll.getBasementAreaList(), bll.getPlacingAreaList());

        if (safeCheck.isSafe()) {
            bll.getPlacingAreaList().get(0).getBlock().setType(Material.TORCH);
            bll.getPlacingAreaList().get(4).getBlock().setType(Material.TORCH);
            bll.getPlacingAreaList().get(20).getBlock().setType(Material.TORCH);
            bll.getPlacingAreaList().get(24).getBlock().setType(Material.TORCH);
            bll.getPlacingAreaList().get(12).getBlock().setType(Material.CAMPFIRE);
        } else {
            player.sendMessage(ChatColor.RED + "You can not place camp on air | water | lava | ice. Make sure that basement is strong and 5x5 flat are!");

        }

    }
}
