package com.github.jummes.elytrabooster.trail;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import com.github.jummes.libs.annotation.Serializable;

import net.md_5.bungee.api.ChatColor;

@SerializableAs("SimpleBoostTrail")
public class SimpleBoostTrail extends BoostTrail {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD, stringValue = true)
    private Particle particle;

    public SimpleBoostTrail(String particle) {
        try {
            if (particle == null) {
                particle = "FIREWORKS_SPARK";
            }
            this.particle = Particle.valueOf(particle.toUpperCase());
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning(ChatColor.RED + particle + " is not a particle, check portals.yml");
            this.particle = Particle.FIREWORKS_SPARK;
        }
    }

    public static SimpleBoostTrail deserialize(Map<String, Object> map) {
        String particle = (String) map.get("particle");
        return new SimpleBoostTrail(particle);
    }

    @Override
    public void spawnTrail(Player player) {
        player.getWorld().spawnParticle(particle, player.getLocation(), 3, 0.1, 0.1, 0.1, 0.1);
    }

    @Override
    public String getName() {
        return "simple";
    }

}
