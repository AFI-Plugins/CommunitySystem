package net.afi.communityattack.inventories.listener;

import net.afi.communityattack.inventories.GuiManager;
import net.afi.communityattack.inventories.guis.CommunityGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickEvent implements Listener {

    private GuiManager guiManager;

    public ClickEvent(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        CommunityGui gui = guiManager.getGuiByTitle(event.getView().getTitle());
        if(!(event.getWhoClicked() instanceof Player))return;

        if(gui == null)return;
        if(gui.cancelClicksByDefault())event.setCancelled(true);
        gui.onClick(event);
    }
}
