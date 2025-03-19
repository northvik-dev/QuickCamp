package com.northvik.quickCamp.GUI;

import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.utils.GuiButtonIndexes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SavedTemplatesMenu {
    public void menu (Player player, QuickCamp plugin) {
        ConfigsInitialize ci = new ConfigsInitialize(plugin);
        GuiButtonIndexes gbi = new GuiButtonIndexes();
        Inventory menu = Bukkit.createInventory(player, 54, "Saved Templates");

        //non usable area
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackGlassMeta = blackGlass.getItemMeta();
        blackGlassMeta.setDisplayName(" ");
        blackGlass.setItemMeta(blackGlassMeta);

        // Back button
        ItemStack backButton = new ItemStack(Material.BARRIER);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        backButtonMeta.setDisplayName("Back");
        backButton.setItemMeta(backButtonMeta);


        for (int i = 0; i < 9 ; i++) {
            menu.setItem(i, blackGlass);
            if (i == gbi.getCloseButton()) menu.setItem(i, backButton);

        }
        for (int i = 45; i < 54 ; i++) {
            menu.setItem(i, blackGlass);
        }

        List<String> templates = new ArrayList<>(ci.getTemplateNames());
        for (int i = 0; i < templates.size(); i++) {
            String name = templates.get(i);
            ItemStack template = new ItemStack(Material.CAMPFIRE);
            if (name != null){
                ItemMeta templateMeta = template.getItemMeta();
                templateMeta.setDisplayName(name);
                template.setItemMeta(templateMeta);
            }
            menu.setItem(9+i, template);

        }
        player.openInventory(menu);

    }



}
