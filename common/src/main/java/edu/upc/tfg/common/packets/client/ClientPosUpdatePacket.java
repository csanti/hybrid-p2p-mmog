package edu.upc.tfg.common.packets.client;

import edu.upc.tfg.common.Connection;
import edu.upc.tfg.common.GameMessage;
import edu.upc.tfg.common.packets.ClientPacket;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

public class ClientPosUpdatePacket extends ClientPacket {
    private static final Logger logger = Logger.getLogger(ClientPosUpdatePacket.class.getName());

    private float posX;
    private float posY;

    public ClientPosUpdatePacket(float posX, float  posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        posX = payload.readFloat();
        posY = payload.readFloat();
    }

    @Override
    public GameMessage write() {
        payload.writeFloat(posX);
        payload.writeFloat(posY);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(Connection conn) {
        logger.info("ClientPosUpdate x: "+posX+" y: "+posY);
    }
}
