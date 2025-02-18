package com.viktor.quickCamp;

import com.viktor.quickCamp.commands.CampCommand;
import com.viktor.quickCamp.utils.BlocksLocationList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            BlocksLocationList bll = new BlocksLocationList();

           if (command.getName().equalsIgnoreCase("camp")){
               if (strings.length == 0) {
                   CampCommand campCommand = new CampCommand();
                   campCommand.campPlace(player);
               }


           }
            bll.bloclocations(player);
        }
        return false;
    }
}
