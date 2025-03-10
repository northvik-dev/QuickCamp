package com.northvik.quickCamp.utils;

import com.jeff_media.customblockdata.CustomBlockData;
import com.northvik.quickCamp.QuickCamp;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
//TODO - CustomBlockData was changed to WorldGuard implementation. Review reusability or delete.
public class CampCustomBlockData {
    QuickCamp plugin;
    NamespacedKey keyBlock;
    String test;
    UUID uuid;
    Player player;
    Block block;
    public CampCustomBlockData(QuickCamp plugin, Player player){
        this.player = player;
        this.plugin = plugin;
        this.keyBlock =  new NamespacedKey(plugin, "blockKey");
        this.uuid = player.getUniqueId();
    }

    public void setCustomBlockData(Block block){
        PersistentDataContainer blockData = new CustomBlockData(block,plugin);
        blockData.set(keyBlock, PersistentDataType.STRING, "uuid.toString()");
    }

    public boolean isHasCustomData (Block block){
        PersistentDataContainer blockData = new CustomBlockData(block,plugin);
        return blockData.has(keyBlock,PersistentDataType.STRING);
    }
    public String getCustomBlockData(Block block){
        PersistentDataContainer blockData = new CustomBlockData(block,plugin);
        if (blockData.has(keyBlock, PersistentDataType.STRING)) {
            test = blockData.get(keyBlock, PersistentDataType.STRING);
        } else{
            test = "no data";
        }
        return test;
    }

    public boolean isCampOwner(Block block, Player player){
        PersistentDataContainer blockData = new CustomBlockData(block,plugin);
        return blockData.has(keyBlock, PersistentDataType.STRING) && blockData.get(keyBlock, PersistentDataType.STRING).equals(player.getUniqueId().toString());
    }

    public void removeCustomBlockData (Block block){
        PersistentDataContainer blockData = new CustomBlockData(block,plugin);
        blockData.remove(keyBlock);
    }

    public Set<Location> expandClaim (Block block) {
        Set<Location> expandedArea = new HashSet<>();
        for (int y = -1; y < 64; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    expandedArea.add(block.getLocation().clone().add(x, y, z));
                }
            }
        }

        return expandedArea;
    }
}
