package net.afi.communityattack.command.subcommands;

import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;

public abstract class SubCommand {

    public abstract String getKey();

    public abstract boolean requiresPlayer();

    public abstract int getArgsLength();

    @Nullable
    public abstract String requiredPermission();

    public abstract void execute(CommandSender sender, String[] args);

    public abstract void sendCommandHelp(CommandSender sender);
}
