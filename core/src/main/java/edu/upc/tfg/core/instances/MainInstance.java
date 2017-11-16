package edu.upc.tfg.core.instances;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.entities.GameEntity;
import edu.upc.tfg.core.entities.Player;
import edu.upc.tfg.core.packets.server.NewEntityPacket;
import edu.upc.tfg.core.packets.server.SpawnPositionPacket;
import edu.upc.tfg.core.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class MainInstance extends MasterGameInstance {

    private List<Player> playerList = new ArrayList<Player>();

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


        // informar de la existencia de la nueva entidad al resto de clientes
        sendToAllPlayers(new NewEntityPacket(lastEID + 1, newPlayer.getName(), spawnPosition).write(), newPlayer.getEntityId());

        lastEID++;
    }

    @Override
    public void updateEntityPosition(int entityId, Position position) {

    }

    @Override
    public void removeEntity(int entityId) {

    }

    @Override
    public void sendToAllPlayers(GameMessage msg, int senderEID) {
        for(Player player : playerList) {
            if(player.getEntityId() != senderEID) {
                player.getCon().sendGameMessage(msg);
            }
        }
    }
}
