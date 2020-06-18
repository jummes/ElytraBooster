package com.github.jummes.elytrabooster.manager;

import com.github.jummes.elytrabooster.spawner.Spawner;
import com.github.jummes.libs.model.ModelManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Handles data of the spawners
 *
 * @author Marco
 */
@Getter
public class SpawnerManager extends ModelManager<Spawner> {

    private List<Spawner> spawners;

    public SpawnerManager(Class<Spawner> classObject, String databaseType, JavaPlugin plugin) {
        super(classObject, databaseType, plugin);
        this.spawners = database.loadObjects();
    }

    public void reloadData() {
        spawners.forEach(Spawner::stopBoosterTask);
        this.spawners = database.loadObjects();
    }


}
