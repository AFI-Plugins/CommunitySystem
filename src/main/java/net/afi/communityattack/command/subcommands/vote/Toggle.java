package net.afi.communityattack.command.subcommands.vote;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;

public class Toggle extends SubCommand {

    public String getKey() {
        return "toggle";
    }

    public boolean requiresPlayer() {
        return false;
    }

    public int getArgsLength() {
        return 1;
    }

    @Nullable
    public String requiredPermission() {
        return "vote.open";
    }

    public void execute(CommandSender sender, String[] args) {

        if(args[0].equalsIgnoreCase("registry")) {

            boolean open = !CommunityAttack.getInstance().getFileManager().getVoteFile().getBoolean("registryopen");
            sender.sendMessage("§6VOTE §8>> §7Kandidaten " + ((open) ? "§akönnen sich" : "§4können sich nicht") + " §7anmelden");
            CommunityAttack.getInstance().getFileManager().getVoteFile().setValue("registryopen", open);
        }else if(args[0].equalsIgnoreCase("election")) {

            boolean open = !CommunityAttack.getInstance().getFileManager().getVoteFile().getBoolean("electionopen");
            sender.sendMessage("§6VOTE §8>> §7Bürger " + ((open) ? "§akönnen" : "§4können nicht") + " §7wählen");
            CommunityAttack.getInstance().getFileManager().getVoteFile().setValue("electionopen", open);
        }else {
            sendCommandHelp(sender);
        }
    }

    public void sendCommandHelp(CommandSender sender) {
        sender.sendMessage("§6/vote toggle <registry/election> §7- Um die Abstimmung zu öffnen bzw. schließen");
    }
}
