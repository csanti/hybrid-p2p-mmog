package edu.upc.tfg.core;

import io.netty.buffer.ByteBuf;

public class GameMessage {
    private int id;
    private ByteBuf payload;

    public GameMessage(int id, ByteBuf payload) {
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
