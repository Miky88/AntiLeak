package ml.miky.antileak.Listeners;

import ml.miky.antileak.AntiLeak;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if(e.getPlayer().hasPermission("antileak.bypass.cmd")) return;
        String cmd = e.getMessage().split(" ")[0];

        if(AntiLeak.instance.config.getBoolean("blocked.block-colons") && cmd.contains(":")) {
            e.getPlayer().sendMessage(config.getString("blocked.default-message"));
            e.setCancelled(true);
            AntiLeak.instance.getLogger().info("Blocked command " + cmd + " from " + e.getPlayer().getDisplayName() + " because it's containing colons");
            return;
        }

        List<String> commands = config.getStringList("blocked.commands");
        if(commands.contains(cmd.substring(1))) {
            e.getPlayer().sendMessage(config.getString("blocked.default-message"));
            e.setCancelled(true);
            AntiLeak.instance.getLogger().info("Blocked command " + cmd + " from " + e.getPlayer().getDisplayName());
        }
    }
}
