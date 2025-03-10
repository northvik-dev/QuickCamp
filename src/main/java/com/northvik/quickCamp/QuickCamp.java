package com.northvik.quickCamp;

import com.northvik.quickCamp.listeners.BlockInteractionListener;
import com.northvik.quickCamp.listeners.GuiMenuListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuickCamp extends JavaPlugin implements Listener {

    private QuickCamp main;

    @Override
    public void onEnable() {
        main = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("camp").setExecutor(new CommandHandler(this));
        Bukkit.getPluginManager().registerEvents(new GuiMenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockInteractionListener(this), this);

    }

    @Override
    public void onDisable() {

    }

    public QuickCamp getPlugin (){
        return main;
    }
}
