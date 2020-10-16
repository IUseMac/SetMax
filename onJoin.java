package listeners;

import SetMaxPlayers.IUseMac.Main;
import org.bukkit.Bukkit;
import org.bukkit.Warning;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class onJoin implements Listener {
    private static Main plugin;

    public onJoin(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public Object onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        String online = String.valueOf(Bukkit.getOnlinePlayers().size());
        String maxPlayers = plugin.getConfig().getString("maxPlayers");
        int max = Integer.parseInt(maxPlayers);
        int on = Integer.parseInt(online);

        ConfigurationSection groupsSection = plugin.getConfig().getConfigurationSection("groups");
        for (String groupName : groupsSection.getKeys(false)) {
            ConfigurationSection curSection = groupsSection.getConfigurationSection(groupName);
            String permission = curSection.getString("permission");
            String groupMax = curSection.getString("maxJoin", "NOT IMPORTANT");
            assert groupMax != null;
            int groupMaxJoin = Integer.parseInt(groupMax);
            if (curSection == null) {
                Bukkit.getLogger().log(Level.WARNING, "[SetMax] ERROR in Config!");
                continue;
            }
            if (p.hasPermission(permission)) {
                if (on > groupMaxJoin) {
                    p.kickPlayer("The Server is Full!");
                }
                return true;
            }
            if (permission.equals("-1")) {
                if (on > max) {
                    p.kickPlayer("The Server is Full! Purchase a Rank to Join even when the Server's Full!");
                }
                return true;
            }
        }
        return true;
    }
}



