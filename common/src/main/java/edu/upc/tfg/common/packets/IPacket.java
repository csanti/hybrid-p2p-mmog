package edu.upc.tfg.common.packets;

import io.netty.buffer.ByteBuf;

public interface IPacket {

    void decode(ByteBuf payload) throws Exception;
    void encode();
}
