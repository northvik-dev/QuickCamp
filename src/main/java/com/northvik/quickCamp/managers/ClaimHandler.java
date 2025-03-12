package com.northvik.quickCamp.managers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;


public class ClaimHandler {

    String regName = "_QuickCamp";

    public boolean claimLand(Player player, Location pos1, Location pos2) {
        String regionName = player.getName() + regName;
        World world = BukkitAdapter.adapt(player.getWorld());
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);

        if (regionManager == null) {
            return false;
        }
        BlockVector3 min = BukkitAdapter.asBlockVector(pos1);
        BlockVector3 max = BukkitAdapter.asBlockVector(pos2);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(regionName, min, max);

        List<ProtectedRegion> intersectingRegions = region.getIntersectingRegions(regionManager.getRegions().values());
        if (!intersectingRegions.isEmpty()) {
            return false;
        }

        region.getOwners().addPlayer(player.getUniqueId());
        try{
            regionManager.addRegion(region);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean removeClaim(Player player){
        String regionName = player.getName() +regName;
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
            if (regionManager !=null && regionManager.hasRegion(regionName)){
                regionManager.removeRegion(regionName);
                return true;
            }
            return false;
    }

    public void setRegionFlags(Player player) {
        String regionName = player.getName()+regName;
        World world = BukkitAdapter.adapt(player.getWorld());
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
        ProtectedRegion region = regionManager.getRegion(regionName);
        if (region != null) {
            //DENY
            region.setFlag(Flags.BUILD, StateFlag.State.DENY);
            region.setFlag(Flags.BLOCK_BREAK, StateFlag.State.DENY);
            region.setFlag(Flags.PVP, StateFlag.State.DENY);
            region.setFlag(Flags.CREEPER_EXPLOSION, StateFlag.State.DENY);
            region.setFlag(Flags.OTHER_EXPLOSION, StateFlag.State.DENY);
            region.setFlag(Flags.MOB_DAMAGE, StateFlag.State.DENY);
            region.setFlag(Flags.INVINCIBILITY, StateFlag.State.DENY);
            region.setFlag(Flags.LAVA_FLOW, StateFlag.State.DENY);
            //ALLOW
            region.setFlag(Flags.SLEEP, StateFlag.State.ALLOW);
            region.setFlag(Flags.CHEST_ACCESS, StateFlag.State.ALLOW);
            region.setFlag(Flags.INTERACT, StateFlag.State.ALLOW);

        }
    }

}
