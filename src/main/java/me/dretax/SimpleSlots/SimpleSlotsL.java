package me.dretax.SimpleSlots;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class SimpleSlotsL implements Listener {
    protected SimpleSlots plugin;

    protected SimpleSlotsL(SimpleSlots instance) {
        this.plugin = instance;
    }

    @EventHandler(priority=EventPriority.HIGH)
    protected void onServerListPing(ServerListPingEvent event) {
        event.setMaxPlayers(this.plugin.config.getInt("SimpleSlots"));
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
    protected void onPlayerLogin(PlayerLoginEvent event) {
        if (this.plugin.config.getInt("SimpleSlots") >= Bukkit.getServer().getOnlinePlayers().size() && event.getResult().equals(PlayerLoginEvent.Result.KICK_FULL)) {
            event.allow();
        }
    }
}
