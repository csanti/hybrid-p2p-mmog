package edu.upc.tfg.core.packets;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.LocalClientInstance;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public abstract class ServerPacket {
    private static final Logger logger = Logger.getLogger(ServerPacket.class.getName());

    protected ByteBuf payload;

    public abstract void read(ByteBuf payload) throws Exception;
    public abstract GameMessage write();
    public abstract void handle(ChannelHandlerContext ctx, LocalClientInstance inst);

    public GameMessage buildGameMessage(Class<? extends ServerPacket> packetType) {
        int msgId = 0;

        if(PacketMapping.serverPacketIdMap.containsKey(packetType)) {
            msgId = PacketMapping.serverPacketIdMap.get(packetType);
        }
        else {
            logger.error("Packetid not found");
        }

        //devuelve un packete con id 0
        return new GameMessage(msgId, payload);
    }
}
