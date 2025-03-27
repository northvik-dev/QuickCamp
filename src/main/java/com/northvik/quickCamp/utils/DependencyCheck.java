package com.northvik.quickCamp.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class DependencyCheck {


    boolean isTowny;

    public void toggle(){

        String pluginName = "Towny";

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        if (plugin != null && plugin.isEnabled()) {
            Bukkit.getLogger().info(pluginName + " found toggle dependency.");
            setTowny(true);
        } else {
            Bukkit.getLogger().info(pluginName + " is not installed! Skipping...");
            setTowny(false);
        }
    }
    public void setTowny(boolean towny) {
        isTowny = towny;
    }

    public boolean isTowny(){
        return isTowny;
    }
}
