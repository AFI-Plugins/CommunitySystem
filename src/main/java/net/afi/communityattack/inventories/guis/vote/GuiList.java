package net.afi.communityattack.inventories.guis.vote;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.inventories.guis.CommunityGui;
import net.afi.communityattack.utils.CFGFile;
import net.afi.communityattack.utils.ItemBuilder;
import net.afi.communityattack.utils.UtilMethods;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GuiList extends CommunityGui {

    public String getKey() {
        return "vote.list";
    }

    public String getTitle() {
        return "§6§lAbstimmen";
    }

    protected void openGui(Player player, String data) {

        if(CommunityAttack.getInstance().getFileManager().getVoteFile().getObject("participating") == null) {
            Inventory inventory = Bukkit.createInventory(null, 9, getTitle());
            ItemBuilder itemBuilder = new ItemBuilder(Material.BARRIER);
            itemBuilder.setDisplayName("§4Kein Teilnehmer");
            itemBuilder.setLore(Arrays.asList(
                    "§7Es hat sich noch keiner",
                    "§7zur Wahl zur Verfügung gestellt"
            ));
            inventory.setItem(4, itemBuilder.build());
            player.openInventory(inventory);
            return;
        }

        List<String> participating = CommunityAttack.getInstance().getFileManager().getVoteFile().getStringList("participating");
        int size = UtilMethods.getInventorySizeByCollectionSize(participating.size());
        Inventory inventory = Bukkit.createInventory(null, size, getTitle());

        for(String participantStr : participating) {
            OfflinePlayer participant = Bukkit.getOfflinePlayer(UUID.fromString(participantStr));
            ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD, 1);
            itemBuilder.setOwner(participant);
            itemBuilder.setDisplayName("§6" + participant.getName());

            List<String> lore = CommunityAttack.getInstance().getFileManager().getVoteFile().getStringList(participantStr + ".slogan");

            for(int i = 0; i < lore.size(); i++) {
                lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
            }
            itemBuilder.setLore(lore);

            inventory.addItem(itemBuilder.build());
            player.openInventory(inventory);
        }
    }

    public boolean cancelClicksByDefault() {
        return true;
    }

    public void onClick(InventoryClickEvent event) {

        if(event.getCurrentItem() == null)return;
        if(event.getCurrentItem().getType() == Material.AIR)return;
        if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§4Kein Teilnehmer"))return;

        Player player = (Player) event.getWhoClicked();
        UUID voteID = ((SkullMeta)event.getCurrentItem().getItemMeta()).getOwnerProfile().getUniqueId();

        String hasVoted = playerHasVoted(player);
        if(hasVoted != null && hasVoted.equals(voteID.toString())) {
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            player.sendMessage("§6VOTE §8>> §cDu darfst nicht zweimal für die selbe Person abstimmen");
            return;
        }

        CommunityAttack.getInstance().getGuiManager().getGuiByKey("vote.confirm").open(player, voteID.toString());
        CommunityAttack.getInstance().getSetupManager().putSetupData(player.getUniqueId(), voteID.toString());
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
