package net.afi.communityattack.command.subcommands.mayor;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import net.afi.communityattack.utils.CFGFile;
import net.afi.communityattack.utils.UtilMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class GivePatent extends SubCommand {

    public String getKey() {
        return "givepatent";
    }

    public boolean requiresPlayer() {
        return true;
    }

    public int getArgsLength() {
        return 1;
    }

    @Nullable
    public String requiredPermission() {
        return null;
    }

    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if(!UtilMethods.isMayor(player) && !player.hasPermission("mayor.override")) {
            player.sendMessage("§6OFFICE §8>> §cDu bist nicht Bürgermeister!");
            return;
        }

        if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage("§6OFFICE §8>> §cBitte halte das Item, das du patentieren willst, in der Hand!");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            player.sendMessage("§6OFFICE §8>> §cDer Spieler ist nicht online!");
            return;
        }

        CFGFile patentFile = CommunityAttack.getInstance().getFileManager().getPatentFile();

        String key = "patents." + player.getInventory().getItemInMainHand().getType().toString();
        patentFile.setValue(key + ".owner", target.getUniqueId().toString());
        patentFile.setValue(key + ".issuer", player.getUniqueId().toString());
        patentFile.setValue(key + ".time", System.currentTimeMillis());

        player.sendMessage("§6OFFICE §8>> §7Du hast das Patent §aerfolgreich §7angemeldet");
    }

    public void sendCommandHelp(CommandSender sender) {
        if(!(sender instanceof Player))return;
        if(!UtilMethods.isMayor((Player) sender) && !sender.hasPermission("mayor.override"))return;
        sender.sendMessage("§6/office givepatent <Spieler> §7- Melde ein Patent an");
    }
}
