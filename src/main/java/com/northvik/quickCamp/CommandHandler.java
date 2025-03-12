package com.northvik.quickCamp;

import com.northvik.quickCamp.commands.CampCommand;
import com.northvik.quickCamp.commands.CampRemove;
import com.northvik.quickCamp.managers.BlocksLocationList;
import com.northvik.quickCamp.managers.CampGUI;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.managers.LocatedCampPDC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    private final QuickCamp plugin;

    public CommandHandler (QuickCamp plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){

            Player player = (Player) sender;
            BlocksLocationList bll = new BlocksLocationList();
            ConfigsInitialize ci = new ConfigsInitialize(plugin);
            LocatedCampPDC lc = new LocatedCampPDC(player,bll.getCampLocation(),plugin);
           if (command.getName().equalsIgnoreCase("camp")){
               if (strings.length == 0) {
                 if (isPermission(player, "quickcamp.camp")){
                       CampCommand campCommand = new CampCommand(player, plugin, ci);
                       campCommand.campPlace();
                       return true;
                   }
               }
               if(strings[0].equalsIgnoreCase("gui")){
                   if(isPermission(player, "quickcamp.camp.gui")) {
                       CampGUI cg = new CampGUI();
                       cg.gui(player, plugin);
                   }
               }else if(strings[0].equalsIgnoreCase("remove")){
                   if(isPermission(player, "quickcamp.camp")){
                       CampRemove campRemove = new CampRemove(player, bll.getCampLocation(), plugin, ci);
                       campRemove.removeCamp();
                   }
               }else if(strings[0].equalsIgnoreCase("location")){
                   if (isPermission(player,"quickcamp.camp")){
                       if (lc.isCamping()) {
                           player.sendMessage(ChatColor.GREEN + "Your camp located at: " + lc.getCampLocation());
                       } else {
                           player.sendMessage(ChatColor.RED + "You don't have any camp set!");
                       }
                   }

               }
           }
            bll.blockLocations(player);
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
