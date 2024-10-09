package fr.ariloxe.arena.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariloxe
 */
public class ForestWorld extends SpecialWorld {

    public ForestWorld() {
        super("§a§lForêt", "Forest");

        List<Location> spawnLocations = new ArrayList<>();
        World world = Bukkit.getWorld("arenas");
        spawnLocations.add(new Location(world, -1, 56, -3));
        spawnLocations.add(new Location(world, -52, 56, 7));
        spawnLocations.add(new Location(world, -127, 56, -3));
        spawnLocations.add(new Location(world, -90, 67, 6));
        spawnLocations.add(new Location(world, -60, 56, -45));

        super.setArenaLocations(spawnLocations);
    }



}
