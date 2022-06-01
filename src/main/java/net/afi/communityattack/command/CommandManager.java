package net.afi.communityattack.command;

import net.afi.communityattack.CommunityAttack;
import net.afi.communityattack.command.subcommands.SubCommand;
import net.afi.communityattack.command.subcommands.admin.SetPatentBlock;
import net.afi.communityattack.command.subcommands.mayor.GivePatent;
import net.afi.communityattack.command.subcommands.mayor.GiveRole;
import net.afi.communityattack.command.subcommands.mayor.RemovePatent;
import net.afi.communityattack.command.subcommands.mayor.Test;
import net.afi.communityattack.command.subcommands.statistics.Show;
import net.afi.communityattack.command.subcommands.vote.*;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;

public class CommandManager {

    private HashMap<String, HashSet<SubCommand>> subcommands;

    public CommandManager() {
        subcommands = new HashMap<String, HashSet<SubCommand>>();
        innitCommands();
    }

    private void innitCommands() {

        CommunityAttack instance = CommunityAttack.getInstance();
        instance.getCommand("vote").setExecutor(new MainCommand(this));
        instance.getCommand("office").setExecutor(new MainCommand(this));
        instance.getCommand("admin").setExecutor(new MainCommand(this));
        instance.getCommand("statistics").setExecutor(new MainCommand(this));

        HashSet<SubCommand> cmdCommands = new HashSet<>();
        cmdCommands.add(new List());
        cmdCommands.add(new Participate());
        cmdCommands.add(new Announce());
        cmdCommands.add(new Toggle());
        cmdCommands.add(new Reset());
        subcommands.put("vote", cmdCommands);

        cmdCommands = new HashSet<>();
        cmdCommands.add(new GivePatent());
        cmdCommands.add(new GiveRole());
        cmdCommands.add(new Test());
        cmdCommands.add(new RemovePatent());
        subcommands.put("office", cmdCommands);

        cmdCommands = new HashSet<>();
        cmdCommands.add(new SetPatentBlock());
        subcommands.put("admin", cmdCommands);

        cmdCommands = new HashSet<>();
        cmdCommands.add(new Show());
        subcommands.put("statistics", cmdCommands);
        subcommands.put("stats", cmdCommands);
    }

    @Nullable
    public HashSet<SubCommand> getSubCommands(String cmd) {
        return subcommands.get(cmd);
    }

    @Nullable
    public SubCommand getSubCommand(String cmd, String key) {

        HashSet<SubCommand> set = getSubCommands(cmd);
        if(set == null)return null;

        for(SubCommand command : set) {
            if(command.getKey().equals(key.toLowerCase()))return command;
        }
        return null;
    }
}
