package net.afi.communityattack.inventories.guis.patents;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.inventories.guis.CommunityGui;
import net.afi.communityattack.utils.ItemBuilder;
import net.afi.communityattack.utils.UtilMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class GuiPatentList extends CommunityGui {

    public String getKey() {
        return "patent.list";
    }

    public String getTitle() {
        return "§6Patente";
    }

    protected void openGui(Player player, String data) {

        if(CommunityAttack.getInstance().getFileManager().getPatentFile().getObject("patents") == null) {
            Inventory inventory = Bukkit.createInventory(null, 9, getTitle());
            inventory.setItem(4, new ItemBuilder(
                    Material.BARRIER, 1)
                    .setDisplayName("§4Keine Patente")
                    .setLore(Arrays.asList("§7Es sind aktuell",
                            "§7keine Patente registriert")).build()
            );
            player.openInventory(inventory);
            return;
        }
        int size = CommunityAttack.getInstance().getFileManager().getPatentFile().getSection("patents").getKeys(false).size();
        Inventory inventory = Bukkit.createInventory(null, UtilMethods.getInventorySizeByCollectionSize(size), "§6Patente");
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        for(String patent : CommunityAttack.getInstance().getFileManager().getPatentFile().getSection("patents").getKeys(false)) {
            ConfigurationSection config = CommunityAttack.getInstance().getFileManager().getPatentFile().getSection("patents." + patent);
            ItemBuilder itemBuilder = new ItemBuilder(Material.getMaterial(patent), 1);

            itemBuilder.setDisplayName("§7" + patent);

            itemBuilder.setLore(Arrays.asList(
                    "",
                    "§7Besitzer: " + Bukkit.getOfflinePlayer(UUID.fromString(config.getString("owner"))).getName(),
                    "§7Herausgeber: " + Bukkit.getOfflinePlayer(UUID.fromString(config.getString("issuer"))).getName(),
                    "§7Ausgabezeit: " + df.format(new Date(config.getLong("time")))
            ));
            inventory.addItem(itemBuilder.build());
        }

        player.openInventory(inventory);
    }

    public boolean cancelClicksByDefault() {
        return true;
    }

    public void onClick(InventoryClickEvent event) {
    }
}
