package edu.upc.tfg.core.instances;

import edu.upc.tfg.core.entities.GameEntity;
import edu.upc.tfg.core.packets.client.ConnectPacket;
import edu.upc.tfg.core.utils.Position;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class LocalClientInstance {
    private static final Logger logger = Logger.getLogger(LocalClientInstance.class.getName());

    private ChannelHandlerContext serverCtx;

    private String instanceOwnerName;
    private List<GameEntity> remoteEntities = new ArrayList<GameEntity>();
    private GameEntity localPlayer;

    public void init(ChannelHandlerContext serverCtx, String instanceOwnerName) {
        // send connect message
        this.serverCtx = serverCtx;
        this.instanceOwnerName = instanceOwnerName;
        serverCtx.writeAndFlush(new ConnectPacket(instanceOwnerName).write());

        //start game loop
    }

    public void spawnLocalPlayer(Position pos, int entityId) {
        localPlayer = new GameEntity(instanceOwnerName, entityId, pos);
        logger.info("[LocalWorld "+instanceOwnerName+"] spawin local player - entityId: "+entityId+" x: "+pos.getPositionX()+" y: "+pos.getPositionY());
    }

    public void updateLocalPlayer(Position pos) {
        throw new NotImplementedException();
    }


    public void spawnEntity(GameEntity entity) {
        remoteEntities.add(entity);
        logger.info("[LocalWorld "+instanceOwnerName+"] spawning entity - entityId: "+entity.getEntityId()+" x: "+entity.getPosition().getPositionX()+" y: "+entity.getPosition().getPositionY());
    }


    public void updateEntityPosition(int entityId, Position newPosition) {
        for(GameEntity entity : remoteEntities) {
            if(entity.getEntityId() == entityId) {
                entity.getPosition().setPositionX(newPosition.getPositionX());
                entity.getPosition().setPositionY(newPosition.getPositionY());
                return;
            }
        }

    }


    public void removeEntity(int entityId) {

    }
}
