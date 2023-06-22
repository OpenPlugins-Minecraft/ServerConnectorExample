package me.indian.example;


import me.indian.connection.ServerConnectBasic;
import me.indian.connection.logger.ConnectorLogger;
import me.indian.connection.ping.PingServer;

import java.net.InetSocketAddress;

public class ServerConnectionExampleNormal {


    public static void main(String[] args) {

        // connector loger
        final ConnectorLogger logger = new ServerConnectBasic().getBasicLogger();

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


        logger.info("Skyblock Players " + skyblock.getPlayers());
        logger.info("CubeCraft Players " + cubecraft.getPlayers());
        logger.info("Both Players " + cubecraft.getPlayers() + skyblock.getPlayers());

        skyblock.disconnect();
        cubecraft.disconnect();

    }
}