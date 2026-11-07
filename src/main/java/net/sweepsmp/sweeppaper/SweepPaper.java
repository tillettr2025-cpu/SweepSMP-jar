package net.sweepsmp.sweeppaper;

import java.util.concurrent.atomic.AtomicLong;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SweepPaper extends JavaPlugin implements Listener {
    private final AtomicLong commandCount = new AtomicLong();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("SweepPaper 26.2.0 feature suite enabled.");
        getLogger().info("Foundation: Paper 26.2. This plugin does not replace the Paper server JAR.");
        long interval = Math.max(20L, getConfig().getLong("monitor.interval-seconds", 60L) * 20L);
        if (getConfig().getBoolean("monitor.enabled", true)) {
            Bukkit.getScheduler().runTaskTimer(this, () -> {
                double mspt = Bukkit.getAverageTickTime();
                if (mspt > getConfig().getDouble("performance.warn-tick-ms", 75.0)) {
                    getLogger().warning(String.format("SweepMonitor: average tick time %.1f ms", mspt));
                }
            }, interval, interval);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("SweepPaper feature suite disabled.");
    }

    @EventHandler
    public void onCommand(ServerCommandEvent event) {
        commandCount.incrementAndGet();
        String raw = event.getCommand().toLowerCase();
        if (getConfig().getBoolean("guard.block-command-blocks", false)
                && raw.startsWith("minecraft:")) {
            // Reserved for explicit server-owner command policy; no commands are blocked by default.
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("sweep")) return false;
        if (args.length == 0 || args[0].equalsIgnoreCase("status")) {
            sender.sendMessage(color("&a&lSweepPaper &7v26.2.0"));
            sender.sendMessage(color("&7Foundation: &fPaper 26.2"));
            sender.sendMessage(color("&7Online players: &f" + Bukkit.getOnlinePlayers().size()));
            sender.sendMessage(color("&7Worlds: &f" + Bukkit.getWorlds().size()));
            sender.sendMessage(color("&7Commands observed: &f" + commandCount.get()));
            sender.sendMessage(color("&7Average tick: &f" +
                    String.format("%.2f ms", Bukkit.getAverageTickTime())));
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("sweeppaper.admin")) {
                sender.sendMessage(color("&cYou don't have permission."));
                return true;
            }
            reloadConfig();
            sender.sendMessage(color("&aSweepPaper configuration reloaded."));
            return true;
        }
        sender.sendMessage(color("&7Usage: /sweep [status|reload]"));
        return true;
    }

    private String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
