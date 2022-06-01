package net.afi.communityattack.command.subcommands.mayor;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Test extends SubCommand {

    public String getKey() {
        return "test";
    }

    public boolean requiresPlayer() {
        return true;
    }

    public int getArgsLength() {
        return 0;
    }

    @Nullable
    public String requiredPermission() {
        return "office.test";
    }

    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        CommunityAttack.getInstance().getGuiManager().getGuiByKey("patent.list").open(player, null);


    }

    public void sendCommandHelp(CommandSender sender) {

    }
}
