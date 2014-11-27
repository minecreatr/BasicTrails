package com.minecreatr.trails;

import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public enum ParticleEffects {

    HUGE_EXPLOSION("hugeexplosion"),
    LARGE_EXPLODE("largeexplode"),
    FIREWORKS_SPARK("fireworksSpark"),
    BUBBLE("bubble"),
    SUSPEND("suspended"),
    DEPTH_SUSPEND("depthSuspend"),
    TOWN_AURA("townaura"),
    CRIT("crit"),
    MAGIC_CRIT("magicCrit"),
    MOB_SPELL("mobSpell"),
    MOB_SPELL_AMBIENT("mobSpellAmbient"),
    SPELL("spell"),
    INSTANT_SPELL("instantSpell"),
    WITCH_MAGIC("witchMagic"),
    NOTE("note"),
    PORTAL("portal"),
    ENCHANTMENT_TABLE("enchantmenttable"),
    EXPLODE("explode"),
    FLAME("flame"),
    LAVA("lava"),
    FOOTSTEP("footstep"),
    SPLASH("splash"),
    LARGE_SMOKE("largesmoke"),
    CLOUD("cloud"),
    RED_DUST("reddust"),
    SNOWBALL_POOF("snowballpoof"),
    DRIP_WATER("dripWater"),
    DRIP_LAVA("dripLava"),
    SNOW_SHOVEL("snowshovel"),
    SLIME("slime"),
    HEART("heart"),
    ANGRY_VILLAGER("angryVillager"),
    HAPPY_VILLAGER("happyVillager"),
    ICONCRACK("iconcrack_"),
    TILECRACK("tilecrack_"),
    GUARDIAN("mobappearance"),
    CRACK("");

    private String particleName;

    ParticleEffects(String particleName) {
        this.particleName = particleName;
    }

    public void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();
        ReflectionHelper.setValue(packet, "a", particleName);
        ReflectionHelper.setValue(packet, "b", (float) location.getX());
        ReflectionHelper.setValue(packet, "c", (float) location.getY());
        ReflectionHelper.setValue(packet, "d", (float) location.getZ());
        ReflectionHelper.setValue(packet, "e", offsetX);
        ReflectionHelper.setValue(packet, "f", offsetY);
        ReflectionHelper.setValue(packet, "g", offsetZ);
        ReflectionHelper.setValue(packet, "h", speed);
        ReflectionHelper.setValue(packet, "i", count);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendBlockBreakToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count, int id, int data){
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();
        String n = "blockcrack_"+id+"_"+data;
        ReflectionHelper.setValue(packet, "a", n);
        ReflectionHelper.setValue(packet, "b", (float) location.getX());
        ReflectionHelper.setValue(packet, "c", (float) location.getY());
        ReflectionHelper.setValue(packet, "d", (float) location.getZ());
        ReflectionHelper.setValue(packet, "e", offsetX);
        ReflectionHelper.setValue(packet, "f", offsetY);
        ReflectionHelper.setValue(packet, "g", offsetZ);
        ReflectionHelper.setValue(packet, "h", speed);
        ReflectionHelper.setValue(packet, "i", count);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}