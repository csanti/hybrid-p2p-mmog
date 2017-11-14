package edu.upc.tfg.common.gameclient;

import edu.upc.tfg.common.entities.GameEntity;
import edu.upc.tfg.common.packets.client.ConnectPacket;
import edu.upc.tfg.common.utils.Position;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

public class LocalWorld {
    private static final Logger logger = Logger.getLogger(LocalWorld.class.getName());
    private static LocalWorld instance;

    private ChannelHandlerContext ctx;

    private List<GameEntity> remoteEntities = new ArrayList<GameEntity>();
    private GameEntity localPlayer;

    public static LocalWorld getInstance() {
        if(instance == null) {
            instance = new LocalWorld();
        }
        return instance;
    }

    public GameEntity getLocalPlayer() {
        return localPlayer;
    }

    public void init(ChannelHandlerContext ctx, String username) {
        // send connect message
        this.ctx = ctx;
        this.localPlayer = new GameEntity(username);
        ctx.writeAndFlush(new ConnectPacket(username).write());
    }

    public void spawnLocalPlayer(int spawnPositionX, int spawnPositionY, int entityId) {
        localPlayer.setEntityId(entityId);
        localPlayer.getPosition().setPositionX(spawnPositionX);
        localPlayer.getPosition().setPositionY(spawnPositionY);
        logger.info("Local player spawned - EID: "+entityId+" x: "+spawnPositionX+" y: "+spawnPositionY);
    }

    public void spawnRemoteEntity(GameEntity entity) {
        logger.info("Spawning new player "+entity.getName());
        remoteEntities.add(entity);
    }

    public void removeRemoteEntity(GameEntity entity) {
        logger.info("Removing player "+entity.getName()+" from localworld");
        remoteEntities.remove(entity);
    }

    public void updateRempoteRemoteEntity(int entityId, Position newPosition) {
        for(GameEntity entity : remoteEntities) {
            if(entity.getEntityId() == entityId) {
                entity.getPosition().setPositionX(newPosition.getPositionX());
                entity.getPosition().setPositionY(newPosition.getPositionY());
                return;
            }
        }
    }
}
