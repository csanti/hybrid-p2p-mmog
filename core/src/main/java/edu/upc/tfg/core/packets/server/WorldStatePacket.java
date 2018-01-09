package edu.upc.tfg.core.packets.server;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.entities.GameEntity;
import edu.upc.tfg.core.entities.Player;
import edu.upc.tfg.core.instances.LocalClientInstance;
import edu.upc.tfg.core.packets.ServerPacket;
import edu.upc.tfg.core.utils.Position;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WorldStatePacket extends ServerPacket {
    private static final Logger logger = Logger.getLogger(WorldStatePacket.class.getName());

    private List<GameEntity> entityStates;
    private int size;

    public WorldStatePacket() {

    }
    public WorldStatePacket(List<GameEntity> entityStates, int size) {
        this.entityStates = entityStates;
        this.size = size;
    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        size = payload.readInt();
        entityStates = new ArrayList<GameEntity>();
        for(int i = 0; i < size; i++){
            GameEntity e = new GameEntity(payload.readInt(), new Position(payload.readInt(), payload.readInt()));
            entityStates.add(e);
        }
    }

    @Override
    public GameMessage write() {
        payload = Unpooled.buffer();
        payload.writeInt(size);
        for(GameEntity e: entityStates) {
            payload.writeInt(e.getEntityId());
            payload.writeInt(e.getPosition().getPositionX());
            payload.writeInt(e.getPosition().getPositionY());
        }
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, LocalClientInstance inst) {
        inst.updateWorldState(entityStates);
    }
}
