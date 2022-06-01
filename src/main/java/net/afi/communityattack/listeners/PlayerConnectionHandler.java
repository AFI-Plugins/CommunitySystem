package net.afi.communityattack.listeners;

import net.afi.communityattack.CommunityAttack;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionHandler implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if(CommunityAttack.getInstance().getFileManager().getPrefixFile().getObject(player.getUniqueId().toString()) != null) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&',
                    CommunityAttack.getInstance().getFileManager().getPrefixFile().getString(
                            CommunityAttack.getInstance().getFileManager().getPrefixFile().getString(player.getUniqueId().toString())))
             + player.getName());
        }else {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&',
                    CommunityAttack.getInstance().getFileManager().getPrefixFile().getString("default"))
             + player.getName());
        }
    }
}
