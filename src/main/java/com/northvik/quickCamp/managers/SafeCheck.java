package com.northvik.quickCamp.managers;

import com.northvik.quickCamp.QuickCamp;
import com.palmergames.bukkit.towny.TownyAPI;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.protection.flags.Flags;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class SafeCheck {

    List<Location> base;
    List<Location> place;
    boolean isAreaSafe;
    boolean isBaseSafe;



    String region;
    public void areaCheck(List<Location> base, List<Location> place, Player player, QuickCamp plugin) {
        this.base = base;
        this.place = place;

        for (Location location : base) {
            Material type = location.getBlock().getType();

            if ( plugin.getDependencyCheck().isTowny() && isLocationClaimedByGriefPrevention(location, plugin)){
                region = " at claimed area!";
                return;
            }
            if (plugin.getDependencyCheck().isGriefPrevention() && isLocationInTown(location, plugin)){
                region = " in a town!";
                return;
            }

            if (isProtected(location, player)
                    || type.isAir()
                    || type.equals(Material.WATER)
                    || type.equals(Material.LAVA)
                    || type.equals(Material.ICE)
            ) {
                isBaseSafe = false;
                break;
            } else {
                isBaseSafe = true;
            }
        }

        for (Location location : place) {
            Material type = location.getBlock().getType();
            if (isProtected(location, player) || type.isSolid()|| type.equals(Material.WATER)
                    || type.equals(Material.LAVA) || type.equals(Material.ICE)) {
                isAreaSafe = false;
                break;
            } else {
                isAreaSafe = true;
            }
        }
    }

    public boolean isSafe() {
        return isAreaSafe && isBaseSafe;
    }

    private boolean isProtected(Location location, Player player) {
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(location));
        return !set.testState(WorldGuardPlugin.inst().wrapPlayer(player), Flags.BUILD);
    }

    private boolean isLocationClaimedByGriefPrevention(Location location, QuickCamp plugin) {
        GriefPreventionClaimCheck gpClaimCheck = new GriefPreventionClaimCheck();
        return gpClaimCheck.isClaimed(location);
    }

    private boolean isLocationInTown(Location location, QuickCamp plugin) {
        return !TownyAPI.getInstance().isWilderness(location);
    }
    public String getMsgRegion() {
        return region;
    }
}

