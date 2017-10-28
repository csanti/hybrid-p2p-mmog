package edu.upc.tfg.common.packets;

import io.netty.buffer.ByteBuf;

public abstract class Packet {

    public abstract void read(ByteBuf payload) throws Exception;
    public abstract void write();
}
