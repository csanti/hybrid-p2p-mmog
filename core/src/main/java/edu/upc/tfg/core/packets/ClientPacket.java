package edu.upc.tfg.core.packets;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.MasterGameInstance;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

public abstract class ClientPacket {
    private static final Logger logger = Logger.getLogger(ClientPacket.class.getName());

    protected ByteBuf payload;

    public abstract void read(ByteBuf payload) throws Exception;
    public abstract GameMessage write();
    public abstract void handle(ClientConnection conn, MasterGameInstance inst);

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
