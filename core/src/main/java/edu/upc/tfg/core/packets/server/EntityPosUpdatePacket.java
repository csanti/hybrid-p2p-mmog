package edu.upc.tfg.core.packets.server;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.LocalClientInstance;
import edu.upc.tfg.core.packets.ServerPacket;
import edu.upc.tfg.core.utils.Position;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class EntityPosUpdatePacket extends ServerPacket {
    private static final Logger logger = Logger.getLogger(NewEntityPacket.class.getName());

    private int entityId;
    private int newPosX;
    private int newPosY;

    public EntityPosUpdatePacket() {

    }
    public EntityPosUpdatePacket(int entityId, Position newPos) {
        this.entityId = entityId;
        this.newPosX = newPos.getPositionX();
        this.newPosY = newPos.getPositionY();
    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        entityId = payload.readInt();
        newPosX = payload.readInt();
        newPosY = payload.readInt();
    }

    @Override
    public GameMessage write() {
        payload = Unpooled.buffer();
        payload.writeInt(entityId);
        payload.writeInt(newPosX);
        payload.writeInt(newPosY);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, LocalClientInstance inst) {
        //logger.info("Entity pos update");
        inst.updateEntityPosition(entityId, new Position(newPosX, newPosY));
    }
}
