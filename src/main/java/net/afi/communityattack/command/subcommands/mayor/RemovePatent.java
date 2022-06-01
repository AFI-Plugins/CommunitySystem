package net.afi.communityattack.command.subcommands.mayor;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import net.afi.communityattack.utils.CFGFile;
import net.afi.communityattack.utils.UtilMethods;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class RemovePatent extends SubCommand {
    @Override
    public String getKey() {
        return "removepatent";
    }

    @Override
    public boolean requiresPlayer() {
        return true;
    }

    @Override
    public int getArgsLength() {
        return 0;
    }

    @Nullable
    @Override
    public String requiredPermission() {
        return null;
    }

    @Override
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

        CFGFile patentFile = CommunityAttack.getInstance().getFileManager().getPatentFile();
        String mat = player.getInventory().getItemInMainHand().getType().toString();
        if(patentFile.getObject("patents." + mat) == null) {
            player.sendMessage("§6OFFICE §8>> §cDieses Patent ist nicht registriert!");
            return;
        }

        patentFile.setValue("patents." + mat, null);
        if(patentFile.getSection("patents").getKeys(false).size() == 0) {
            patentFile.setValue("patents", null);
            player.sendMessage("§6OFFICE §8>> §7Patent erfolgreich §4abgemeldet§7! Es sind §4keine §7Patent mehr vorhanden°");
        }else {
            player.sendMessage("§6OFFICE §8>> §7Patent erfolgreich §4abgemeldet");
        }
    }

    @Override
    public void sendCommandHelp(CommandSender sender) {
        if(!(sender instanceof Player))return;
        if(!UtilMethods.isMayor((Player) sender) && !sender.hasPermission("mayor.override"))return;

        sender.sendMessage("§6/office removepatent §7- Entfernt das Patent auf das Item in deiner Hand");
    }
}
