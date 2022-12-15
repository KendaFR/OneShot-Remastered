package fr.kenda.oneshot.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationsUtils {

    public static String locationParse(final Location location) {
        return location.getWorld().getName() + "," +
                (Math.round(location.getX()) + 0.5) + "," + Math.round(location.getY()) + "," + (Math.round(location.getZ()) + 0.5) + "," +
                Math.round(location.getYaw()) + "," + Math.round(location.getPitch());
    }

    public static String[] getArgumentsLocation(String locationParsed){
        return locationParsed.trim().split(",");
    }
    public static Location getParsedLocation(final String string) {
        final String[] args = string.trim().split(",");
        final World world = Bukkit.getWorld(args[0]);
        final double x = Double.parseDouble(args[1]);
        final double y = Double.parseDouble(args[2]);
        final double z = Double.parseDouble(args[3]);
        final float yaw = Float.parseFloat(args[4]);
        final float pitch = Float.parseFloat(args[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }
}
