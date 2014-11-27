package com.minecreatr.trails.command;


import com.minecreatr.trails.Trails;
import com.minecreatr.trails.Trail;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/2/2014
 */
public class CommandTrails extends AbstractCommand{

    public CommandTrails(){
        super("trail");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("trails.trail")&&!Trails.perTrailPerm){
                player.sendMessage(Trails.prefix+ "You dont have permission to use trails!");
                return true;
            }
            if (args.length < 1) {
                player.sendMessage(Trails.prefix+"The correct use is /trail <trail>");
                return true;
            }
            if (Trails.perTrailPerm){
                String trail = args[0];
                if (Trails.registeredTrails.containsKey(trail)){
                    if (!player.hasPermission("trails."+trail) && Trails.perTrailPerm){
                        player.sendMessage(Trails.prefix+"You dont have permission to use the trail "+trail+"!");
                        return true;
                    }
                }
                else if (!trail.equalsIgnoreCase("clear")){
                    player.sendMessage(Trails.prefix+"No Trail with the name " + args[0]);
                }
            }
            if (args[0].equals("crack")){
                if (args.length<2){
                    player.sendMessage(Trails.prefix+"The correct use is /trail crack <block id>");
                    return true;
                }
                int id;
                try {
                    id=Integer.parseInt(args[1]);
                } catch(NumberFormatException exception){
                    player.sendMessage(Trails.prefix+"Id must be an int!");
                    return true;
                }
                if (id==0){
                    player.sendMessage(Trails.prefix+"Id cant be 0!");
                    return true;
                }
                Trails.effects.put(player.getUniqueId(), Trail.crack);
                Trails.crackedIds.put(player.getUniqueId(), id);
                player.sendMessage(Trails.prefix+"Enabled crack trail with block id "+args[1]+" , do /trail clear to clear");
                return true;
            }
            if (args[0].equalsIgnoreCase("clear")) {
                Trails.effects.remove(player.getUniqueId());
                player.sendMessage(Trails.prefix + "Removed trail!");
                return true;
            } else if (Trails.registeredTrails.containsKey(args[0])) {
                if (Trails.effects.containsKey(player.getUniqueId())) {
                    if (Trails.effects.get(player.getUniqueId()).getName().equals(args[0])) {
                        Trails.effects.remove(player.getUniqueId());
                        player.sendMessage(Trails.prefix + "Disabled " + args[0] + " trail!");
                        return true;
                    }
                }
                Trails.effects.put(player.getUniqueId(), Trails.registeredTrails.get(args[0]));
                player.sendMessage(Trails.prefix+"Activated " + args[0] + " trail");
                return true;
            }
            else {
                return true;
            }
        }
        else {
            sender.sendMessage(Trails.prefix+"Sender must be a player!");
            return true;
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length==1) {
            if (Trails.registeredTrails.size() == 0) {
                return null;
            }
            List<String> l = super.toList(Trails.registeredTrails.keySet());
            l.add("clear");
            return filterStartsWith(l, args[0]);
        }
        else {
            return null;
        }
    }

    //returns a list of only string that start with the filter
    public static List<String> filterStartsWith(List<String> in, String filter){
        List<String> out = new ArrayList<String>();
        for (int i=0;i<in.size();i++){
            if (in.get(i).startsWith(filter)){
                out.add(in.get(i));
            }
        }
        return out;
    }




}
