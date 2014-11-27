package com.minecreatr.trails.command;

import com.minecreatr.trails.Trails;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 9/5/2014
 *
 * @author minecreatr
 */
public class CommandTrailsGui extends AbstractCommand{

    public CommandTrailsGui(){
        super("trails");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if ((sender instanceof Player && sender.hasPermission("trails.trail"))){
            Trails.openTrailsGui((Player)sender);
            return true;
        }
        else {
        	if(!(sender instanceof Player))
        		sender.sendMessage(Trails.prefix+"Sender must be player!");
            if((sender instanceof Player && !(sender.hasPermission("trails.trail"))))
            	sender.sendMessage(Trails.prefix+"You dont have permission to use trails!");
        	return true;
        }
    }
}
