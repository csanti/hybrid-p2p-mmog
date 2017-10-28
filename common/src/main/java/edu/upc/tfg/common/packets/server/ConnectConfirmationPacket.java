package edu.upc.tfg.common.packets.server;

import edu.upc.tfg.common.Connection;
import edu.upc.tfg.common.GameMessage;
import edu.upc.tfg.common.packets.ServerPacket;
import io.netty.buffer.ByteBuf;

public class ConnectConfirmationPacket extends ServerPacket {


    @Override
    public void read(ByteBuf payload) throws Exception {

    }

    @Override
    public GameMessage write() {
        return null;
    }

    @Override
    public void handle(Connection conn) {

    }
}
