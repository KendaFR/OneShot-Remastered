package fr.kenda.oneshot.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@SuppressWarnings({"all"})
public class LocationsUtils {

    /**
     * Get the string format of location
     *
     * @param location Location
     * @return Stirng
     */
    public static String locationParse(final Location location) {
        double x = ((int) Math.floor(location.getX())) + 0.5;
        double y = (int) Math.floor(location.getY());
        double z = ((int) Math.floor(location.getZ())) + 0.5;
        double yaw = (int) Math.floor(location.getYaw());
        double pitch = (int) Math.floor(location.getPitch());

        return location.getWorld().getName() + "," + x + "," + y + "," + z + "," + yaw + "," + pitch;
    }

    /**
     * Get the arguments of location string
     *
     * @param locationParsed String
     * @return String[]
     */
    public static String[] getArgumentsLocation(String locationParsed) {
        return locationParsed.trim().split(",");
    }

    /**
     * Get the Location from String
     *
     * @param string String
     * @return Location
     */
    public static Location getParsedLocation(final String string) {
        if (string == null) {
            return new Location(Bukkit.getWorld("world"), 0, 0, 0);
        }
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
