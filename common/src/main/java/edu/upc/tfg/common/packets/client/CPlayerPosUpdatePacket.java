package edu.upc.tfg.common.packets.client;

import edu.upc.tfg.common.ClientConnection;
import edu.upc.tfg.common.GameMessage;
import edu.upc.tfg.common.packets.ClientPacket;
import edu.upc.tfg.common.utils.Position;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

public class CPlayerPosUpdatePacket extends ClientPacket {
    private static final Logger logger = Logger.getLogger(CPlayerPosUpdatePacket.class.getName());

    private int posX;
    private int posY;

    public CPlayerPosUpdatePacket(Position newPos) {
        this.posX = newPos.getPositionX();
        this.posY = newPos.getPositionY();
    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        posX = payload.readInt();
        posY = payload.readInt();
    }

    @Override
    public GameMessage write() {
        payload.writeInt(posX);
        payload.writeInt(posY);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ClientConnection conn) {
        logger.info("[RECVD] Player pos update - Username: "+ conn.getPlayer().getUsername()+" - EntityID: "+conn.getPlayer().getEntityId());
        logger.info("        x: "+posX+" y: "+posY);
    }
}
