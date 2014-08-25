package net.depotwarehouse.bukkitbod;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public final class BukkitBOD extends JavaPlugin implements Listener {

    private BanServer server;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("BukkitBOD loaded. Prepare yonder banhammers.");
        this.server = new BanServer(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("BukkitBOD has been disabled. Thor's banhammer has returned to Asgard.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("bantotal")) {
            sender.sendMessage("Current ban total: " + server.banTotal());
            return false;
        }
        return false;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (this.server.isBanned(player)) {
            long bannedUntil = this.server.getBanLength(player);
            long now = new Date().getTime();
            // We convert the remaining time to seconds
            long remainingBan = (bannedUntil - now) / 1000;
            event.setKickMessage("You are still banned for another " + remainingBan + " seconds!");
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        server.ban(event.getEntity());
        getLogger().info("Banned " + player.getName());
    }
}
