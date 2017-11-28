package edu.upc.tfg.core.instances;

import edu.upc.tfg.core.entities.GameEntity;
import edu.upc.tfg.core.packets.client.CPlayerPosUpdatePacket;
import edu.upc.tfg.core.packets.client.ConnectPacket;
import edu.upc.tfg.core.packets.client.ServerCreationResultPacket;
import edu.upc.tfg.core.server.GameServer;
import edu.upc.tfg.core.utils.Position;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocalClientInstance {
    private static final Logger logger = Logger.getLogger(LocalClientInstance.class.getName());

    private ChannelHandlerContext serverCtx;

    private String instanceOwnerName;
    private List<GameEntity> remoteEntities = new ArrayList<GameEntity>();
    private GameEntity localPlayer;
    private Position startPosition;

    public void init(ChannelHandlerContext serverCtx, String instanceOwnerName) {
        // send connect message
        this.serverCtx = serverCtx;
        this.instanceOwnerName = instanceOwnerName;
        serverCtx.writeAndFlush(new ConnectPacket(instanceOwnerName).write());
    }

    public void spawnLocalPlayer(Position pos, int entityId) {
        localPlayer = new GameEntity(instanceOwnerName, entityId, pos);
        startPosition = new Position(pos.getPositionX(), pos.getPositionY());
        logger.info("[LocalWorld "+instanceOwnerName+"] spawin local player - entityId: "+entityId+" x: "+pos.getPositionX()+" y: "+pos.getPositionY());
        logger.info("[LocalWorld "+instanceOwnerName+"] Starting game loop...");
        // fake game loop que simula envio constante de paquetes
        initGameLoop();
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

    public void initGameLoop() {
        serverCtx.channel().eventLoop().scheduleAtFixedRate(new Runnable() {
            public void run() {
                // simple update of player position
                if(localPlayer.getPosition().getPositionX() < startPosition.getPositionX() + 1000) {
                    localPlayer.getPosition().setPositionX(localPlayer.getPosition().getPositionX() + 1);
                } else {
                    localPlayer.getPosition().setPositionX(startPosition.getPositionX());
                }
                try {
                    serverCtx.writeAndFlush(new CPlayerPosUpdatePacket(localPlayer.getPosition()).write());
                } catch (Exception ex) {
                    logger.error("[LocalWorld "+instanceOwnerName+"] GameLoop error: ", ex);
                }
            }
        },2000 ,2000, TimeUnit.MILLISECONDS);
    }

    public void initNewDelegatedInstance(int instanceId, int port, int maxConnections, int duration) {

        // parar game loop
        try {
            serverCtx.writeAndFlush(new ServerCreationResultPacket(instanceId, 0, port).write());
            new GameServer(instanceId).run(port); // bloquea

        } catch (Exception ex) {
            logger.error("Error while initiating p2p server", ex);
            serverCtx.writeAndFlush(new ServerCreationResultPacket(instanceId, 1).write());
        }

    }

    public void stablishConnectionWithNewP2PServer(String ip, int port) {
        
    }
}
