package edu.upc.tfg.core.instances;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.entities.GameEntity;
import edu.upc.tfg.core.entities.Player;
import edu.upc.tfg.core.packets.ServerPacket;
import edu.upc.tfg.core.packets.server.NewEntityPacket;
import edu.upc.tfg.core.packets.server.SpawnPositionPacket;
import edu.upc.tfg.core.utils.Position;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DelegatedInstance extends MasterGameInstance {
    private static final Logger logger = Logger.getLogger(DelegatedInstance.class.getName());
    private List<Player> playerList = new ArrayList<Player>();
    private List<Player> playingPlayerList = new ArrayList<Player>();

    private int instanceId;

    private Position lastSpawnPosition;
    private int lastEID;
    private int spawnOffset;
    private int worldSizeX;
    private int worldSizeY;

    public DelegatedInstance(int instanceId) {
        this.instanceId = instanceId;
        lastSpawnPosition = new Position(0,0);
        spawnOffset = 50;
        worldSizeX = 1000;
        worldSizeY = 500;
        lastEID = 0;
        logger.info("Creating DelegateInstance with id "+instanceId);
    }

    @Override
    public void spawnEntity(String username, ClientConnection con) {
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

        Player newPlayer = new Player(username, lastEID + 1, spawnPosition, con);

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
    public void sendToAllPlayers(ServerPacket packet, int senderEID) {

    }

    @Override
    public void updateEntityPosition(int entityId, Position position) {

    }

    @Override
    public void removeEntity(int entityId) {

    }

}
