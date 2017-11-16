package edu.upc.tfg.core.packets.server;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.LocalClientInstance;
import edu.upc.tfg.core.packets.ServerPacket;
import edu.upc.tfg.core.utils.Position;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class EntityPosUpdatePacket extends ServerPacket {

    private int entityId;
    private int newPosX;
    private int newPosY;

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
        payload.writeInt(entityId);
        payload.writeInt(newPosX);
        payload.writeInt(newPosY);
        return null;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, LocalClientInstance inst) {

    }
}
