package net.afi.communityattack.inventories.guis;

import net.afi.communityattack.CommunityAttack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;

public abstract class CommunityGui {

    public abstract String getKey();

    public abstract String getTitle();

    public void open(final Player player, @Nullable final String data) {

        if(player.getOpenInventory().getType() != InventoryType.CRAFTING) {
            player.closeInventory();
            new BukkitRunnable() {

                public void run() {
                    openGui(player, data);
                }
            }.runTaskLater(CommunityAttack.getInstance(), 1);
        }else {
            openGui(player, data);
        }
    }

    protected abstract void openGui(Player player, String data);

    public abstract boolean cancelClicksByDefault();

    public abstract void onClick(InventoryClickEvent event);
}
