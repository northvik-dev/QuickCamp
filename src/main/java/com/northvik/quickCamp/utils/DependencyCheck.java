package com.northvik.quickCamp.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DependencyCheck {


    boolean isTowny;
    boolean isGriefPrevention;

    public void toggle() {
        Plugin towny = Bukkit.getPluginManager().getPlugin("Towny");
        if (towny != null && towny.isEnabled()) {
            Bukkit.getLogger().info("Towny found and enabled.");
            setTowny(true);
        } else {
            Bukkit.getLogger().info("Towny is not installed or disabled.");
            setTowny(false);
        }

        Plugin griefPrevention = Bukkit.getPluginManager().getPlugin("GriefPrevention");
        if (griefPrevention != null && griefPrevention.isEnabled()) {
            Bukkit.getLogger().info("GriefPrevention found and enabled.");
            setGriefPrevention(true);
        } else {
            Bukkit.getLogger().info("GriefPrevention is not installed or disabled.");
            setGriefPrevention(false);
        }
    }

    public void setTowny(boolean towny) {
        isTowny = towny;
    }

    public boolean isTowny(){
        return isTowny;
    }

    public boolean isGriefPrevention() {
        return isGriefPrevention;
    }

    public void setGriefPrevention(boolean griefPrevention) {
        isGriefPrevention = griefPrevention;
    }

}
