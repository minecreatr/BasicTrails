package com.minecreatr.trails.command;

import com.minecreatr.trails.ReflectionHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created on 10/26/2014
 *
 * @author minecreatr
 */
public class CommandLoader {

    public JavaPlugin plugin;
    private CommandMap cmap;

    public CommandLoader(JavaPlugin p){
        this.plugin=p;
        cmap = (CommandMap) ReflectionHelper.getValue(Bukkit.getServer(), "commandMap");
    }

    public void registerCommand(AbstractCommand command){
        cmap.register("", command);
    }
}