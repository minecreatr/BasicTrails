package com.minecreatr.trails;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on 9/4/2014
 *
 * @author minecreatr
 */
public class Trail {

    public static final Trail star = rt(new Trail(ParticleEffects.MAGIC_CRIT, "star"));;
    public static final Trail spark = rt(new Trail(ParticleEffects.FIREWORKS_SPARK, "spark"));
    public static final Trail music = rt(new Trail(ParticleEffects.NOTE, "music"));
    public static final Trail happy = rt(new Trail(ParticleEffects.HAPPY_VILLAGER, "happy"));
    public static final Trail lava = rt(new Trail(ParticleEffects.DRIP_LAVA, "lava"));
    public static final Trail ash = rt(new Trail(ParticleEffects.LAVA, "ash"));
    public static final Trail water = rt(new Trail(ParticleEffects.DRIP_WATER, "water"));
    public static final Trail magic = rt(new Trail(ParticleEffects.WITCH_MAGIC, "magic"));
    public static final Trail love = rt(new Trail(ParticleEffects.HEART, "love"));
    public static final Trail dust = rt(new Trail(ParticleEffects.RED_DUST, "dust"));
    public static final Trail slime = rt(new Trail(ParticleEffects.SLIME, "slime"));
    public static final Trail ender = rt(new Trail(ParticleEffects.PORTAL, "ender"));
    public static final Trail knowledge = rt(new Trail(ParticleEffects.ENCHANTMENT_TABLE, "knowledge"));
    public static final Trail step = rt(new Trail(ParticleEffects.FOOTSTEP, "step"));
    public static final Trail voidTrail = rt(new Trail(ParticleEffects.TOWN_AURA, "void"));
    public static final Trail potion = rt(new Trail(ParticleEffects.MOB_SPELL, "potion"));
    public static final Trail pop = rt(new Trail(ParticleEffects.EXPLODE, "pop"));
    public static final Trail splash = rt(new Trail(ParticleEffects.SPLASH, "splash"));
    public static final Trail smoke = rt(new Trail(ParticleEffects.LARGE_SMOKE, "smoke"));
    public static final Trail snow = rt(new Trail(ParticleEffects.SNOWBALL_POOF, "snow"));
    public static final Trail flame = rt(new Trail(ParticleEffects.FLAME, "flame"));
    public static final Trail angry = rt(new Trail(ParticleEffects.ANGRY_VILLAGER, "angry"));
    public static final Trail crack = rt(new Trail(ParticleEffects.CRACK, "crack"));


        //public static final Trail guardian = rt(new Trail(ParticleEffects.GUARDIAN, "guardian"));
    private static ArrayList<Trail> toRegister;


    public static Trail rt(Trail trail){
        if (toRegister==null){
            toRegister = new ArrayList<Trail>();
        }
        toRegister.add(trail);
        return trail;
    }

    private ParticleEffects effect;
    private String name;

    public Trail(ParticleEffects e, String n){
        this.effect=e;
        name=n;
    }

    public ParticleEffects getEffect(){
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
