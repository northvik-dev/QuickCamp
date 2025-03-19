package com.northvik.quickCamp.managers;

import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.utils.GuiButtonIndexes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class ConfigsInitialize {
//slots:
//  - 0,1,2,3,4,5,6,7,8,
//  - 9,10,11,12,13,14,15,16,17,
//  - 18,19,20,21,22,23,24,25,26,
//  - 27,28,29,30,31,32,33,34,35,
//  - 36,37,38,39,40,41,42,43,44,
//  - 45,46,47,48,49,50,51,52,53,

    QuickCamp plugin;
    File file;
    File locationFile;
    YamlConfiguration config;
    YamlConfiguration campLocationConfig;
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
        file = new File(plugin.getPlugin().getDataFolder(), "config.yml");
        locationFile = new File(plugin.getPlugin().getDataFolder(), "camps.yml");
        if (!file.exists() || !locationFile.exists()){
            plugin.getServer().getConsoleSender().sendMessage("Config file doesn't exist!");
        }
        campLocationConfig = YamlConfiguration.loadConfiguration(locationFile);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public HashMap<Integer, String> getCampTemplate(String templateName) {
        campBlueprint.clear(); // Clear the existing map to ensure it's fresh
        plugin.reloadConfig(); // Reload the config
        loadFile(); // Reload the cached files
        if (config.getConfigurationSection("CampBlueprint." + templateName) != null) {
            ConfigurationSection blueprintSection = config.getConfigurationSection("CampBlueprint." + templateName + ".blueprint");

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
        if (config.getConfigurationSection("CampBlueprint." + templateName) != null) {

            ConfigurationSection blueprintSection = config.getConfigurationSection("CampBlueprint." + templateName);
            size = blueprintSection.getInt(".size");
        } else{
            size = 1;
        }
        return size;
    }

    //TEMPLATES NAMES LIST
    public List<String> getTemplateNames (){
        savedTemplateNames.clear(); // Clear the existing map to ensure it's fresh
        plugin.reloadConfig(); // Reload the config
        loadFile();
        if (config.getConfigurationSection("CampBlueprint")!= null) {
            savedTemplateNames.addAll(config.getConfigurationSection("CampBlueprint").getKeys(false));
        }
        return savedTemplateNames;
    }

    //Get list of GoodsProtection
    public List<Material> getGoodsProtectionList(){
        List<String> goodsProtection =  config.getStringList("GoodsProtection.");
        if (config.getConfigurationSection("GoodsProtection")!= null) {
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
    public YamlConfiguration getYmlConfig(){
        return config;
    }

    public YamlConfiguration getCampLocationConfig(){
        return campLocationConfig;
    }
    public File getLocationFile(){
        return locationFile;
    }
    public File getFile(){
        return file;
    }
}
