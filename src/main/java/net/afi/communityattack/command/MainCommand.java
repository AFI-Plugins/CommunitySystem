package net.afi.communityattack.command;

import net.afi.communityattack.command.subcommands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    private CommandManager commandManager;

    public MainCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {
            sendCommandHelp(sender, label);
            return true;
        }

        SubCommand subCommand = commandManager.getSubCommand(label, args[0]);

        if(subCommand == null) {
            sendCommandHelp(sender, label);
            return true;
        }

        if(subCommand.getArgsLength() != -1 && subCommand.getArgsLength() != args.length-1) {
            subCommand.sendCommandHelp(sender);
            return true;
        }

        String[] newArgs = new String[args.length-1];

        for(int i = 0; i < newArgs.length; i++) {
            newArgs[i] = args[i+1];
        }

        if(subCommand.requiredPermission() != null && !(sender.hasPermission(subCommand.requiredPermission()))) {
            sender.sendMessage(ChatColor.GOLD + label.toUpperCase() + "§8>> " + ChatColor.RED + "You do not have the necessary permissions to perform this command");
            return true;
        }

        if(subCommand.requiresPlayer() && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.GOLD + label.toUpperCase() + "§8>> " + ChatColor.RED + "You must be a player to perform this command");
            return true;
        }

        subCommand.execute(sender, newArgs);
        return true;
    }

    private void sendCommandHelp(CommandSender sender, String label) {

        sender.sendMessage("§7=====§6Command-Hilfe§7=====");
        sender.sendMessage("");
        for(SubCommand subCommand : commandManager.getSubCommands(label)) {
            if(subCommand.requiredPermission() != null && !sender.hasPermission(subCommand.requiredPermission()))continue;
            subCommand.sendCommandHelp(sender);
        }
        sender.sendMessage("");
        sender.sendMessage("§7=======================");
    }
}
