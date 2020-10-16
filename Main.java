package SetMaxPlayers.IUseMac;

import listeners.onJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();

        Bukkit.getConsoleSender().sendMessage("[SetMax] by IUseMac has been Enabled!");
        saveDefaultConfig();

        new commands.setmax(this);
        new onJoin(this);
    }


    @Override
    public void onDisable() {
        super.onDisable();
        Bukkit.getConsoleSender().sendMessage("[SetMax] by IUseMac has ben Disabled!");
    }
}
