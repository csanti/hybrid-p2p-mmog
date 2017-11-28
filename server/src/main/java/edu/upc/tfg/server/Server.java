package edu.upc.tfg.server;

import edu.upc.tfg.core.packets.PacketMapping;
import edu.upc.tfg.core.server.GameServer;
import org.apache.log4j.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(final String[] args) throws Exception {
        logger.info("Entry point");
        PacketMapping.mapClientPackets();
        PacketMapping.mapServerPackets();

        new GameServer(0).run(8182);

        System.in.read();
    }

}
