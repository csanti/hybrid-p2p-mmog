package edu.upc.tfg.core.instances;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.entities.DelegatedInstanceInfo;
import edu.upc.tfg.core.entities.GameEntity;
import edu.upc.tfg.core.entities.Player;
import edu.upc.tfg.core.packets.ServerPacket;
import edu.upc.tfg.core.packets.server.ChangeServerPacket;
import edu.upc.tfg.core.packets.server.NewEntityPacket;
import edu.upc.tfg.core.packets.server.NewP2PServerPacket;
import edu.upc.tfg.core.packets.server.SpawnPositionPacket;
import edu.upc.tfg.core.utils.Position;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

public class MainInstance extends MasterGameInstance {
    private static final Logger logger = Logger.getLogger(MainInstance.class.getName());
    private List<Player> playerList = new ArrayList<Player>();
    private List<Player> playingPlayerList = new ArrayList<Player>(); // players that are in the main instance
    private List<DelegatedInstanceInfo> delegatedInstances = new ArrayList<DelegatedInstanceInfo>();
    private int instanceCounter;

    private Position lastSpawnPosition;
    private int lastEID;
    private int spawnOffset;
    private int worldSizeX;
    private int worldSizeY;

    public MainInstance() {
        lastSpawnPosition = new Position(0,0);
        spawnOffset = 50;
        worldSizeX = 1000;
        worldSizeY = 500;
        lastEID = 0;
        instanceCounter = 0;
    }

    @Override
    public void spawnEntity(String playerName, ClientConnection con) {

        Position spawnPosition = new Position();
        // determinar la nueva posicion de spawn
        if(lastSpawnPosition.getPositionX() + spawnOffset > worldSizeX) {
            spawnPosition.setPositionX(0);
            if(lastSpawnPosition.getPositionY() + spawnOffset > worldSizeY) {
                spawnPosition.setPositionY(0);
            } else {
                spawnPosition.setPositionY(lastSpawnPosition.getPositionY() + spawnOffset);
            }
        } else {
            spawnPosition.setPositionX(lastSpawnPosition.getPositionX() + spawnOffset);
            spawnPosition.setPositionY(lastSpawnPosition.getPositionY());
        }
        lastSpawnPosition.setPositionX(spawnPosition.getPositionX());
        lastSpawnPosition.setPositionY(spawnPosition.getPositionY());

        Player newPlayer = new Player(playerName, lastEID + 1, spawnPosition, con);

        // enviar al jugador la posicion en la que aparecerá
        newPlayer.getCon().sendGameMessage(new SpawnPositionPacket(lastEID + 1, spawnPosition).write());

        playerList.add(newPlayer);
        playingPlayerList.add(newPlayer);
        con.setPlayer(newPlayer);

        // informar de la existencia de la nueva entidad al resto de clientes
        sendToAllPlayers(new NewEntityPacket(lastEID + 1, newPlayer.getName(), spawnPosition), newPlayer.getEntityId());

        lastEID++;

    }

    @Override
    public void updateEntityPosition(int entityId, Position position) {

    }

    @Override
    public void removeEntity(int entityId) {

    }

    @Override
    public void sendToAllPlayers(ServerPacket packet, int senderEID) {
        for(Player player : playingPlayerList) {
            if(player.getEntityId() != senderEID) {
                player.getCon().sendGameMessage(packet.write());
            }
        }
    }

    public void sendToPlayer(ServerPacket packet, int receiverEID) {
        for(Player player : playingPlayerList) {
            if(player.getEntityId() == receiverEID) {
                player.getCon().sendGameMessage(packet.write());
            }
        }
    }

    public void delegateInstance(int numDelegatedPlayers) {
        // guardar lo que se esta delgando
        if(numDelegatedPlayers < 2 || numDelegatedPlayers > playingPlayerList.size()) {
            logger.warn("Can't delegate the number of players specified");
            return;
        }
        DelegatedInstanceInfo newInstanceInfo;
        int serverEid;
        Player serverPlayer;
        List<Player> delegatedPlayers = new ArrayList<Player>();
        if(playingPlayerList.get(0) != null) {
            serverEid = playingPlayerList.get(0).getEntityId();
            delegatedPlayers.add(playingPlayerList.get(0));
            serverPlayer = playingPlayerList.get(0);
        } else {
            logger.warn("Not enough players in playingPlayerList to select a server");
            return;
        }

        for(int i = 1; i < numDelegatedPlayers; i++) {
            if(playingPlayerList.get(i) != null) {
                delegatedPlayers.add(playingPlayerList.get(i));
            } else {
                logger.warn("Not enough players in playingPlayerList to delegate to the p2p area");
                return;
            }
        }



        newInstanceInfo = new DelegatedInstanceInfo(instanceCounter+1, delegatedPlayers, serverEid);
        // TODO la ip del servidor hay que cojerla de otra forma
        newInstanceInfo.setServerIp(serverPlayer.getCon().getCtx().channel().remoteAddress().toString());
        delegatedInstances.add(newInstanceInfo);


        // enviar notificacion al servidor
        sendToPlayer(new NewP2PServerPacket(instanceCounter+1, 3000+instanceCounter, 10, 10), serverEid);
        instanceCounter++;
        // se esperara a la respuesta del nuevo servidor para avisar al resto de clientes
    }

    public void abortDelegation(int instanceId) {
        for(int i = 0; i < delegatedInstances.size(); i++) {

            if(delegatedInstances.get(i).getInstanceId() == instanceId) {
                DelegatedInstanceInfo instance = delegatedInstances.get(i);

                // notificar aborto
                for(Player player : instance.getPlayerList()) {
                    if(player.getEntityId() != instance.getServerEntityId()) {
                        player.getCon().sendGameMessage(new ChangeServerPacket(1, instance.getServerIp(), instance.getServerPort(), instanceId).write());
                    }
                }

                delegatedInstances.remove(i);
                break;
            }
        }

        // intentarlo con otro cliente ??
    }

    public void notifyPlayersOfNewServer(int instanceId, int serverPort) {
        // el server se ha creado, pero puede haber excepcion inmediatamente
        logger.info("Notifying players of instanceId "+instanceId);

        DelegatedInstanceInfo instance = new DelegatedInstanceInfo();
        for(DelegatedInstanceInfo inst : delegatedInstances) {
            if(inst.getInstanceId() == instanceId) {
                instance = inst;
                break;
            }
        }

        instance.setServerPort(serverPort);

        for(Player player : instance.getPlayerList()) {
            playingPlayerList.remove(player);
            if(player.getEntityId() != instance.getServerEntityId()) {
                player.getCon().sendGameMessage(new ChangeServerPacket(0, instance.getServerIp(), instance.getServerPort(), instanceId).write());
            }
        }
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Player> getPlayingPlayerList() {
        return playingPlayerList;
    }

    public List<DelegatedInstanceInfo> getDelegatedInstances() {
        return delegatedInstances;
    }
}
