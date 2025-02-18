package com.viktor.quickCamp;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuickCamp extends JavaPlugin implements Listener {

    private QuickCamp main;

    @Override
    public void onEnable() {
        main = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        // Plugin startup logic
        getCommand("camp").setExecutor(new CommandHandler(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public QuickCamp getPlugin (){
        return main;
    }
}
