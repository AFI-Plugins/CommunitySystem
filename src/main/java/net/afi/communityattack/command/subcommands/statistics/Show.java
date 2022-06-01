package net.afi.communityattack.command.subcommands.statistics;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import net.afi.communityattack.utils.StatisticEnum;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Show extends SubCommand {
    @Override
    public String getKey() {
        return "show";
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
        CommunityAttack.getInstance().getGuiManager().getGuiByKey("statistics.list").open(((Player)sender), StatisticEnum.values()[0].toString());
    }

    @Override
    public void sendCommandHelp(CommandSender sender) {
        sender.sendMessage("§6/statistics show §7- Um die Statistiken aller Spieler zu zeigen");
    }
}
