package edu.upc.tfg.core.packets.server;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.LocalClientInstance;
import edu.upc.tfg.core.packets.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class RemoveEntityPacket extends ServerPacket {
    private static final Logger logger = Logger.getLogger(RemoveEntityPacket.class.getName());

    private int entityId;

    public RemoveEntityPacket() {

    }
    public RemoveEntityPacket(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        entityId = payload.readInt();
    }

    @Override
    public GameMessage write() {
        payload = Unpooled.buffer();
        payload.writeInt(entityId);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, LocalClientInstance inst) {
        logger.info("RemoveEIDPacket eid: "+entityId);
        inst.removeEntity(entityId);
    }
}
