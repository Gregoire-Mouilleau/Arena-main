package fr.ariloxe.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class EnferWorld extends SpecialWorld {

    public EnferWorld() {
        super("§4§lEnfer", "Enfer");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, -495, 59, 485));
        spawnLocations.add(new Location(world, -526, 61, 416));
        spawnLocations.add(new Location(world, -564, 51, 524));
        spawnLocations.add(new Location(world, -455, 51, 578));
        spawnLocations.add(new Location(world, -273, 70, 478));
        spawnLocations.add(new Location(world, -355, 54, 403));

        super.setArenaLocations(spawnLocations);
    }



}
