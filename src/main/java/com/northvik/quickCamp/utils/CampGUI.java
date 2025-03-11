package com.northvik.quickCamp.utils;
import com.northvik.quickCamp.QuickCamp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class CampGUI {
    public void gui(Player player, QuickCamp plugin) {
        ConfigsInitialize ci = new ConfigsInitialize(plugin);

        Inventory campGui = Bukkit.createInventory(player, 54, "Camp GUI");

        //non usable area
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackGlassMeta = blackGlass.getItemMeta();
        blackGlassMeta.setDisplayName(" ");
        blackGlass.setItemMeta(blackGlassMeta);

        // Save button
        ItemStack saveButton = getCustomSkull("a79a5c95ee17abfef45c8dc224189964944d560f19a44f19f8a46aef3fee4756");
        ItemMeta saveButtonMeta = saveButton.getItemMeta();
        saveButtonMeta.setDisplayName("Save setup");
        saveButton.setItemMeta(saveButtonMeta);

        // Close button
        ItemStack closeButton = getCustomSkull("27548362a24c0fa8453e4d93e68c5969ddbde57bf6666c0319c1ed1e84d89065");
        ItemMeta closeButtonMeta = closeButton.getItemMeta();
        closeButtonMeta.setDisplayName("Close");
        closeButton.setItemMeta(closeButtonMeta);
        //Clear button
        ItemStack clearButton = getCustomSkull("cb067ae612d5256a24ccfc74c11814f01962b4d81817a618134b45f36fe6fcb3");
        ItemMeta clearButtonMeta = clearButton.getItemMeta();
        clearButtonMeta.setDisplayName("Clear");
        clearButton.setItemMeta(clearButtonMeta);
        //Info button
        ItemStack infoButton = getCustomSkull("2705fd94a0c431927fb4e639b0fcfb49717e412285a02b439e0112da22b2e2ec");
        ItemMeta infoButtonMeta = infoButton.getItemMeta();
        infoButtonMeta.setDisplayName("Info");
        infoButtonMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Use Clear button to clear blueprint configs table.",
                ChatColor.GRAY + "Use Save button to save blueprint to config file.",
                ChatColor.GRAY + "Use Close button to close menu without saving."));
        infoButton.setItemMeta(infoButtonMeta);



        List<Integer> nonUsableSlots = new ArrayList<>(ci.getNonUsableSlotsIndexes());
        for (Integer slot : nonUsableSlots) {
            campGui.setItem(slot, blackGlass);
        }
        HashMap<Integer, String> campBlueprint = new HashMap<>(ci.getCampBlueprint());
        for (var entry : campBlueprint.entrySet()) {
            String matString = entry.getValue();

            ItemStack item = new ItemStack(Material.valueOf(matString));
            campGui.setItem(entry.getKey(), item);
        }


        campGui.setItem(ci.saveButton, saveButton);
        campGui.setItem(ci.closeButton, closeButton);
        campGui.setItem(ci.clearButton, clearButton);
        campGui.setItem(ci.infoButton, infoButton);

        player.openInventory(campGui);
    }

    public ItemStack getCustomSkull(String texture) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures playerTextures = profile.getTextures();
        URL url = null;

        try {
            url = new URL("http://textures.minecraft.net/texture/"+texture);
        } catch (MalformedURLException ignored) {

        }
        playerTextures.setSkin(url);
        profile.setTextures(playerTextures);

        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwnerProfile(profile);
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }
}
