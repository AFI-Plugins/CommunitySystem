package net.afi.communityattack.utils;

import net.afi.communityattack.CommunityAttack;
import org.bukkit.entity.Player;

public class UtilMethods {

    public static boolean isMayor(Player player) {
        return CommunityAttack.getInstance().getFileManager().getPrefixFile().getObject(player.getUniqueId().toString()) != null
                && CommunityAttack.getInstance().getFileManager().getPrefixFile().getString(player.getUniqueId().toString()).equals("mayor");
    }

    public static int getInventorySizeByCollectionSize(int listSize) {

        if(listSize == 0)return 9;
        if(listSize%9 == 0)return listSize;

        return listSize + (9-(listSize%9));
    }

    public static String getTimer(long time) {
        String out = "";
        long seconds = time/20;

        if(seconds >= 86400) {
            out += seconds/86400 + "d ";
            seconds -= seconds/86400 * 86400;
        }
        if(seconds >= 3600) {
            out += seconds/3600 + "h ";
            seconds -= seconds/3600*3600;
        }

        if(seconds >= 60) {
            out += seconds/60 + "m ";
            seconds -= seconds/60*60;
        }

        if(seconds != 0) {
            out += seconds + "s";
        }

        return out;
    }
}
