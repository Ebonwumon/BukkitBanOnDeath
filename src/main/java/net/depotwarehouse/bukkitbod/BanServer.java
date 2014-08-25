package net.depotwarehouse.bukkitbod;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BanServer {

    private Plugin context;
    private String BANNED_METADATA_KEY;
    private int BAN_LENGTH;
    private String BAN_MESSAGE;

    public BanServer(Plugin plugin) {
        this.context = plugin;
        this.BANNED_METADATA_KEY = plugin.getConfig().getString("banned-metadata-key");
        this.BAN_LENGTH = plugin.getConfig().getInt("ban-length");
        this.BAN_MESSAGE = plugin.getConfig().getString("ban-message");
    }

    public boolean isBanned(Player player) {
        return this.getBanLength(player) > 0;
    }

    public long getBanLength(Player player) {
        if (player.hasMetadata(this.BANNED_METADATA_KEY)) {

            // Cycle through the metadata for our key, and find the one owned by this plugin
            List<MetadataValue> dataList = player.getMetadata(this.BANNED_METADATA_KEY);
            for (MetadataValue value : dataList) {
                Plugin plugin = value.getOwningPlugin();
                if (plugin instanceof BukkitBOD) {
                    long timeStamp = value.asLong();
                    long nowStamp = new Date().getTime();

                    if (nowStamp > timeStamp) {
                        player.removeMetadata(this.BANNED_METADATA_KEY, this.context);
                        return 0;
                    } else {
                        return timeStamp;
                    }
                }
            }
        }
        return 0;
    }

    public void ban(Player player) {
        if (this.isBanned(player)) {
            // Todo error, already banned
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, this.BAN_LENGTH);
        long banTimeStamp = calendar.getTime().getTime();
        MetadataValue value = new FixedMetadataValue(this.context, banTimeStamp);
        player.kickPlayer(this.BAN_MESSAGE + " Banned for: " + this.BAN_LENGTH + " seconds!");
        player.setMetadata(this.BANNED_METADATA_KEY, value);
    }

    public int banTotal() {
        int banCount = 0;
        for (Player player : this.context.getServer().getOnlinePlayers()) {
            if (this.isBanned(player)) {
                banCount++;
            }
        }
        return banCount;
    }
}

