package net.afi.communityattack.inventories.guis.vote;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.inventories.guis.CommunityGui;
import net.afi.communityattack.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GuiParticipate extends CommunityGui {

    private HashMap<Integer, ItemStack> items;

    public GuiParticipate() {

        items = new HashMap<Integer, ItemStack>();
        ItemBuilder itemBuilder = new ItemBuilder(Material.NAME_TAG, 1);
        itemBuilder.setDisplayName("§6Slogan");
        itemBuilder.setLore(Arrays.asList("§7Klick hier um",
                "§7den Slogan für",
                "§7deine Kampagne festzulegen",
                "",
                "§cBenutze ; für Zeilen",
                "§cBenutze & für Farbe"));
        items.put(4, itemBuilder.build());
    }

    public String getKey() {
        return "vote.participate";
    }

    public String getTitle() {
        return "§6Participate";
    }

    @Override
    protected void openGui(Player player, String data) {

        Inventory inventory = Bukkit.createInventory(null, 9, getTitle());

        for(int slot : items.keySet()) {
            inventory.setItem(slot, items.get(slot));
        }

        inventory.setItem(8, getWool(player));
        player.openInventory(inventory);
    }

    public boolean cancelClicksByDefault() {
        return true;
    }

    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(event.getSlot() == 4) {
            CommunityAttack.getInstance().getSetupManager().putSetupData(player.getUniqueId(), "list;vote;" + player.getUniqueId().toString() + ".slogan");
            player.closeInventory();
            player.sendMessage("§6VOTE §8>> §aSchreib deinen Slogan in den Chat");
        }else if(event.getSlot() == 8) {

            if(event.getCurrentItem().getType() == Material.LIME_WOOL) {

                List<String> participating = CommunityAttack.getInstance().getFileManager().getVoteFile().getStringList("participating");
                participating.remove(player.getUniqueId().toString());

                if(participating.isEmpty()) {
                    CommunityAttack.getInstance().getFileManager().getVoteFile().setValue("participating", null);
                }else {
                    CommunityAttack.getInstance().getFileManager().getVoteFile().setValue("participating", participating);
                }

                event.getView().getTopInventory().setItem(8, getWool(player));
            }

            else {

                List<String> participating = new ArrayList<String>();
                if(CommunityAttack.getInstance().getFileManager().getVoteFile().getObject("participating") != null) {
                    participating = CommunityAttack.getInstance().getFileManager().getVoteFile().getStringList("participating");
                }
                participating.add(player.getUniqueId().toString());
                CommunityAttack.getInstance().getFileManager().getVoteFile().setValue("participating", participating);

                if(CommunityAttack.getInstance().getFileManager().getVoteFile().getObject(player.getUniqueId().toString() + ".slogan") == null) {
                    CommunityAttack.getInstance().getFileManager().getVoteFile().setValue(player.getUniqueId().toString() + ".slogan",
                            Arrays.asList("&7Ich habe keinen Slogan",
                                    "&7deswegen solltet ihr Alex wählen"));
                }

                event.getView().getTopInventory().setItem(8, getWool(player));
            }
        }
    }

    private ItemStack getWool(Player player) {

        ItemBuilder itemBuilder;
        if(CommunityAttack.getInstance().getFileManager().getVoteFile().getStringList("participating").contains(player.getUniqueId().toString())) {
            itemBuilder = new ItemBuilder(Material.LIME_WOOL, 1);
            itemBuilder.setDisplayName("§aÖffentlich");
            itemBuilder.setLore(Arrays.asList("§7Deine Kampagne ist aktuell:",
                    "§aÖffentlich",
                    "",
                    "§7Klicke um das zu ändern"));
        }
        else {
            itemBuilder = new ItemBuilder(Material.RED_WOOL, 1);
            itemBuilder.setDisplayName("§4Privat");
            itemBuilder.setLore(Arrays.asList("§7Deine Kampagne ist aktuell:",
                    "§4Privat",
                    "",
                    "§7Klicke um das zu ändern"));
        }
        return itemBuilder.build();
    }
}
