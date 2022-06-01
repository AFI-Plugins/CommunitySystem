package net.afi.communityattack.command.subcommands.vote;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.io.File;

public class Reset extends SubCommand {
    @Override
    public String getKey() {
        return "reset";
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
        return "vote.reset";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        File file = new File(CommunityAttack.getInstance().getDataFolder().getPath() + "/vote.yml");
        if(file.exists())file.delete();
        Bukkit.getServer().reload();

        sender.sendMessage("§6VOTE §8>> §aWahl zurückgesetzt und Server neugeladen");
    }

    @Override
    public void sendCommandHelp(CommandSender sender) {
        sender.sendMessage("§6/vote reset §7- Setzt die Wahldaten zurück §c(ACHTUNG: NICHT WIDERRUFBAR)");
    }
}
