package net.afi.communityattack.command.subcommands.vote;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Participate extends SubCommand {

    public String getKey() {
        return "participate";
    }

    public boolean requiresPlayer() {
        return true;
    }

    public int getArgsLength() {
        return 0;
    }

    @Nullable
    public String requiredPermission() {
        return null;
    }

    public void execute(CommandSender sender, String[] args) {

        if(!CommunityAttack.getInstance().getFileManager().getVoteFile().getBoolean("registryopen")) {
            sender.sendMessage("§6VOTE §8>> §cDie Anmeldung ist leider noch nicht offen");
            return;
        }
        Player player = (Player) sender;
        CommunityAttack.getInstance().getGuiManager().getGuiByKey("vote.participate").open(player, null);
    }

    public void sendCommandHelp(CommandSender sender) {
        sender.sendMessage("§6/vote participate §7- Um sich als Kandidat aufzustellen");
    }
}
