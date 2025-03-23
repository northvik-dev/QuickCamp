package com.northvik.quickCamp.managers;

import com.northvik.quickCamp.QuickCamp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class ConfigsInitialize {
    QuickCamp plugin;

    File mainConfigFile;
    File locationFile;
    File templatesFile;

    YamlConfiguration mainConfig;
    YamlConfiguration campLocationConfig;
    YamlConfiguration templatesConfig;

    HashMap<Integer,String> campBlueprint = new HashMap<>();
    List<Material> goodsProtectionList = new ArrayList<>();
    List<String> savedTemplateNames = new ArrayList<>();

    public ConfigsInitialize (QuickCamp plugin){
        this.plugin = plugin;
        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();
        loadFile();
    }

    public void loadFile(){
        mainConfigFile = new File(plugin.getPlugin().getDataFolder(), "config.yml");
        locationFile = new File(plugin.getPlugin().getDataFolder(), "camps.yml");
        templatesFile = new File(plugin.getPlugin().getDataFolder(), "templates.yml");
        if (!mainConfigFile.exists()){
            try {
                mainConfigFile.createNewFile();
                plugin.getLogger().info("[QuickCamp] config.yml created successfully!");
            } catch (IOException e) {
                plugin.getLogger().info("[QuickCamp] Unable to create config.yml");
                throw new RuntimeException(e);
            }
        }
        if (!locationFile.exists()){
            try {
                locationFile.createNewFile();
                plugin.getLogger().info("[QuickCamp] camps.yml created successfully!");
            } catch (IOException e) {
                plugin.getLogger().info("[QuickCamp] Unable to create camps.yml");
                throw new RuntimeException(e);
            }
        }
        if (!templatesFile.exists()){
            try {
                templatesFile.createNewFile();
                plugin.getLogger().info("[QuickCamp] templates.yml created successfully!");
            } catch (IOException e) {
                plugin.getLogger().info("[QuickCamp] Unable to create templates.yml");
                throw new RuntimeException(e);
            }
        }

        campLocationConfig = YamlConfiguration.loadConfiguration(locationFile);
        mainConfig = YamlConfiguration.loadConfiguration(mainConfigFile);
        templatesConfig = YamlConfiguration.loadConfiguration(templatesFile);
    }

    public HashMap<Integer, String> getCampTemplate(String templateName) {
        campBlueprint.clear(); // Clear the existing map to ensure it's fresh
        plugin.reloadConfig(); // Reload the config
        loadFile(); // Reload the cached files
        if (templatesConfig.getConfigurationSection("CampBlueprint." + templateName) != null) {
            ConfigurationSection blueprintSection = templatesConfig.getConfigurationSection("CampBlueprint." + templateName + ".blueprint");

            for (String key : blueprintSection.getKeys(false)) {
                try {
                    int slot = Integer.parseInt(key); // Convert slot key to an integer
                    String item = blueprintSection.getString(key); // Get the material name for the slot

                    if (item != null && !item.isEmpty()) {
                        campBlueprint.put(slot, item); // Add to the HashMap
                    } else {
                        plugin.getLogger().warning("Invalid or missing material for slot: " + key + " in template: " + templateName);
                    }
                } catch (NumberFormatException e) {
                    plugin.getLogger().warning("Invalid slot number: " + key + " in template: " + templateName);
                }
            }
        } else {
            plugin.getLogger().warning("Template section missing: " + templateName);
        }
        return  campBlueprint;
    }

    public int getCampTemplateSize(String templateName){
        campBlueprint.clear(); // Clear the existing map to ensure it's fresh
        plugin.reloadConfig(); // Reload the config
        loadFile();
        int size;
        if (templatesConfig.getConfigurationSection("CampBlueprint." + templateName) != null) {

            ConfigurationSection blueprintSection = templatesConfig.getConfigurationSection("CampBlueprint." + templateName);
            size = blueprintSection.getInt(".size");
        } else{
            size = 1;
        }
        return size;
    }
    //LINKED ITEM
    public ItemStack getLinkedItem(String templateName) {

        return  mainConfig.getItemStack("LinkedItems." + templateName);
    }
    //TEMPLATES NAMES LIST
    public List<String> getTemplateNames (){
        savedTemplateNames.clear(); // Clear the existing map to ensure it's fresh
        plugin.reloadConfig(); // Reload the config
        loadFile();
        if (templatesConfig.getConfigurationSection("CampBlueprint")!= null) {
            savedTemplateNames.addAll(templatesConfig.getConfigurationSection("CampBlueprint").getKeys(false));
        }
        return savedTemplateNames;
    }

    //Get list of GoodsProtection
    public List<Material> getGoodsProtectionList(){
        List<String> goodsProtection =  mainConfig.getStringList("GoodsProtection.");
        if (mainConfig.getConfigurationSection("GoodsProtection")!= null) {
            for (String name: goodsProtection){
                goodsProtectionList.add(Material.valueOf(name));
            }
        }
        return goodsProtectionList;
    }
    ///CONFIG SAVE
    public void saveConfig(YamlConfiguration config, File file){
        try {
            config.save(file);
        } catch (Exception ex) {
            Bukkit.getServer().getConsoleSender().sendMessage("Couldn't save configs");
            throw new RuntimeException(ex);
        }
    }
    //CONFIG
    public YamlConfiguration getMainConfig() {
        return mainConfig;
    }
    public YamlConfiguration getTemplatesConfig(){
        return templatesConfig;
    }

    public YamlConfiguration getCampLocationConfig(){
        return campLocationConfig;
    }
    public File getMainConfigFile() {
        return mainConfigFile;
    }
    public File getLocationFile(){
        return locationFile;
    }
    public File getTemplatesFile(){
        return templatesFile;
    }
}
