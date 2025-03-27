package com.northvik.quickCamp;

import com.northvik.quickCamp.listeners.BlockInteractionListener;
import com.northvik.quickCamp.listeners.GuiMenuListener;
import com.northvik.quickCamp.listeners.LinkedItemUseListener;
import com.northvik.quickCamp.utils.DependencyCheck;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuickCamp extends JavaPlugin implements Listener {

    private QuickCamp main;
    private final DependencyCheck dependencyCheck = new DependencyCheck();

    @Override
    public void onEnable() {
        main = this;
        dependencyCheck.toggle();
        Bukkit.getLogger().info("Dependency check for Towny completed: " + dependencyCheck.isTowny());

        Metrics metrics = new Metrics(this, 25164);
        getCommand("camp").setExecutor(new CommandHandler(this));
        Bukkit.getPluginManager().registerEvents(new GuiMenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockInteractionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new LinkedItemUseListener(this), this);

    }

    @Override
    public void onDisable() {

    }

    public DependencyCheck getDependencyCheck() {
        return dependencyCheck;
    }
    public QuickCamp getPlugin (){
        return main;
    }
}
