package com.viktor.quickCamp;

import com.viktor.quickCamp.commands.CampCommand;
import com.viktor.quickCamp.commands.CampRemove;
import com.viktor.quickCamp.utils.CampGUI;
import com.viktor.quickCamp.utils.BlocksLocationList;
import com.viktor.quickCamp.utils.ConfigsInitialize;
import com.viktor.quickCamp.utils.LocatedCampPDC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

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
                   CampCommand campCommand = new CampCommand(player, plugin, ci);
                   campCommand.campPlace();
                   return true;
               }
               if(strings[0].equalsIgnoreCase("gui")){
                   CampGUI cg = new CampGUI();
                   cg.gui(player, plugin);
               }else if(strings[0].equalsIgnoreCase("remove")){
                    CampRemove campRemove = new CampRemove(player, bll.getCampLocation(), plugin, ci);
                    campRemove.removeCamp();
               }else if(strings[0].equalsIgnoreCase("location")){
                    if(lc.isCamping()) {
                        player.sendMessage(ChatColor.GREEN + "Your camp located at: " + lc.getCampLocation());
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have any camp set!");
                    }

               }
           }
            bll.blockLocations(player);

        }
        return false;

    }
}
