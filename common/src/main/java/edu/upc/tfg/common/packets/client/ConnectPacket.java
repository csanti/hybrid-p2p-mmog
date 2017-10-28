package edu.upc.tfg.common.packets.client;

import edu.upc.tfg.common.Connection;
import edu.upc.tfg.common.packets.ClientPacket;
import edu.upc.tfg.common.GameMessage;
import edu.upc.tfg.common.packets.PacketMapping;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ConnectPacket extends ClientPacket {

    private String clientName;

    public ConnectPacket(String clientName) {
        this.clientName = clientName;
    }
    public ConnectPacket() {

    }

    @Override
    public void read(ByteBuf payload) throws Exception{
        clientName = new String(payload.array(), "UTF-8");
    }

    @Override
    public GameMessage write() {
        // pasar el clientname a bytes y copiarlo a payload
        this.payload = Unpooled.wrappedBuffer(clientName.getBytes());
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(Connection conn) {
        conn.setUsername(clientName);
    }
}
