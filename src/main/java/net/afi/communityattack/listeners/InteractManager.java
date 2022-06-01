package net.afi.communityattack.listeners;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.utils.CFGFile;
import net.afi.communityattack.utils.FileUtils;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractManager implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(event.getClickedBlock() == null)return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK)return;
        CFGFile patentFile = CommunityAttack.getInstance().getFileManager().getPatentFile();

        if(patentFile.getObject("block") == null)return;

        if(event.getClickedBlock().getLocation().equals(FileUtils.getLocationFromString(patentFile.getString("block")))) {
            event.setUseInteractedBlock(Event.Result.DENY);
            CommunityAttack.getInstance().getGuiManager().getGuiByKey("patent.list").open(event.getPlayer(), null);
        }
    }
}
