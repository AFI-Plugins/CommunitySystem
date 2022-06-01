package net.afi.communityattack.inventories.guis.statistics;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.inventories.guis.CommunityGui;
import net.afi.communityattack.utils.ItemBuilder;
import net.afi.communityattack.utils.StatisticEnum;
import net.afi.communityattack.utils.UtilMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class GuiStatList extends CommunityGui {

    @Override
    public String getKey() {
        return "statistics.list";
    }

    @Override
    public String getTitle() {
        return "§6Statistiken";
    }

    @Override
    protected void openGui(Player player, String data) {

        Inventory inventory = Bukkit.createInventory(null, UtilMethods.getInventorySizeByCollectionSize(Bukkit.getOfflinePlayers().length) + 9, getTitle());

        StatisticEnum stat = StatisticEnum.valueOf(data);

        List<OfflinePlayer> players = new ArrayList<>();
        players.addAll(Arrays.asList(Bukkit.getOfflinePlayers()));

        players.sort(new Comparator<OfflinePlayer>() {
            @Override
            public int compare(OfflinePlayer o1, OfflinePlayer o2) {

                if(stat.isSortDescending()) {
                    if(o1.getStatistic(stat.getStatistic()) > o2.getStatistic(stat.getStatistic())) {
                        return -1;
                    }else if(o1.getStatistic(stat.getStatistic()) < o2.getStatistic(stat.getStatistic())) {
                        return 1;
                    }
                }else {
                    if(o1.getStatistic(stat.getStatistic()) < o2.getStatistic(stat.getStatistic())) {
                        return -1;
                    }else if(o1.getStatistic(stat.getStatistic()) > o2.getStatistic(stat.getStatistic())) {
                        return 1;
                    }
                }
                return 0;
            }
        });

        for(OfflinePlayer offlinePlayer : players) {

            ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD, 1)
                    .setDisplayName("§6" + offlinePlayer.getName())
                    .setOwner(offlinePlayer)
                    .setLore(Arrays.asList("",
                            "§7" + stat.getDisplayName() + ": §6" +
                                    ((stat.getStatistic() == Statistic.PLAY_ONE_MINUTE) ?
                                            UtilMethods.getTimer(offlinePlayer.getStatistic(Statistic.PLAY_ONE_MINUTE)) :
                                            offlinePlayer.getStatistic(stat.getStatistic())),
                            "",
                            "§aKlicke für mehr")
                    );

            inventory.addItem(itemBuilder.build());
        }

        ItemBuilder itemBuilder = new ItemBuilder(Material.HOPPER, 1)
                .setDisplayName("§6Rotieren");
        List<String> lore = new ArrayList<>();

        for(StatisticEnum statEnum : StatisticEnum.values()) {

            if(statEnum == stat) {
                lore.add("§a" + statEnum.getDisplayName());
            }else {
                lore.add("§7" + statEnum.getDisplayName());
            }
        }
        itemBuilder.setLore(lore);
        inventory.setItem(inventory.getSize() - 5, itemBuilder.build());
        player.openInventory(inventory);
    }

    @Override
    public boolean cancelClicksByDefault() {
        return true;
    }

    @Override
    public void onClick(InventoryClickEvent event) {

        if(event.getCurrentItem() == null)return;
        Player player = (Player) event.getWhoClicked();
        if(event.getSlot() == event.getInventory().getSize() -5) {

            for(int i = 0; i < StatisticEnum.values().length; i++) {
                if(event.getCurrentItem().getItemMeta().getLore().get(i).startsWith("§a")) {

                    if(i == StatisticEnum.values().length-1) {
                        CommunityAttack.getInstance().getGuiManager().getGuiByKey("statistics.list").open(player, StatisticEnum.values()[0].toString());
                    }else {
                        CommunityAttack.getInstance().getGuiManager().getGuiByKey("statistics.list").open(player, StatisticEnum.values()[i+1].toString());
                    }
                }
            }
            return;
        }

        OfflinePlayer offlinePlayer = ((SkullMeta)event.getCurrentItem().getItemMeta()).getOwningPlayer();
        CommunityAttack.getInstance().getGuiManager().getGuiByKey("statistics.playerstats").open(player, offlinePlayer.getUniqueId().toString());
    }

}
