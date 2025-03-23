package com.northvik.quickCamp;

import com.northvik.quickCamp.GUI.MainMenu;
import com.northvik.quickCamp.commands.CampPlace;
import com.northvik.quickCamp.commands.CampRemove;
import com.northvik.quickCamp.managers.BlocksLocationList;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.managers.LocatedCampPDC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
                        player.sendMessage(ChatColor.YELLOW + "Usage:"+
                                "\n\n /camp remove" +
                                "\n\n /camp location");
                        return true;
                    }
                } else {
                    String subCommand = strings[0];
                    List<String> templateNames = new ArrayList<>(ci.getTemplateNames());
                    if (subCommand.equalsIgnoreCase("place")) {
                        if(templateNames.contains(strings[1])) {
                            if (isPermission(player, "quickcamp.place")) {
                                campPlace.campPlace(strings[1]);
                                campSize = ci.getCampTemplateSize(strings[1]);
                            }
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
                                player.sendMessage(ChatColor.DARK_GREEN + "Your camp located at: " + ChatColor.GREEN + lc.getCampLocation());
                            } else {
                                player.sendMessage(ChatColor.RED + "You don't have any camp set!");
                            }
                        }
                    }else if (subCommand.equalsIgnoreCase("help")) {
                        if (isPermission(player, "quickcamp.camp.help")) {
                            player.sendMessage(ChatColor.GREEN + "\n\n---------[QuickCamp] Usage:---------" +
                                    ChatColor.YELLOW+"\n\n /camp place <template_name> - place a camp" +
                                    "\n\n /camp gui - open GUI menu" +
                                    "\n\n /camp remove - removing camp" +
                                    "\n\n /camp location - send the location of placed camp");

                            TextComponent msg = new TextComponent(ChatColor.LIGHT_PURPLE + "\n Have question or need help?");
                            TextComponent end = new TextComponent(ChatColor.GREEN +"\n----------------------------------");
                            TextComponent discord = new TextComponent(ChatColor.AQUA+"\n --> Click to join our Discord <--");
                            discord.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/jT8X9faerT"));
                            discord.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("https://discord.gg/jT8X9faerT")));
                            TextComponent documentation = new TextComponent(ChatColor.BLUE+"\n --> Open documentation <--");
                            documentation.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/northvik-dev/QuickCamp/wiki"));
                            documentation.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("https://github.com/northvik-dev/QuickCamp/wiki")));
                            msg.addExtra(discord);
                            msg.addExtra(documentation);
                            msg.addExtra(end);
                            player.spigot().sendMessage(msg);
                        }
                    }
                }
            }
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
