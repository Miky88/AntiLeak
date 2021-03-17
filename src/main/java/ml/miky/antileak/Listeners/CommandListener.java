package ml.miky.antileak.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

import static ml.miky.antileak.AntiLeak.instance;

public class CommandListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(event.getPlayer().hasPermission("antileak.bypass.cmd")) return;
        String cmd = event.getMessage().split(" ")[0];

        if(instance.getConfig().getBoolean("blocked.block-colons") && cmd.contains(":")) {
            event.getPlayer().sendMessage(instance.getConfig().getString("blocked.default-message"));
            event.setCancelled(true);
            instance.getLogger().info("Blocked command " + cmd + " from " + event.getPlayer().getDisplayName() + " because it's containing colons");
            return;
        }

        List<String> commands = instance.getConfig().getStringList("blocked.commands");
        if(commands.contains(cmd.substring(1))) {
            event.getPlayer().sendMessage(instance.getConfig().getString("blocked.default-message"));
            event.setCancelled(true);
            instance.getLogger().info("Blocked command " + cmd + " from " + event.getPlayer().getDisplayName());
        }
    }
}
