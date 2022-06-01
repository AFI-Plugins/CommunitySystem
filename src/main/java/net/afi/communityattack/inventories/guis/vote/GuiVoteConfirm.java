package net.afi.communityattack.inventories.guis.vote;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.inventories.guis.CommunityGui;
import net.afi.communityattack.utils.CFGFile;
import net.afi.communityattack.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

public class GuiVoteConfirm extends CommunityGui {

    private HashMap<Integer, ItemStack> items;

    public GuiVoteConfirm() {

        items = new HashMap<Integer, ItemStack>();

        ItemBuilder itemBuilder = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1);
        itemBuilder.setDisplayName("§aBestätigen");
        for(int i = 0; i <= 3; i++) {
            items.put(i, itemBuilder.build());
        }

        itemBuilder = new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1);
        itemBuilder.setDisplayName("§4Ablehnen");
        for(int i = 5; i <= 8; i++) {
            items.put(i, itemBuilder.build());
        }
    }
    public String getKey() {
        return "vote.confirm";
    }

    public String getTitle() {
        return "§6§lBestätigen";
    }

    protected void openGui(Player player, String data) {

        Inventory inventory = Bukkit.createInventory(null, 9, getTitle());
        for(int slot : items.keySet()) {
            inventory.setItem(slot, items.get(slot));
        }

        ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD, 1);
        OfflinePlayer voted = Bukkit.getOfflinePlayer(UUID.fromString(data));
        String hasVoted = playerHasVoted(player);

        itemBuilder.setDisplayName("§6" + voted.getName());
        ArrayList<String> lore = new ArrayList<String>();
        lore.addAll(Arrays.asList("§7Klicke links, um deine", "§7Stimme für §6" + voted.getName() + "§7 abzugeben"));
        if(hasVoted != null) {
            lore.addAll(Arrays.asList("",
                    "§cDies überschreibt deine",
                    "§cStimme für " + Bukkit.getOfflinePlayer(UUID.fromString(hasVoted)).getName())
            );
        }
        itemBuilder.setLore(lore);
        inventory.setItem(4, itemBuilder.build());

        player.openInventory(inventory);
    }

    public boolean cancelClicksByDefault() {
        return true;
    }

    public void onClick(InventoryClickEvent event) {

        if(event.getSlot() == 4)return;

        Player player = (Player) event.getWhoClicked();
        if(event.getSlot() < 4) {
            CFGFile voteFile = CommunityAttack.getInstance().getFileManager().getVoteFile();
            List<String> alreadyvoted = new ArrayList<String>();
            if(voteFile.getObject("alreadyvoted") != null) {
                alreadyvoted = voteFile.getStringList("alreadyvoted");
            }
            OfflinePlayer voted = Bukkit.getOfflinePlayer(UUID.fromString(CommunityAttack.getInstance().getSetupManager().getSetupData(player.getUniqueId())));
            alreadyvoted.add(player.getUniqueId().toString() + ";" + voted.getUniqueId().toString());

            int votes = 1;
            if(voteFile.getObject(voted.getUniqueId().toString() + ".votes") != null) {
                votes = voteFile.getInt(voted.getUniqueId().toString() + ".votes") + 1;
            }

            String hasVoted = playerHasVoted(player);
            if(hasVoted != null) {
                alreadyvoted.remove(player.getUniqueId().toString() + ";" + hasVoted);
                int previousVotes = voteFile.getInt(hasVoted + ".votes");
                previousVotes--;
                voteFile.setValue(hasVoted + ".votes", previousVotes);
            }

            voteFile.setValue(voted.getUniqueId() + ".votes", votes);
            voteFile.setValue("alreadyvoted", alreadyvoted);
        }
        player.closeInventory();
    }

    @Nullable
    private String playerHasVoted(Player toCheck) {

        CFGFile voteFile = CommunityAttack.getInstance().getFileManager().getVoteFile();
        if(voteFile.getObject("alreadyvoted") == null)return null;
        List<String> alreadyVoted = voteFile.getStringList("alreadyvoted");

        for(String vote : alreadyVoted) {
            if(vote.startsWith(toCheck.getUniqueId().toString())){
                return vote.split(";")[1];
            }
        }
        return null;
    }
}
