package com.minecreatr.trails;

import com.minecreatr.mcocore.ClassInitializationException;
import com.minecreatr.mcocore.MCOCore;
import com.minecreatr.mcocore.command.CommandLoader;
import com.minecreatr.trails.command.CommandTrails;
import com.minecreatr.trails.trail.Trail;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

/**
 * Created on 9/2/2014
 */
public class Trails extends JavaPlugin implements Listener {

    public static HashMap<UUID, Trail> effects = new HashMap<UUID, Trail>();
    public static final String prefix = "["+ ChatColor.RED+"Trails"+ChatColor.RESET+"] "+ChatColor.BLUE;
    public static HashMap<String, Trail> registeredTrails = new HashMap<String, Trail>();
    public static HashMap<UUID, Integer> crackedIds = new HashMap<UUID, Integer>();
    private CommandLoader loader;
    Random random = new Random();

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
        loader = new CommandLoader(this);
        loader.registerCommand(new CommandTrails());
        Trail.registerTrails(registeredTrails);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent event){
        if (effects.containsKey(event.getPlayer().getUniqueId())){
            Player player = event.getPlayer();
            Location loc = player.getLocation();
            Iterator<? extends Player> players = this.getServer().getOnlinePlayers().iterator();
            while (players.hasNext()){
                Player curPlayer = players.next();
                if (curPlayer.getWorld().getName().equals(player.getWorld().getName())){
                    if (effects.get(player.getUniqueId())==Trail.crack){
                        if (crackedIds.containsKey(player.getUniqueId())){
                            if (crackedIds.get(player.getUniqueId())!=0){
                                ParticleEffects.sendBlockBreakToPlayer(curPlayer, loc, 0, 0, 0, random.nextInt(16), 5, crackedIds.get(player.getUniqueId()));
                                return;
                            }
                        }
                    }
                    effects.get(player.getUniqueId()).getEffect().sendToPlayer(curPlayer, loc, 0, 0, 0, random.nextInt(16), 5);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event){
        effects.remove(event.getPlayer().getUniqueId());
    }
}
