package edu.upc.tfg.server;

import io.netty.buffer.ByteBuf;

public class GamePacket {
    private int id;
    private byte[] payload;

    public GamePacket(int id, byte[] payload) {
        this.id = id;
        this.payload = payload;
    }

    public int getId() {
        return id;
    }

    public byte[] getPayload() {
        return payload;
    }
}
