package com.viktor.quickCamp;

import com.viktor.quickCamp.listeners.GuiMenuListener;
import com.viktor.quickCamp.utils.CampGUI;
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

    }

    @Override
    public void onDisable() {

    }

    public QuickCamp getPlugin (){
        return main;
    }
}
