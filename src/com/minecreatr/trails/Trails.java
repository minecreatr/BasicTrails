package com.minecreatr.trails;

import com.minecreatr.trails.command.CommandLoader;
import com.minecreatr.trails.command.CommandTrails;
import com.minecreatr.trails.command.CommandTrailsGui;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created on 9/2/2014
 */
public class Trails extends JavaPlugin implements Listener {

    public static HashMap<UUID, Trail> effects = new HashMap<UUID, Trail>();
    public static final String prefix = "["+ ChatColor.RED+"Trails"+ChatColor.RESET+"] "+ChatColor.BLUE;
    public static HashMap<String, Trail> registeredTrails = new HashMap<String, Trail>();
    public static HashMap<UUID, Integer> crackedIds = new HashMap<UUID, Integer>();
    public static final String trailInventoryName = ChatColor.AQUA+"Trails";
    public static final String trailsKey = ChatColor.BLUE+""+ChatColor.ITALIC+"Trail";
    public static boolean perTrailPerm;
    public static final int max_distance = 160;
    private CommandLoader loader;
    Random random = new Random();

    @Override
    public void onEnable(){
        reloadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        loader = new CommandLoader(this);
        loader.registerCommand(new CommandTrails());
        loader.registerCommand(new CommandTrailsGui());
        Trail.registerTrails(registeredTrails);
        if (!getConfigFile().exists()){
            getConfig().set("perTrailPerm", false);
            saveConfig();
        }
        if (getConfig().contains("perTrailPerm")){
            perTrailPerm=getConfig().getBoolean("perTrailPerm");
        }
        else {
            perTrailPerm=false;
        }
    }

    public File getConfigFile() {
        try {
            Field field = JavaPlugin.class.getDeclaredField("configFile");
            field.setAccessible(true);
            return (File)field.get(this);
        } catch (Exception exception) {
            exception.printStackTrace();
            getLogger().severe("Error getting config file!");
            return null;
        }
    }
    @Override
    public void onDisable(){
    }

    //To get around ide bug :P
    public static Player[] getOnlinePlayers(){
        try {
            Method m = Bukkit.class.getDeclaredMethod("getOnlinePlayers");
            m.setAccessible(true);
            return (Player[])m.invoke(null);
        } catch (Exception exception){
            return new Player[0];
        }
    }

    //Gets all online players as a string list
    public static List<String> getOnlinePlayersAsStrings(){
        Player[] players = getOnlinePlayers();
        List<String> list = new ArrayList<String>();
        for (Player player : players){
            list.add(player.getName());
        }
        return list;
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent event){
        if (effects.containsKey(event.getPlayer().getUniqueId())){
            Player player = event.getPlayer();
            Location loc = player.getLocation();
            for (Player curPlayer : getOnlinePlayers()){
                if (player.getWorld().equals(curPlayer.getWorld())) {
                    if (getDistance(loc, curPlayer.getLocation())<=max_distance) {
                        if (effects.get(player.getUniqueId()) == Trail.crack) {
                            if (crackedIds.containsKey(player.getUniqueId())) {
                                if (crackedIds.get(player.getUniqueId()) != 0) {
                                    sendBlockBreakToPlayer(curPlayer, loc, 0, 0, 0, random.nextInt(16), 5, crackedIds.get(player.getUniqueId()), 0);
                                }
                            }
                        } else {
                            sendToPlayer(effects.get(player.getUniqueId()).getEffect(),curPlayer, loc, 0, 0, 0, random.nextInt(16), 5);
                        }
                    }
                }
            }
//            while (players.hasNext()){
//                Player curPlayer = players.next();
//                if (curPlayer.getWorld().getName().equals(player.getWorld().getName())){
//                    if (effects.get(player.getUniqueId())==Trail.crack){
//                        if (crackedIds.containsKey(player.getUniqueId())){
//                            if (crackedIds.get(player.getUniqueId())!=0){
//                                ParticleEffects.sendBlockBreakToPlayer(curPlayer, loc, 0, 0, 0, random.nextInt(16), 5, crackedIds.get(player.getUniqueId()));
//                                return;
//                            }
//                        }
//                    }
//                    effects.get(player.getUniqueId()).getEffect().sendToPlayer(curPlayer, loc, 0, 0, 0, random.nextInt(16), 5);
//                    return;
//                }
//            }
        }
    }

    public static int getDistance(Location loc1, Location loc2){
        return (int)Math.round(Math.sqrt(sqr(loc1.getBlockX()-loc2.getBlockX())+sqr(loc1.getBlockY()-loc2.getBlockY())+sqr(loc1.getBlockZ()-loc2.getBlockZ())));
    }

    private static int sqr(int num){
        return num*num;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event){
        effects.remove(event.getPlayer().getUniqueId());
    }

    public static void openTrailsGui(Player player){
        player.sendMessage(prefix+"Opening trails gui...");
        Inventory inv = Bukkit.createInventory(null, 27, trailInventoryName);

        inv.addItem(gti(Trail.star, Material.NETHER_STAR));
        inv.addItem(gti(Trail.spark, Material.FIREWORK));
        inv.addItem(gti(Trail.music, Material.NOTE_BLOCK));
        inv.addItem(gti(Trail.happy, Material.EMERALD));
        inv.addItem(gti(Trail.lava, Material.LAVA_BUCKET));
        inv.addItem(gti(Trail.ash, Material.INK_SACK, (short)8));
        inv.addItem(gti(Trail.water, Material.WATER_BUCKET));
        inv.addItem(gti(Trail.magic, Material.BREWING_STAND_ITEM));
        inv.addItem(gti(Trail.love, Material.INK_SACK, (short)1));
        inv.addItem(gti(Trail.dust, Material.REDSTONE));
        inv.addItem(gti(Trail.slime, Material.SLIME_BALL));
        inv.addItem(gti(Trail.ender, Material.EYE_OF_ENDER));
        inv.addItem(gti(Trail.knowledge, Material.ENCHANTMENT_TABLE));
        inv.addItem(gti(Trail.barrier, Material.BARRIER));
        inv.addItem(gti(Trail.step, Material.GRAVEL));
        inv.addItem(gti(Trail.voidTrail, Material.ENDER_PORTAL));
        inv.addItem(gti(Trail.potion, Material.GLOWSTONE_DUST));
        inv.addItem(gti(Trail.pop, Material.TNT));
        inv.addItem(gti(Trail.splash, Material.WATER_LILY));
        inv.addItem(gti(Trail.smoke, Material.INK_SACK, (short)7));
        inv.addItem(gti(Trail.snow, Material.SNOW_BALL));
        inv.addItem(gti(Trail.flame, Material.FIRE));
        inv.addItem(gti(Trail.angry, Material.REDSTONE_TORCH_ON));
        inv.addItem(gti(Trail.cloud, Material.WOOL));
        ItemStack clearStack = new ItemStack(Material.BARRIER);
        ItemMeta clearMeta = clearStack.getItemMeta();
        clearMeta.setDisplayName(ChatColor.RED+"Clear Trail");
        clearMeta.setLore(Arrays.asList(ChatColor.GRAY+"Cleares your current trail"));
        clearStack.setItemMeta(clearMeta);
        inv.setItem(26, clearStack);


        player.openInventory(inv);
    }

    //Generate Trail Item
    public static ItemStack gti(Trail trail, Material material){
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+StringUtils.capitalize(trail.getName()));
        List<String> lore = new ArrayList<String>();
        lore.add(trailsKey);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    //Generate Trail Item
    public static ItemStack gti(Trail trail, Material material, short d){
        ItemStack stack = new ItemStack(material, 1, d);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+StringUtils.capitalize(trail.getName()));
        List<String> lore = new ArrayList<String>();
        lore.add(trailsKey);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getInventory().getName().equals(trailInventoryName)){
            if (event.getCurrentItem().getItemMeta()!=null) {
                if (event.getCurrentItem().getItemMeta().getLore().contains(trailsKey)) {
                    HumanEntity entity = event.getWhoClicked();
                    if (entity instanceof Player) {
                        Bukkit.getServer().dispatchCommand((Player) entity, "trail " + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase()));
                        event.getWhoClicked().closeInventory();
                    }
                }
                else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"Clear Trail")){
                    HumanEntity entity = event.getWhoClicked();
                    if (entity instanceof Player) {
                        Bukkit.getServer().dispatchCommand((Player) entity, "trail clear");
                        event.getWhoClicked().closeInventory();
                    }
                }
            }
            event.setCancelled(true);
        }
    }

    public static void sendToPlayer(EnumParticle particle, Player player, Location location, float offsetX, float offsetY, float offsetZ, float data, int count) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float)location.getX(), (float)location.getY(), (float)location.getZ(),
                offsetX, offsetY, offsetZ, data, count);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendBlockBreakToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float pData, int count, int id, int data){
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.BLOCK_CRACK, true, (float)location.getX(), (float)location.getY(), (float)location.getZ(),
                offsetX, offsetY, offsetZ, pData, count, new int[] { id | (data << 12) });
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
