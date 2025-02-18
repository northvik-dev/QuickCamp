package com.viktor.quickCamp.utils;

import com.viktor.quickCamp.QuickCamp;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigsInitialize {
    QuickCamp plugin;
    File file;
    YamlConfiguration config;

    public ConfigsInitialize (QuickCamp plugin){
        this.plugin = plugin;
        loadFile();
    }

    public void loadFile(){
        file = new File(plugin.getPlugin().getDataFolder(), "config.yml");

        if (!file.exists()){
            plugin.getServer().getConsoleSender().sendMessage("Config file doesn't exist!");
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getYmlConfig(){
        return config;
    }
}
