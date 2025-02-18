package com.viktor.quickCamp;

import com.viktor.quickCamp.listeners.CampGUI;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuickCamp extends JavaPlugin implements Listener {

    private QuickCamp main;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("camp").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new CampGUI(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public QuickCamp getPlugin (){
        return main;
    }
}
