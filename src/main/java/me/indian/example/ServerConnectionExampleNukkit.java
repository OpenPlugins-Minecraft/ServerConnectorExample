package me.indian.example;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.TextFormat;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import me.indian.connection.ServerConnectNukkit;
import me.indian.connection.logger.ConnectorLogger;
import me.indian.connection.ping.PingServer;

import java.net.InetSocketAddress;

public class ServerConnectionExampleNukkit extends PluginBase {


    @Override
    public void onEnable() {
        final PluginManager pluginManager = this.getServer().getPluginManager();
        if (pluginManager.getPlugin("PlaceholderAPI") == null || pluginManager.getPlugin("KotlinLib") == null || pluginManager.getPlugin("ServerConnection") == null) {
            pluginManager.disablePlugin(this);
            return;
        }
        this.registerPlaceholders();


    }


    private void registerPlaceholders() {
        try {
            final PlaceholderAPI api = PlaceholderAPI.getInstance();
            final String prefix = "example_";


            // connector loger
            final ConnectorLogger logger = ServerConnectNukkit.getInstance().getNukkitLogger();

            // client addres
            final InetSocketAddress client1 = new InetSocketAddress("0.0.0.0", 19110);
            final InetSocketAddress client2 = new InetSocketAddress("0.0.0.0", 19111);
            // server addres
            final InetSocketAddress skyblockAddress = new InetSocketAddress("play.skyblockpe.com", 19132);
            final InetSocketAddress cubecraftAddres = new InetSocketAddress("play.cubecraft.net", 19132);

            //Ping server instance
            final PingServer skyblock = new PingServer("Skyblockpe ping ", client1, skyblockAddress, logger);
            final PingServer cubecraft = new PingServer("Cubecraft ping ", client2, cubecraftAddres, logger);
            //now try connect to server
            skyblock.tryToConnect();
            cubecraft.tryToConnect();


            api.builder(prefix + "skyblock", Integer.class)
                    .visitorLoader(entry -> skyblock.getPlayers())
                    .build();


            api.builder(prefix + "cubecraft", Integer.class)
                    .visitorLoader(entry -> cubecraft.getPlayers())
                    .build();

            api.builder(prefix + "both", Integer.class)
                    .visitorLoader(entry -> cubecraft.getPlayers() + skyblock.getPlayers())
                    .build();

            this.getLogger().info(TextFormat.GREEN + "Loaded placeholderapi placeholders");
        } catch (final Exception exception) {
            this.getLogger().error(TextFormat.RED + "Loading placeholders failed ");
            exception.printStackTrace();
        }
    }
}