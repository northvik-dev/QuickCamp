package com.viktor.quickCamp;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.SlimeSplitEvent;

import java.util.ArrayList;
import java.util.List;

public class CampCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Location playerLoc = player.getLocation();
            World world = playerLoc.getWorld();

            //blocks coordinates around player
            List<Location> placingArea = new ArrayList<>();
            //blocks under a player
            List<Location> basementBlockArea = new ArrayList<>();

            IsSafe isSafe = new IsSafe();

            //adding coordinates of blocks
            if (world !=null){
                for (int x = -2; x < 3; x++) {
                    for (int z = -2; z < 3; z++) {
                        Location zeroLvlBlock = playerLoc.clone().add(x, 0, z);
                        Location basementLvl = playerLoc.clone().add(x, -1, z);
                        placingArea.add(zeroLvlBlock);
                        basementBlockArea.add(basementLvl);
                    }
                }

                isSafe.areCheck(basementBlockArea,placingArea);

                if (isSafe.isSafe()) {
                    placingArea.get(0).getBlock().setType(Material.TORCH);
                    placingArea.get(4).getBlock().setType(Material.TORCH);
                    placingArea.get(20).getBlock().setType(Material.TORCH);
                    placingArea.get(24).getBlock().setType(Material.TORCH);
                    placingArea.get(12).getBlock().setType(Material.CAMPFIRE);
                }else {
                    player.sendMessage(ChatColor.RED + "You can not place camp on air | water | lava | ice. Make sure that basement is strong and 5x5 flat are!");

                }
            }
        }
        return false;
    }
}
