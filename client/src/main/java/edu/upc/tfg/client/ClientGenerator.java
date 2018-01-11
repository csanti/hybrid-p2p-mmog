package edu.upc.tfg.client;

import edu.upc.tfg.core.Global;
import edu.upc.tfg.core.client.GameClientHandler;
import edu.upc.tfg.core.client.GameClient;
import edu.upc.tfg.core.packets.PacketMapping;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ClientGenerator {
    private static final Logger logger = Logger.getLogger(GameClientHandler.class.getName());


    public static void main(final String[] args) {
        logger.info("Entry point");
        int numClients = 1000;
        int intervClients = 100;
        int intervTimeSeconds = 61*5;
        int modoOperacion = 2;
        String serverIP = "147.83.118.108";
        String p2pServerIP = "147.83.118.109";

        if (args.length == 0) {
            logger.info("No has introducido argumentos - modo por defecto");
        }
        else {
            modoOperacion = Integer.parseInt(args[0]);
            serverIP = args[1];
            numClients = Integer.parseInt(args[2]);
            p2pServerIP = args[3];
        }

        Global.getInstance().P2P_SERVER_IP = p2pServerIP;

        PacketMapping.mapClientPackets();
        PacketMapping.mapServerPackets();

        List<GameClient> activeClients = new ArrayList<GameClient>();
        try {

            /* --------------------------------- modo 0 ------------------------------------ */
            if(modoOperacion == 0) {
                new GameClient(serverIP, 8182, "mainBot").run();
            }
            /* --------------------------------- modo 1 ------------------------------------ */
            else if(modoOperacion == 1) {
                int i = 0;
                while(true) {
                    int randomNum = 0 + (int)(Math.random() * 100);

                    if(activeClients.size() < numClients && randomNum < 50) {
                        logger.info("Iniciando cliente nº"+i);
                        GameClient newGameClient = new GameClient(serverIP, 8182, "bot"+i);
                        activeClients.add(newGameClient);
                        newGameClient.run();
                        i++;
                    }
                    Thread.sleep(1000);


                    if(randomNum > 70 && activeClients.size() > 1) {
                        int clientToRemove = 0;
                        if(activeClients.size() > 2) {
                            clientToRemove = 0 + (int)(Math.random() * activeClients.size() - 1);
                        }
                        logger.info("Parando cliente en posición "+ clientToRemove);
                        activeClients.get(clientToRemove).stop();
                        activeClients.remove(clientToRemove);
                    }
                }
            }
            /* --------------------------------- modo 2 ------------------------------------ */
            else if(modoOperacion == 2) {
                int intervalCounter = 0;
                for(int i = 0; i < numClients; i++)
                {
                    new GameClient(serverIP, 8182, "bot"+i).run();
                    intervalCounter++;
                    if(intervalCounter == intervClients) {
                        logger.info("Sleeping for "+intervTimeSeconds+" seconds. Total clients started: "+(i+1));
                        Thread.sleep(intervTimeSeconds * 1000);
                        logger.info("Starting interval");
                        intervalCounter = 0;
                    }
                    Thread.sleep(50);
                }
            }
            /* --------------------------------- modo 3 ------------------------------------ */
            else if(modoOperacion == 3) {
                for(int i = 0; i < numClients; i++)
                {
                    new GameClient(serverIP, 8182, "bot"+i).run();
                    Thread.sleep(2000);
                }
            }
        } catch(Exception ex) {
            logger.error("Error ", ex);
        }


    }

}
