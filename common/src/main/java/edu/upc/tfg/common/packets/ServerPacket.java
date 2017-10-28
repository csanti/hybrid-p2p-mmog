package edu.upc.tfg.common.packets;

import edu.upc.tfg.common.Connection;
import edu.upc.tfg.common.GameMessage;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

public abstract class ServerPacket {
    private static final Logger logger = Logger.getLogger(ServerPacket.class.getName());

    protected ByteBuf payload;

    public abstract void read(ByteBuf payload) throws Exception;
    public abstract GameMessage write();
    public abstract void handle(Connection conn);

    public GameMessage buildGameMessage(Class<? extends ClientPacket> packetType) {
        int msgId = 0;

        if(PacketMapping.clientPacketIdMap.containsKey(packetType)) {
            msgId = PacketMapping.clientPacketIdMap.get(packetType);
        }
        else {
            logger.error("Packetid not found");
        }

        //devuelve un packete con id 0
        return new GameMessage(msgId, payload);
    }
}
