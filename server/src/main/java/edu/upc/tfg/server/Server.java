package edu.upc.tfg.server;

import edu.upc.tfg.core.instances.MainInstance;
import edu.upc.tfg.core.packets.PacketMapping;
import edu.upc.tfg.core.server.GameServer;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(final String[] args) throws Exception {
        logger.info("Entry point");
        PacketMapping.mapClientPackets();
        PacketMapping.mapServerPackets();

        MainInstance mainInstance = new MainInstance();

        new GameServer(mainInstance).run(8182);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean looping = true;
        while(looping) {
            String s = br.readLine();
            if(s.equals("hi")) {
                System.out.println("bye");
            }
        }

    }

}
