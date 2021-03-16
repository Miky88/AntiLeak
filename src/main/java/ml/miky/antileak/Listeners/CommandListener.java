package ml.miky.antileak.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

import static ml.miky.antileak.AntiLeak.instance;

public class CommandListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if(e.getPlayer().hasPermission("antileak.bypass.cmd")) return;
        String cmd = e.getMessage().split(" ")[0];

        if(instance.getConfig().getBoolean("blocked.block-colons") && cmd.contains(":")) {
            e.getPlayer().sendMessage(instance.getConfig().getString("blocked.default-message"));
            e.setCancelled(true);
            instance.getLogger().info("Blocked command " + cmd + " from " + e.getPlayer().getDisplayName() + " because it's containing colons");
            return;
        }

        List<String> commands = instance.getConfig().getStringList("blocked.commands");
        if(commands.contains(cmd.substring(1))) {
            e.getPlayer().sendMessage(instance.getConfig().getString("blocked.default-message"));
            e.setCancelled(true);
            instance.getLogger().info("Blocked command " + cmd + " from " + e.getPlayer().getDisplayName());
        }
    }
}
