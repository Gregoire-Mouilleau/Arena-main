package fr.ariloxe.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class OriginalWorld extends SpecialWorld {

    public OriginalWorld() {
        super("§b§Japon", "Originale");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, 527, 66, -520));
        spawnLocations.add(new Location(world, 534, 68, -475));
        spawnLocations.add(new Location(world, 527, 53, -432));
        spawnLocations.add(new Location(world, 588, 53, -525));
        spawnLocations.add(new Location(world, 490, 53, -562));

        super.setArenaLocations(spawnLocations);
    }



}
