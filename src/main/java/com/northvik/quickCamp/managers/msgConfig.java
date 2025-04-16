package com.northvik.quickCamp.managers;

import com.northvik.quickCamp.QuickCamp;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class msgConfig {

    QuickCamp plugin;
    YamlConfiguration yml;

    public msgConfig(QuickCamp plugin){
        this.plugin =plugin;
        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();
        yml = loadConfigs("msg.yml");
    }

    public YamlConfiguration loadConfigs( String configName){
        File file = new File(plugin.getPlugin().getDataFolder(), configName);
        if(!file.exists()){
            plugin.getLogger().info("Couldn't find " + configName + ", saving default version.");
            plugin.saveResource(configName, false);
            plugin.getLogger().info(configName + " defaults loaded!");
        } else {
            plugin.getLogger().info(configName + " loaded.");
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getMsg(){
        return yml;
    }

    public String msgBuilder (String configPath){
        return ChatColor.translateAlternateColorCodes('&', yml.getString(configPath));
    }
}
