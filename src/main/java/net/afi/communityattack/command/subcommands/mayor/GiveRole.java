package net.afi.communityattack.command.subcommands.mayor;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import net.afi.communityattack.utils.CFGFile;
import net.afi.communityattack.utils.UtilMethods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class GiveRole extends SubCommand {

    public String getKey() {
        return "promote";
    }

    public boolean requiresPlayer() {
        return true;
    }

    public int getArgsLength() {
        return 2;
    }

    @Nullable
    public String requiredPermission() {
        return null;
    }

    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if(!UtilMethods.isMayor(player) && !player.hasPermission("mayor.override")) {
            sender.sendMessage("§6OFFICE §8>> §cDu bist nicht Bürgermeister!");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            player.sendMessage("§6OFFICE §8>> §cDieser Spieler ist nicht online!");
            return;
        }

        CFGFile prefixFile = CommunityAttack.getInstance().getFileManager().getPrefixFile();
        if(!prefixFile.getSection("").getKeys(false).contains(args[1].toLowerCase())) {
            player.sendMessage("§6OFFICE §8>> §cDiese Rolle wurde nicht gefunden!");
            player.sendMessage("§7Verfügbar sind:");
            for(String s : prefixFile.getKeys(false)) {
                if(s.length() > 15)continue;
                if(s.equalsIgnoreCase("mayor"))continue;
                player.sendMessage("§6" + s);
            }
            return;
        }

        prefixFile.setValue(target.getUniqueId().toString(), args[1].toLowerCase());
        target.setPlayerListName(ChatColor.translateAlternateColorCodes('&', prefixFile.getString(args[1].toLowerCase())) + target.getName());
    }

    public void sendCommandHelp(CommandSender sender) {
        if(!(sender instanceof Player))return;
        if(!UtilMethods.isMayor((Player) sender) && !sender.hasPermission("mayor.override"))return;
        sender.sendMessage("§6/office promote <player> <role>");
    }
}
