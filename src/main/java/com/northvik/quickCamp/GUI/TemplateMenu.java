package com.northvik.quickCamp.GUI;
import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.managers.GuiCustomSize;
import com.northvik.quickCamp.utils.GuiButtonIndexes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
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

public class TemplateMenu {
    QuickCamp plugin;
    int choiceSize;

    public void setChoiceSize(int choiceSize) {
        this.choiceSize = choiceSize;
    }

    public TemplateMenu(QuickCamp plugin){
        this.plugin = plugin;
    }
    Inventory campGui;
    public void menu(Player player) {
        getInventory(player);
        GuiButtonIndexes gbi = new GuiButtonIndexes();


        // Close button
        ItemStack closeButton = new ItemStack(Material.BARRIER);
        ItemMeta closeButtonMeta = closeButton.getItemMeta();
        closeButtonMeta.setDisplayName(ChatColor.GRAY + "Back");
        closeButton.setItemMeta(closeButtonMeta);

        // Close button
        ItemStack itemLinkButton = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta itemLinkButtonMeta = itemLinkButton.getItemMeta();
        itemLinkButtonMeta.setDisplayName(ChatColor.GRAY + "Link item");
        itemLinkButton.setItemMeta(itemLinkButtonMeta);

        // Save button
        ItemStack saveButton = getCustomSkull("a79a5c95ee17abfef45c8dc224189964944d560f19a44f19f8a46aef3fee4756");
        ItemMeta saveButtonMeta = saveButton.getItemMeta();
        saveButtonMeta.setDisplayName(ChatColor.GREEN + "Save");
        saveButton.setItemMeta(saveButtonMeta);

        //Clear button
        ItemStack clearButton = getCustomSkull("cb067ae612d5256a24ccfc74c11814f01962b4d81817a618134b45f36fe6fcb3");
        ItemMeta clearButtonMeta = clearButton.getItemMeta();
        clearButtonMeta.setDisplayName(ChatColor.YELLOW + "Delete");
        clearButton.setItemMeta(clearButtonMeta);

        //Info button
        ItemStack infoButton = getCustomSkull("2705fd94a0c431927fb4e639b0fcfb49717e412285a02b439e0112da22b2e2ec");
        ItemMeta infoButtonMeta = infoButton.getItemMeta();
        infoButtonMeta.setDisplayName(ChatColor.GRAY + (ChatColor.BOLD + "Info"));
        infoButtonMeta.setLore(Arrays.asList(
                ChatColor.YELLOW + "Delete button:",
                ChatColor.GRAY + " - clear blueprint configs and table.",
                ChatColor.GREEN + "Save button:",
                ChatColor.GRAY + " - save template and blueprint to config file."
                ));
        infoButton.setItemMeta(infoButtonMeta);

        //Camp size button
        ItemStack sizeButton = getSizeButton(choiceSize);

        campGui.setItem(gbi.getSaveButton(), saveButton);
        campGui.setItem(gbi.getCloseButton(), closeButton);
        campGui.setItem(gbi.getClearButton(), clearButton);
        campGui.setItem(gbi.getInfoButton(), infoButton);
        campGui.setItem(gbi.getSizeButton(), sizeButton);
        campGui.setItem(gbi.getItemLinkButton(), itemLinkButton);
        player.openInventory(campGui);
    }

    //TEMPLATE LOADER
    public void loadTemplate(String templateName){
        ConfigsInitialize ci = new ConfigsInitialize(plugin);

        HashMap<Integer, String> campBlueprint = new HashMap<>(ci.getCampTemplate(templateName));
        for (var entry : campBlueprint.entrySet()) {
            String matString = entry.getValue();
            ItemStack item = new ItemStack(Material.valueOf(matString));
            campGui.setItem(entry.getKey(), item);
        }
    }
    ////non usable area
    public ItemStack getNonUsableItem(){
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackGlassMeta = blackGlass.getItemMeta();
        blackGlassMeta.setDisplayName(" ");
        blackGlass.setItemMeta(blackGlassMeta);
        return blackGlass;
    }
    //SIZE BUTTON CONSTRUCTOR
    public ItemStack getSizeButton(int choiceSize){
        ItemStack sizeButton = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta sizeButtonItemMeta = sizeButton.getItemMeta();
        sizeButtonItemMeta.setDisplayName(ChatColor.WHITE + "Choose Camp Size");
        switch (choiceSize){
            case 1: {
                sizeButtonItemMeta.setLore(Arrays.asList(
                        ChatColor.YELLOW +(ChatColor.BOLD + "3x3"),
                        ChatColor.GRAY + "4x4",
                        ChatColor.GRAY + "5x5",
                        ChatColor.GRAY + "6x6"
                ));
            }break;
            case 2: {
                sizeButtonItemMeta.setLore(Arrays.asList(
                        ChatColor.GRAY + "3x3",
                        ChatColor.YELLOW +(ChatColor.BOLD + "4x4"),
                        ChatColor.GRAY + "5x5",
                        ChatColor.GRAY + "6x6"
                ));
            }break;
            case 3: {
                sizeButtonItemMeta.setLore(Arrays.asList(
                        ChatColor.GRAY + "3x3",
                        ChatColor.GRAY + "4x4",
                        ChatColor.YELLOW +(ChatColor.BOLD + "5x5"),
                        ChatColor.GRAY + "6x6"
                ));
            }break;
            case 4: {
                sizeButtonItemMeta.setLore(Arrays.asList(
                        ChatColor.GRAY + "3x3",
                        ChatColor.GRAY + "4x4",
                        ChatColor.GRAY + "5x5",
                        ChatColor.YELLOW +(ChatColor.BOLD + "6x6")
                        ));
            }
        }

        sizeButton.setItemMeta(sizeButtonItemMeta);
        return sizeButton;
    }
    //CUSTOM SKULL BUTTONS
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

    public Inventory getInventory(Player player){
        return campGui = Bukkit.createInventory(player, 54, "Template Editor");

    }


}
