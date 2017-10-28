package edu.upc.tfg.common.packets.client;

import edu.upc.tfg.common.packets.Packet;
import io.netty.buffer.ByteBuf;

public class ConnectPacket extends Packet {

    private ByteBuf payload;

    private String clientName;

    public ConnectPacket(String clientName) {
        this.clientName = clientName;
    }
    public ConnectPacket() {

    }

    @Override
    public void read(ByteBuf payload) throws Exception{
        clientName = new String(payload.slice(0,20).array(), "UTF-8");
    }

    @Override
    public void write() {

    }
}
