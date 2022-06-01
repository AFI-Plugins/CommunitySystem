package net.afi.communityattack.command.subcommands.admin;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import net.afi.communityattack.utils.FileUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import javax.annotation.Nullable;

public class SetPatentBlock extends SubCommand {
    @Override
    public String getKey() {
        return "setpatentblock";
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
        return "admin.setpatentblock";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        RayTraceResult result = player.rayTraceBlocks(5);
        if(result.getHitBlock() == null) {
            player.sendMessage("§6ADMIN §8>> §cDu guckst nicht auf einen Block");
            return;
        }

        CommunityAttack.getInstance().getFileManager().getPatentFile().setValue("block", FileUtils.getStringOfLocation(result.getHitBlock().getLocation(), false));
        player.sendMessage("§6ADMIN §8>> §aBlock gesetzt!");
    }

    @Override
    public void sendCommandHelp(CommandSender sender) {
        sender.sendMessage("§6/admin setpatentblock §7- Setzt den Block als Patent-Block");
    }
}
