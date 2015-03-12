package com.minecreatr.trails;


import net.minecraft.server.v1_8_R2.EnumParticle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on 9/4/2014
 *
 * @author minecreatr
 */
public class Trail {

    public static final Trail star = rt(new Trail(EnumParticle.CRIT_MAGIC, "star"));;
    public static final Trail spark = rt(new Trail(EnumParticle.FIREWORKS_SPARK, "spark"));
    public static final Trail music = rt(new Trail(EnumParticle.NOTE, "music"));
    public static final Trail happy = rt(new Trail(EnumParticle.VILLAGER_HAPPY, "happy"));
    public static final Trail lava = rt(new Trail(EnumParticle.DRIP_LAVA, "lava"));
    public static final Trail ash = rt(new Trail(EnumParticle.LAVA, "ash"));
    public static final Trail water = rt(new Trail(EnumParticle.DRIP_WATER, "water"));
    public static final Trail magic = rt(new Trail(EnumParticle.SPELL_WITCH, "magic"));
    public static final Trail love = rt(new Trail(EnumParticle.HEART, "love"));
    public static final Trail dust = rt(new Trail(EnumParticle.REDSTONE, "dust"));
    public static final Trail slime = rt(new Trail(EnumParticle.SLIME, "slime"));
    public static final Trail ender = rt(new Trail(EnumParticle.PORTAL, "ender"));
    public static final Trail knowledge = rt(new Trail(EnumParticle.ENCHANTMENT_TABLE, "knowledge"));
    public static final Trail voidTrail = rt(new Trail(EnumParticle.TOWN_AURA, "void"));
    public static final Trail step = rt(new Trail(EnumParticle.FOOTSTEP, "step"));
    public static final Trail potion = rt(new Trail(EnumParticle.SPELL_MOB, "potion"));
    public static final Trail pop = rt(new Trail(EnumParticle.EXPLOSION_NORMAL, "pop"));
    public static final Trail splash = rt(new Trail(EnumParticle.WATER_SPLASH, "splash"));
    public static final Trail smoke = rt(new Trail(EnumParticle.SMOKE_LARGE, "smoke"));
    public static final Trail snow = rt(new Trail(EnumParticle.SNOWBALL, "snow"));
    public static final Trail flame = rt(new Trail(EnumParticle.FLAME, "flame"));
    public static final Trail angry = rt(new Trail(EnumParticle.VILLAGER_ANGRY, "angry"));
    public static final Trail crack = rt(new Trail(EnumParticle.BLOCK_CRACK, "crack"));
    public static final Trail cloud = rt(new Trail(EnumParticle.CLOUD, "cloud"));
    public static final Trail barrier = rt(new Trail(EnumParticle.BARRIER, "barrier"));


        //public static final Trail guardian = rt(new Trail(ParticleEffects.GUARDIAN, "guardian"));
    private static ArrayList<Trail> toRegister;


    public static Trail rt(Trail trail){
        if (toRegister==null){
            toRegister = new ArrayList<Trail>();
        }
        toRegister.add(trail);
        return trail;
    }

    private EnumParticle effect;
    private String name;

    public Trail(EnumParticle e, String n){
        this.effect=e;
        name=n;
    }

    public EnumParticle getEffect(){
        return this.effect;
    }

    public String getName(){
        return this.name;
    }

    public static void registerTrails(HashMap<String, Trail> register){
        for (int i=0;i<toRegister.size();i++){
            register.put(toRegister.get(i).getName(), toRegister.get(i));
        }
    }
}
