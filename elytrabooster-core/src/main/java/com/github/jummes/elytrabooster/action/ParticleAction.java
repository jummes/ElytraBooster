package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.targeter.LocationTarget;
import com.github.jummes.elytrabooster.action.targeter.Target;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Enumerable.Child
@Enumerable.Displayable(name = "&c&LParticle", description = "gui.action.particle.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQ2MWQ5ZDA2YzBiZjRhN2FmNGIxNmZkMTI4MzFlMmJlMGNmNDJlNmU1NWU5YzBkMzExYTJhODk2NWEyM2IzNCJ9fX0=")
public class ParticleAction extends AbstractAction {

    private static final String TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY4NDczNWZjOWM3NjBlOTVlYWYxMGNlYzRmMTBlZGI1ZjM4MjJhNWZmOTU1MWVlYjUwOTUxMzVkMWZmYTMwMiJ9fX0=";
    private static final String COUNT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";
    private static final String OFFSET_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDI4OTIyMTdjZThmYTg0MTI4YWJlMjY0YjVlNzFkN2VlN2U2YTlkNTgyMzgyNThlZjdkMmVmZGMzNDcifX19";
    private static final String SPEED_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmYzUyMjY0ZDhhZDllNjU0ZjQxNWJlZjAxYTIzOTQ3ZWRiY2NjY2Y2NDkzNzMyODliZWE0ZDE0OTU0MWY3MCJ9fX0=";
    private static final String FORCE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==";


    @Serializable(headTexture = TYPE_HEAD, stringValue = true, description = "gui.action.particle.type", fromListMapper = "particlesMapper", fromList = "getParticles")
    private Particle type;
    @Serializable(headTexture = COUNT_HEAD, description = "gui.action.particle.count")
    @Serializable.Number(minValue = 0)
    private int count;
    @Serializable(headTexture = OFFSET_HEAD, description = "gui.action.particle.offset")
    @Serializable.Number(minValue = 0)
    private double offset;
    @Serializable(headTexture = SPEED_HEAD, description = "gui.action.particle.speed")
    @Serializable.Number(minValue = 0)
    private double speed;
    @Serializable(headTexture = FORCE_HEAD, description = "gui.action.particle.force")
    private boolean force;

    public ParticleAction() {
        this(Particle.FIREWORKS_SPARK, 1, 0, 0, false);
    }

    public static ParticleAction deserialize(Map<String, Object> map) {
        Particle type = Particle.valueOf((String) map.get("type"));
        int count = (int) map.get("count");
        double offset = (double) map.get("offset");
        double speed = (double) map.get("speed");
        boolean force = (boolean) map.get("force");
        return new ParticleAction(type, count, offset, speed, force);
    }

    public static List<Object> getParticles(ModelPath path) {
        return Lists.newArrayList(Particle.values());
    }

    public static Function<Object, ItemStack> particlesMapper() {
        return obj -> {
            Particle type = (Particle) obj;
            return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQ2MWQ5ZDA2YzBiZjRhN2FmNGIxNmZkMTI4MzFlMmJlMGNmNDJlNmU1NWU5YzBkMzExYTJhODk2NWEyM2IzNCJ9fX0=")
                    , type.name(), new ArrayList<>());
        };
    }

    @Override
    public void execute(Target target) {
        ((LocationTarget) target).getTarget().getWorld().spawnParticle(type, ((LocationTarget) target).getTarget(), count, offset, offset, offset, speed, null, force);
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(LocationTarget.class);
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue(TYPE_HEAD),
                "&6&lParticle: &c" + WordUtils.capitalize(type.toString()), Libs.getLocale().getList("gui.action.description"));
    }

}
