package ml.miky.antileak.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.List;

import static ml.miky.antileak.AntiLeak.instance;

public class TabListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabComplete(PlayerCommandSendEvent e) {
        if(e.getPlayer().hasPermission("antileak.bypass.tab")) return;
        List<String> commands = instance.getConfig().getStringList("whitelist");
        e.getCommands().removeIf(cmd -> !commands.contains(cmd));
    }

}
