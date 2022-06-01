package net.afi.communityattack;

import net.afi.communityattack.command.CommandManager;
import net.afi.communityattack.inventories.GuiManager;
import net.afi.communityattack.listeners.InteractManager;
import net.afi.communityattack.listeners.PlayerConnectionHandler;
import net.afi.communityattack.setup.SetupManager;
import net.afi.communityattack.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommunityAttack extends JavaPlugin {

    private static CommunityAttack instance;

    private CommandManager commandManager;
    private GuiManager guiManager;
    private FileManager fileManager;
    private SetupManager setupManager;

    @Override
    public void onEnable() {
        instance = this;

        commandManager = new CommandManager();
        guiManager = new GuiManager();
        fileManager = new FileManager(this.getDataFolder().getPath());
        setupManager = new SetupManager();

        setupDefaultListeners();
    }

    private void setupDefaultListeners() {
        PluginManager pl = Bukkit.getPluginManager();
        pl.registerEvents(new PlayerConnectionHandler(), this);
        pl.registerEvents(new InteractManager(), this);
    }

    public static CommunityAttack getInstance() {
        return instance;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public SetupManager getSetupManager() {
        return setupManager;
    }
}
