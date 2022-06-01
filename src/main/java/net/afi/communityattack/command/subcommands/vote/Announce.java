package net.afi.communityattack.command.subcommands.vote;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import net.afi.communityattack.utils.CFGFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Announce extends SubCommand {

    public String getKey() {
        return "announce";
    }

    public boolean requiresPlayer() {
        return false;
    }

    public int getArgsLength() {
        return 0;
    }

    @Nullable
    public String requiredPermission() {
        return "vote.announce";
    }

    public void execute(CommandSender sender, String[] args) {

        CFGFile voteFile = CommunityAttack.getInstance().getFileManager().getVoteFile();
        List<String> toAnnounce = new ArrayList<String>();
        String winner = "";
        int bestVotes = 0;
        boolean tie = false;

        for(String participating : voteFile.getStringList("participating")) {

            if(voteFile.getObject(participating + ".votes") == null || voteFile.getInt(participating + ".votes") < bestVotes)continue;

            if(voteFile.getInt(participating + ".votes") > bestVotes) {
                tie = false;
                winner = participating;
                bestVotes = voteFile.getInt(participating + ".votes");
            }else {
                tie = true;
            }
        }

        String title;
        String subtitle;

        if(tie) {
            title = "§6Unentschieden";
            subtitle = "§7Es gibt keinen klaren Gewinner";
        }else {
            title = "§6" + Bukkit.getOfflinePlayer(UUID.fromString(winner)).getName();
            subtitle = "§7Dieser Spieler hat die Wahl gewonnen";
        }

        for(OfflinePlayer offlinePlayer : Bukkit.getServer().getOfflinePlayers()) {

            Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());
            if(player == null ) {
                toAnnounce.add(offlinePlayer.getUniqueId().toString());
            }else {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                player.sendTitle(title, subtitle, 20, 60, 20);
            }
        }

        if(tie)return;
        voteFile.setValue("mayor", winner);
        CommunityAttack.getInstance().getFileManager().getPrefixFile().setValue(winner, "mayor");

        Player player = Bukkit.getPlayer(UUID.fromString(winner));
        if(player != null) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&',
                    CommunityAttack.getInstance().getFileManager().getPrefixFile().getString("mayor")) + player.getName());
        }
    }

    public void sendCommandHelp(CommandSender sender) {
        sender.sendMessage("§6/vote announce §7- Announces the winner of the election");
    }
}
