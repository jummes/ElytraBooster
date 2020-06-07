package com.github.jummes.elytrabooster.spawner.entityholder.entity;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import lombok.Getter;
import org.bukkit.Location;

@Getter
@Enumerable(classArray = {FireworkEntityDescription.class, MushroomEntityDescription.class,
        PotionEntityDescription.class})
public abstract class EntityDescription implements Model, Cloneable {
    protected ElytraBooster plugin;

    public EntityDescription() {
        this.plugin = ElytraBooster.getInstance();
    }

    public abstract void spawn(Location loc);

    public abstract void entityDespawn();

    @Override
    protected abstract Object clone() throws CloneNotSupportedException;

}