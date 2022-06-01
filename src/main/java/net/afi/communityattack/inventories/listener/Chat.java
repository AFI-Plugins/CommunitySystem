package net.afi.communityattack.inventories.listener;

import net.afi.communityattack.CommunityAttack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        final Player player = event.getPlayer();
        if(CommunityAttack.getInstance().getSetupManager().getSetupData(player.getUniqueId()) == null)return;
        event.setCancelled(true);
        String[] data = CommunityAttack.getInstance().getSetupManager().getSetupData(player.getUniqueId()).split(";");

        Object toSet = null;
        if(data[0].equals("list")) {
            toSet = new ArrayList<String>();
            for(String s : event.getMessage().split(";")) {
                ((ArrayList) toSet).add(s);
            }
        }
        if(toSet == null)return;

        if(data[1].equals("vote")) {
            CommunityAttack.getInstance().getFileManager().getVoteFile().setValue(data[2], toSet);

            new BukkitRunnable() {
                public void run() {
                    CommunityAttack.getInstance().getGuiManager().getGuiByKey("vote.participate").open(player, null);
                }
            }.runTask(CommunityAttack.getInstance());
        }
    }
}
