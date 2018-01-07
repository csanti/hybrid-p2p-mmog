package edu.upc.tfg.server;

import edu.upc.tfg.core.entities.DelegatedInstanceInfo;
import edu.upc.tfg.core.entities.Player;
import edu.upc.tfg.core.instances.MainInstance;
import edu.upc.tfg.core.packets.PacketMapping;
import edu.upc.tfg.core.server.GameServer;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(final String[] args) throws Exception {
        logger.info("Entry point...");
        PacketMapping.mapClientPackets();
        PacketMapping.mapServerPackets();
        //main instance
        MainInstance mainInstance = new MainInstance();

        new GameServer(mainInstance).run(8182);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean looping = true;
        while(looping) {
            String line = br.readLine();
            String[] s = line.split("\\s+");
            if(s[0].equals("s")) {
                List<Player> playerList = mainInstance.getPlayerList();
                List<Player> playingList = mainInstance.getPlayingPlayerList();
                System.out.println("--------------------------------------------------------------------------------------");
                System.out.println("Player list count = "+playerList.size());
                System.out.println("Player on main instance count = "+playingList.size());
                for(Player p : playerList) {
                    System.out.println("Username: "+p.getName()+" - Status: "+p.getStatus()+" - InstanceId: "+p.getInstanceId()+" - EntityId: "+p.getEntityId());
                }
                System.out.println("--------------------------------------------------------------------------------------");

            }
            else if(s[0].equals("d")) {
                try {
                    int numDelegatedPlayers = Integer.parseInt(s[1]);
                    if(numDelegatedPlayers >= 2 ){
                        mainInstance.delegateInstance(numDelegatedPlayers);
                    }
                    else {
                        System.out.println("Can't delegate less than 2 players");
                    }

                } catch(Exception ex) {
                    System.out.println("Error parsing command: "+ex.toString());
                }
            }
            else if(s[0].equals("i")) {
                List<DelegatedInstanceInfo> delegatedInstances = mainInstance.getDelegatedInstances();
                System.out.println("--------------------------------------------------------------------------------------");
                System.out.println("Number of delegated instances = "+delegatedInstances.size());

                for(DelegatedInstanceInfo d : delegatedInstances) {
                    System.out.println("ServerEndP: "+d.getServerIp()+" - ServerPort: "+d.getServerPort()+" - ConnectedPlayers: "+d.getPlayerList().size());
                }
                System.out.println("--------------------------------------------------------------------------------------");
            }
        }

    }

}
