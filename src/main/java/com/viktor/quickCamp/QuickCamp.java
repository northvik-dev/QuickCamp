package com.viktor.quickCamp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuickCamp extends JavaPlugin {

    private QuickCamp main;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("camp").setExecutor(new CampCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public QuickCamp getPlugin (){
        return main;
    }
}
