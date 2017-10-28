package edu.upc.tfg.common.packets;

import edu.upc.tfg.common.Connection;
import io.netty.buffer.ByteBuf;

public abstract class ClientPacket {

    protected ByteBuf payload;

    public abstract void read(ByteBuf payload) throws Exception;
    public abstract GamePacket write();
    public abstract void handle(Connection conn);


}
