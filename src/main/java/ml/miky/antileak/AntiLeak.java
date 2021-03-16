package ml.miky.antileak;

import ml.miky.antileak.Commands.AntiLeakCommand;
import ml.miky.antileak.Listeners.CommandListener;
import ml.miky.antileak.Listeners.TabListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class AntiLeak extends JavaPlugin {
    public static AntiLeak instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        // Listeners
        Bukkit.getServer().getPluginManager().registerEvents(new TabListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CommandListener(), this);

        // Commands
        this.getCommand("antileak").setExecutor(new AntiLeakCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}