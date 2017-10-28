package edu.upc.tfg.common.packets;

import io.netty.buffer.ByteBuf;

public class GamePacket {
    private int id;
    private ByteBuf payload;

    public GamePacket(int id, ByteBuf payload) {
        this.id = id;
        this.payload = payload;
    }

    public int getId() {
        return id;
    }

    public ByteBuf getPayload() {
        return payload;
    }

}
