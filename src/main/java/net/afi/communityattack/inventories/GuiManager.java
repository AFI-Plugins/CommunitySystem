package net.afi.communityattack.inventories;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.inventories.guis.CommunityGui;
import net.afi.communityattack.inventories.guis.patents.GuiPatentList;
import net.afi.communityattack.inventories.guis.statistics.GuiPlayerStats;
import net.afi.communityattack.inventories.guis.statistics.GuiStatList;
import net.afi.communityattack.inventories.guis.vote.GuiList;
import net.afi.communityattack.inventories.guis.vote.GuiParticipate;
import net.afi.communityattack.inventories.guis.vote.GuiVoteConfirm;
import net.afi.communityattack.inventories.listener.Chat;
import net.afi.communityattack.inventories.listener.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nullable;
import java.util.HashSet;

public class GuiManager {

    private HashSet<CommunityGui> guis;

    public GuiManager() {
        guis = new HashSet<CommunityGui>();
        innitGuis();
        innitListeners();
    }

    private void innitGuis() {
        guis.add(new GuiList());
        guis.add(new GuiParticipate());
        guis.add(new GuiVoteConfirm());
        guis.add(new GuiPatentList());
        guis.add(new GuiStatList());
        guis.add(new GuiPlayerStats());
    }

    private void innitListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin plugin = CommunityAttack.getInstance();

        pluginManager.registerEvents(new Chat(), plugin);
        pluginManager.registerEvents(new ClickEvent(this), plugin);
    }

    @Nullable
    public CommunityGui getGuiByTitle(String title) {

        for(CommunityGui gui : guis) {
            if(gui.getTitle().equals(title))return gui;
        }

        return  null;
    }

    @Nullable
    public CommunityGui getGuiByKey(String key) {

        for(CommunityGui gui : guis) {
            if(gui.getKey().equals(key))return gui;
        }
        return null;
    }
}
