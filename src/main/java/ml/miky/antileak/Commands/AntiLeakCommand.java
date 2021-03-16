package ml.miky.antileak.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static ml.miky.antileak.AntiLeak.instance;

public class AntiLeakCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("antileak.admin")) return true;

        instance.reloadConfig();
        sender.sendMessage("[AntiLeak] Configuration file reloaded!");
        return true;
    }
}
