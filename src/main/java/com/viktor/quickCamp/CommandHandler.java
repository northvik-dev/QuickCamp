package com.viktor.quickCamp;

import com.viktor.quickCamp.commands.CampCommand;
import com.viktor.quickCamp.listeners.CampGUI;
import com.viktor.quickCamp.utils.BlocksLocationList;
import com.viktor.quickCamp.utils.ConfigsInitialize;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

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


           if (command.getName().equalsIgnoreCase("camp")){
               if (strings.length == 0) {
                   CampCommand campCommand = new CampCommand();
                   campCommand.campPlace(player);
                   return true;
               }
               if(strings[0].equalsIgnoreCase("gui")){
                   CampGUI cg = new CampGUI();
                   cg.gui(player);
               } else
               if (strings[0].equalsIgnoreCase("slots")){

                   List<String> slots =  ci.getYmlConfig().getStringList("placingArea.placing-slots-rows");

                   for (String row : slots){
                      player.sendMessage(row);
                   }
               }
           }
            bll.bloclocations(player);

        }
        return false;

    }
}
