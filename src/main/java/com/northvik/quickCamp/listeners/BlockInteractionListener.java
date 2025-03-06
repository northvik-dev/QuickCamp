package com.northvik.quickCamp.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.event.entity.DamageEntityEvent;
import com.sk89q.worldguard.bukkit.event.inventory.UseItemEvent;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.utils.ConfigsInitialize;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class BlockInteractionListener implements Listener {
    QuickCamp plugin;
    Player player;
    Location location;
    YamlConfiguration playerCamp;
    RegionQuery query;
    LocalPlayer localPlayer;
    String rgName = "_QuickCamp";

    ChatColor grey = ChatColor.GRAY;

    public BlockInteractionListener(QuickCamp plugin){
        ConfigsInitialize ci = new ConfigsInitialize(plugin);
        this.plugin = plugin;
        this.playerCamp = ci.getCampLocationConfig();

    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        query = regionContainer().createQuery();
        player = e.getPlayer();
        location = e.getBlock().getLocation();
        if(!query.testState(BukkitAdapter.adapt(location),localPlayer, Flags.BUILD)){
            player.sendMessage(grey+ "You cannot break block here!");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        query = regionContainer().createQuery();
        player = e.getPlayer();
        location = e.getBlock().getLocation();
        if(!query.testState(BukkitAdapter.adapt(location),localPlayer, Flags.BUILD)){
            player.sendMessage(grey+ "You cannot place block here!");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock() != null){
            Material blockType = e.getClickedBlock().getType();
            String value = blockType.name();
            boolean isBed = value.endsWith("_BED");
            if(blockType == Material.CHEST || blockType == Material.TRAPPED_CHEST || isBed){
                player = e.getPlayer();
                location = e.getClickedBlock().getLocation();
                query = regionContainer().createQuery();

                ApplicableRegionSet regionSet = query.getApplicableRegions(BukkitAdapter.adapt(location));
                for (ProtectedRegion region : regionSet){
                    if(region.getOwners().contains(player.getUniqueId())){
                        return;
                    }
                }
                player.sendMessage(grey+ "You not owner of the camp!");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerUseWeapon(EntityDamageByEntityEvent event) {
        // Check if the damager is a player
        if (event.getDamager() instanceof Player attacker) {
            Location attackerLocation = attacker.getLocation();

            // Query the region for the attacker's location
            query = regionContainer().createQuery();
            ApplicableRegionSet regionSet = query.getApplicableRegions(BukkitAdapter.adapt(attackerLocation));

            // Check if PVP is denied in the region
            if (isPlayerInRegion(attacker)&&!regionSet.testState(WorldGuardPlugin.inst().wrapPlayer(attacker), Flags.PVP)) {
                event.setCancelled(true);
                attacker.sendMessage(grey+ "You cannot harm entity inside this camp!");
            }
        }
    }

    @EventHandler
    public void onLavaWaterPlace (UseItemEvent e){

    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        // Check if the shooter is a player (e.g., bow or crossbow)
        if (event.getEntity().getShooter() instanceof Player shooter) {
            Location shooterLocation = shooter.getLocation();

            // Query the region for the shooter's location
            query = regionContainer().createQuery();
            ApplicableRegionSet regionSet = query.getApplicableRegions(BukkitAdapter.adapt(shooterLocation));

            // Check if PVP or projectile use is denied in the region
            if (isPlayerInRegion(shooter) && !regionSet.testState(WorldGuardPlugin.inst().wrapPlayer(shooter), Flags.PVP)) {
                event.setCancelled(true);
                shooter.sendMessage(grey+ "You cannot use ranged weapons inside this camp!");
            }
        }
    }

    @EventHandler
    public void onReceivingDamage(DamageEntityEvent e){
        if(e.getEntity() instanceof Player damagedPlayer){
            Location playerLocation = damagedPlayer.getLocation();

            query = regionContainer().createQuery();
            ApplicableRegionSet regionSet = query.getApplicableRegions(BukkitAdapter.adapt(playerLocation));

                if (isPlayerInRegion(damagedPlayer) && !regionSet.testState(WorldGuardPlugin.inst().wrapPlayer(damagedPlayer), Flags.PVP)) {
                        e.setCancelled(true);
                }
        }
    }

    @EventHandler
    public void onTNTearByExplode(EntityExplodeEvent e) {
        List<Block> affectedBlocks = e.blockList();
        query = regionContainer().createQuery();
        // Remove only blocks inside regions where TNT explosions are denied
        affectedBlocks.removeIf(block -> {
            Location blockLocation = block.getLocation();
            ApplicableRegionSet regionSet = query.getApplicableRegions(BukkitAdapter.adapt(blockLocation));
            // If the block is in any region, check if TNT is denied
            boolean tntDenied = !regionSet.testState(null, Flags.TNT);
            // Remove block only if it's in a region and TNT is denied
            return tntDenied && !regionSet.getRegions().isEmpty();
        });
    }


    /// Check if the player inside a camp region
    public boolean isPlayerInRegion (Player player){
        Location location = player.getLocation();
        query = regionContainer().createQuery();

        ApplicableRegionSet regionSet = query.getApplicableRegions(BukkitAdapter.adapt(location));

        for (ProtectedRegion region : regionSet){
            if (region.getId().equalsIgnoreCase(player.getName()+rgName)){
                return true;
            }
        }
        return false;
    }

    public RegionContainer regionContainer(){
        return WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

}
