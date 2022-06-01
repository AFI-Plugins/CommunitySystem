package net.afi.communityattack.utils;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.inventory.ItemStack;

public enum StatisticEnum {

    PLAYTIME("playtime", "Spielzeit", Statistic.PLAY_ONE_MINUTE, true, Material.CLOCK),
    DEATHS("deaths", "Tode", Statistic.DEATHS, false, Material.SKELETON_SKULL),
    PLAYERKILLS("playerkills", "Spielerkills", Statistic.PLAYER_KILLS, true, Material.DIAMOND_SWORD),
    MOBKILLS("mobkills", "Mobkills", Statistic.MOB_KILLS, true, Material.ZOMBIE_HEAD),
    TRADED("traded", "Gehandelt", Statistic.TRADED_WITH_VILLAGER, true, Material.EMERALD);

    private String key, displayName;
    private Statistic statistic;
    private boolean sortDescending;
    private Material displayMaterial;

    StatisticEnum(String key, String displayName, Statistic statistic, boolean sortDescending, Material displayMaterial) {
        this.key = key;
        this.displayName = displayName;
        this.statistic = statistic;
        this.sortDescending = sortDescending;
        this.displayMaterial = displayMaterial;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public boolean isSortDescending() {
        return sortDescending;
    }

    public Material getDisplayMaterial() {
        return displayMaterial;
    }
}
