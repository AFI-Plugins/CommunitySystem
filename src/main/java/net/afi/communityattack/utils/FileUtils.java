package net.afi.communityattack.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class FileUtils {

    public static Location getLocationFromString(String locationString) {

        String[] locArr = locationString.split(";");

        Location location = new Location(Bukkit.getWorld(locArr[0]), Double.valueOf(locArr[1]), Double.valueOf(locArr[2]), Double.valueOf(locArr[3]));
        if(locArr.length > 4) {
            location.setYaw(Float.valueOf(locArr[4]));
            location.setPitch(Float.valueOf(locArr[5]));
        }
        return location;
    }

    public static String getStringOfLocation(Location location, boolean detailed) {

        String str = location.getWorld().getName() + ";";

        if(detailed) {
            str += location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
        }else {
            str += location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
        }
        return str;
    }

}
