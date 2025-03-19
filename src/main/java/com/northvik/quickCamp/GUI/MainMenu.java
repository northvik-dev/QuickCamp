package com.northvik.quickCamp.GUI;

import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.utils.GuiButtonIndexes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu {
    public void menu(Player player, QuickCamp plugin) {
        GuiButtonIndexes gbi = new GuiButtonIndexes();
        Inventory menu = Bukkit.createInventory(player, 54, "QuickCamp menu");
        //non usable area
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackGlassMeta = blackGlass.getItemMeta();
        blackGlassMeta.setDisplayName(" ");
        blackGlass.setItemMeta(blackGlassMeta);

        //Create new template button
        ItemStack createCamp = new ItemStack(Material.CAMPFIRE);
        ItemMeta createCampMeta = createCamp.getItemMeta();
        createCampMeta.setDisplayName("Create new camp template");
        createCamp.setItemMeta(createCampMeta);

        //Saved templates list
        ItemStack savedTemplatesMenu = new ItemStack(Material.CHEST);
        ItemMeta savedTemplatesMenuMeta = savedTemplatesMenu.getItemMeta();
        savedTemplatesMenuMeta.setDisplayName("Load Templates");
        savedTemplatesMenu.setItemMeta(savedTemplatesMenuMeta);
        // Close button
        ItemStack closeButton = new ItemStack(Material.BARRIER);
        ItemMeta closeButtonMeta = closeButton.getItemMeta();
        closeButtonMeta.setDisplayName("Close");
        closeButton.setItemMeta(closeButtonMeta);

        for (int i = 0; i < menu.getSize() ; i++) {
            menu.setItem(i, blackGlass);
            if (i == gbi.getCreateTemplate() ) menu.setItem(i, createCamp);
            if (i == gbi.getLoadTemplates() ) menu.setItem(i, savedTemplatesMenu);
            if (i == gbi.getCloseButton()) menu.setItem(i, closeButton);

        }
        player.openInventory(menu);

    }
}
