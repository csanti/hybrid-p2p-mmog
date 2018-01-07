package edu.upc.tfg.client;

import edu.upc.tfg.core.client.GameClientHandler;
import edu.upc.tfg.core.client.GameClient;
import edu.upc.tfg.core.packets.PacketMapping;
import org.apache.log4j.Logger;

public class ClientGenerator {
    private static final Logger logger = Logger.getLogger(GameClientHandler.class.getName());


    public static void main(final String[] args) {
        logger.info("Entry point");
        int numClients = 50;

        if (args.length == 0) {
            logger.info("No has introducido argumentos");
        }
        else {
            numClients = Integer.parseInt(args[0]);
        }

        PacketMapping.mapClientPackets();
        PacketMapping.mapServerPackets();

        try {
            for(int i = 0; i < numClients; i++) {
                logger.info("Iniciando cliente nº"+i);
                new GameClient("localhost", 8182, "bot"+i).run();
                Thread.sleep(6000);
            }
        } catch(Exception ex) {
            logger.error("Error ", ex);
        }


    }

}
