package com.northvik.quickCamp;

import com.northvik.quickCamp.GUI.MainMenu;
import com.northvik.quickCamp.commands.CampPlace;
import com.northvik.quickCamp.commands.CampRemove;
import com.northvik.quickCamp.managers.BlocksLocationList;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.managers.LocatedCampPDC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor {
    private final QuickCamp plugin;
    ConfigsInitialize ci;
    int campSize;
    public CommandHandler (QuickCamp plugin){
        this.plugin = plugin;
        this.ci = new ConfigsInitialize(plugin);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){

            Player player = (Player) sender;
            BlocksLocationList bll = new BlocksLocationList();
            LocatedCampPDC lc = new LocatedCampPDC(player,bll.getCampLocation(),plugin);
            CampPlace campPlace = new CampPlace(player, plugin, ci);
            MainMenu cm = new MainMenu();
            CampRemove campRemove = new CampRemove(player, bll.getCampLocation(), plugin, ci);

            if (command.getName().equalsIgnoreCase("camp")) {
                if (strings.length == 0) {
                    if (isPermission(player, "quickcamp.camp")) {
                        player.sendMessage(ChatColor.YELLOW + "Usage: /camp <template | gui | remove | location>");
                        return true;
                    }
                } else {
                    String subCommand = strings[0];
                    List<String> templateNames = new ArrayList<>(ci.getTemplateNames());
                    if (templateNames.contains(subCommand)) {
                        if (isPermission(player, "quickcamp.camp")) {
                            campPlace.campPlace(subCommand);
                            campSize = ci.getCampTemplateSize(subCommand);
                        }
                    } else if (subCommand.equalsIgnoreCase("gui")) {
                        if (isPermission(player, "quickcamp.camp.gui")) {
                            cm.menu(player, plugin);
                        }
                    } else if (subCommand.equalsIgnoreCase("remove")) {
                        if (isPermission(player, "quickcamp.camp")) {
                            campRemove.removeCamp();
                        }
                    } else if (subCommand.equalsIgnoreCase("location")) {
                        if (isPermission(player, "quickcamp.camp")) {
                            if (lc.isCamping()) {
                                player.sendMessage(ChatColor.GREEN + "Your camp located at: " + lc.getCampLocation());
                            } else {
                                player.sendMessage(ChatColor.RED + "You don't have any camp set!");
                            }
                        }
                    }
                }
            }
            player.sendMessage("camp size " + campSize);
            bll.blockLocations(player, plugin, campSize );
        }
        return false;
    }

    public boolean isPermission (Player player, String permission){
        if (!player.hasPermission(permission)){
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command");
            return false;
        }
        return true;
    }

}
