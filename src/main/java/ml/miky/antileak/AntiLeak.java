package ml.miky.antileak;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class AntiLeak extends JavaPlugin implements Listener {
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("antileak").setExecutor(new ReloadCommand());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabComplete(PlayerCommandSendEvent e) {
        if(e.getPlayer().hasPermission("antileak.bypass.tab")) return;
        List<String> commands = config.getStringList("whitelist");
        e.getCommands().removeIf(cmd -> !commands.contains(cmd));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if(e.getPlayer().hasPermission("antileak.bypass.cmd")) return;
        String cmd = e.getMessage().split(" ")[0];

        if(config.getBoolean("blocked.block-colons") && cmd.contains(":")) {
            e.getPlayer().sendMessage(config.getString("blocked.default-message"));
            e.setCancelled(true);
            getLogger().info("Blocked command " + cmd + " from " + e.getPlayer().getDisplayName() + " because it's containing colons");
            return;
        }

        List<String> commands = config.getStringList("blocked.commands");
        if(commands.contains(cmd.substring(1))) {
            e.getPlayer().sendMessage(config.getString("blocked.default-message"));
            e.setCancelled(true);
            getLogger().info("Blocked command " + cmd + " from " + e.getPlayer().getDisplayName());
        }
    }

    public class ReloadCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if(!sender.hasPermission("antileak.admin")) return true;

            reloadConfig();
            config = getConfig();
            sender.sendMessage("[AntiLeak] Configuration file reloaded!");
            return true;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
