package com.northvik.quickCamp.utils;
import com.northvik.quickCamp.QuickCamp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.*;

public class CampGUI {
    public void gui(Player player, QuickCamp plugin) {
        ConfigsInitialize ci = new ConfigsInitialize(plugin);

        Inventory campGui = Bukkit.createInventory(player, 54, "Camp GUI");

        //non usable area
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackGlassMeta = blackGlass.getItemMeta();
        blackGlassMeta.setItemName(" ");
        blackGlass.setItemMeta(blackGlassMeta);

        // Save button
        ItemStack saveButton = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta saveButtonMeta = saveButton.getItemMeta();
        saveButtonMeta.setItemName("Save setup");
        saveButton.setItemMeta(saveButtonMeta);

        // Close button
        ItemStack closeButton = new ItemStack(Material.RED_CONCRETE);
        ItemMeta closeButtonMeta = closeButton.getItemMeta();
        closeButtonMeta.setItemName("Close");
        closeButton.setItemMeta(closeButtonMeta);
        //Clear button
        ItemStack clearButton = new ItemStack(Material.YELLOW_CONCRETE);
        ItemMeta clearButtonMeta = clearButton.getItemMeta();
        clearButtonMeta.setItemName("Clear");
        clearButton.setItemMeta(clearButtonMeta);
        //Info button
        ItemStack infoButton = new ItemStack(Material.CANDLE);
        ItemMeta infoButtonMeta = clearButton.getItemMeta();
        infoButtonMeta.setItemName("Info");
        infoButtonMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Use Clear button to clear blueprint configs table.",
                ChatColor.GRAY + "Use Save button to save blueprint to config file.",
                ChatColor.GRAY + "Use Close button to close menu without saving."));
        infoButton.setItemMeta(infoButtonMeta);

       // ItemStack infoButton = createCustomHead("Info", Arrays.asList("-Some info", "-Info again"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcwNWZkOTRhMGM0MzE5MjdmYjRlNjM5YjBmY2ZiNDk3MTdlNDEyMjg1YTAyYjQzOWUwMTEyZGEyMmIyZTJlYyJ9fX0=");


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
//TODO - resolve issue
//Caused by: java.lang.IllegalArgumentException: Can not set net.minecraft.world.item.component.ResolvableProfile field org.bukkit.craftbukkit.inventory.CraftMetaSkull.profile to com.mojang.authlib.GameProfile

/*   public static ItemStack createCustomHead(String name, List<String> lore, String texture) {
        // Create a player head item
        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta buttonMeta = (SkullMeta) button.getItemMeta();

        if (buttonMeta != null) {
            buttonMeta.setDisplayName(ChatColor.GOLD + name);
            buttonMeta.setLore(lore);

            GameProfile profile = new GameProfile(UUID.randomUUID(), "");
            profile.getProperties().put("textures", new Property("textures",texture));

            Field field;

            try {
              field = buttonMeta.getClass().getDeclaredField("profile");
              field.setAccessible(true);
              field.set(buttonMeta, profile);
            } catch (NoSuchFieldException | IllegalAccessException e){
                e.printStackTrace();

            }
            button.setItemMeta(buttonMeta);
        }
        return button;
    }*/
}
