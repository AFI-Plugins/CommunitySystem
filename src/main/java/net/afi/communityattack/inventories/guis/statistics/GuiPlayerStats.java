package net.afi.communityattack.inventories.guis.statistics;

import net.afi.communityattack.inventories.guis.CommunityGui;
import net.afi.communityattack.utils.ItemBuilder;
import net.afi.communityattack.utils.StatisticEnum;
import net.afi.communityattack.utils.UtilMethods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.UUID;

public class GuiPlayerStats extends CommunityGui {

    @Override
    public String getKey() {
        return "statistics.playerstats";
    }

    @Override
    public String getTitle() {
        return "§6Spielerstatistik";
    }

    @Override
    protected void openGui(Player player, String data) {

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(data));
        Inventory inventory = Bukkit.createInventory(null, 9, getTitle());
        int slotCount = 0;

        for(StatisticEnum statEnum : StatisticEnum.values()) {

            ItemBuilder itemBuilder = new ItemBuilder(statEnum.getDisplayMaterial(), 1)
                    .setDisplayName("§6" + statEnum.getDisplayName())
                    .setLore(Arrays.asList(
                            "",
                            "§7" + statEnum.getDisplayName() + ": §6" +
                                    ((statEnum.getStatistic() == Statistic.PLAY_ONE_MINUTE) ?
                                            UtilMethods.getTimer(offlinePlayer.getStatistic(Statistic.PLAY_ONE_MINUTE)) :
                                            offlinePlayer.getStatistic(statEnum.getStatistic()))
                    ));

            inventory.setItem(slotCount, itemBuilder.build());
            slotCount += 2;
        }

        player.openInventory(inventory);
    }

    @Override
    public boolean cancelClicksByDefault() {
        return true;
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }
}
