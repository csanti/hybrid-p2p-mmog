package edu.upc.tfg.common.gameserver;

import edu.upc.tfg.common.GameMessage;
import edu.upc.tfg.common.entities.Player;
import edu.upc.tfg.common.packets.server.NewEntityPacket;
import edu.upc.tfg.common.packets.server.SpawnPositionPacket;
import edu.upc.tfg.common.utils.Position;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class World {
    private static final Logger logger = Logger.getLogger(World.class.getName());

    public static World instance = null;

    private List<Player> playerList = new ArrayList<Player>();
    private Position lastSpawnPostion;

    private int worldSizeX;
    private int worldSizeY;
    private int spawnOffset;
    private int lastEID;


    protected World() {
        lastSpawnPostion = new Position(0,0);
        worldSizeX = 1000;
        worldSizeY = 500;
        spawnOffset = 50;
    }

    public static World getInstance() {
        if(instance == null) {
            instance = new World();
        }

        return instance;
    }

    public void spawnPlayer(Player newPlayer) {
        // por defecto el jugador empieza en el mundo principal - id de instancia = 0
        newPlayer.setInstanceId(0);

        Position spawnPosition = new Position();
        // determinar la nueva posicion de spawn
        if(lastSpawnPostion.getPositionX() + spawnOffset > worldSizeX) {
            spawnPosition.setPositionX(0);
            if(lastSpawnPostion.getPositionY() + spawnOffset > worldSizeY) {
                spawnPosition.setPositionY(0);
            } else {
                spawnPosition.setPositionY(lastSpawnPostion.getPositionY() + spawnOffset);
            }
        } else {
            spawnPosition.setPositionX(lastSpawnPostion.getPositionX() + spawnOffset);
            spawnPosition.setPositionY(lastSpawnPostion.getPositionY());
        }
        lastSpawnPostion.setPositionX(spawnPosition.getPositionX());
        lastSpawnPostion.setPositionY(spawnPosition.getPositionY());

        newPlayer.getPlayerPos().setPositionX(spawnPosition.getPositionX());
        newPlayer.getPlayerPos().setPositionY(spawnPosition.getPositionY());
        newPlayer.setEntityId(lastEID + 1);

        // enviar al jugador la posicion en la que aparecerá
        newPlayer.getCon().sendGameMessage(new SpawnPositionPacket(lastEID + 1, spawnPosition).write());

        playerList.add(newPlayer);

        // informar de la existencia de la nueva entidad al resto de clientes
        sendToLOSplayers(new NewEntityPacket(lastEID + 1, newPlayer.getUsername(), spawnPosition).write(), 0, newPlayer.getEntityId());
        lastEID++;


    }

    public void sendToLOSplayers(GameMessage msg, int instanceId, int senderEID) {
        // por el momento se envia a todos los jugadores que estén en la instancia
        // TODO: modificar

        for(Player player : playerList) {
            if(player.getInstanceId() == instanceId && player.getEntityId() != senderEID) {
                player.getCon().sendGameMessage(msg);
            }
        }
    }

}
