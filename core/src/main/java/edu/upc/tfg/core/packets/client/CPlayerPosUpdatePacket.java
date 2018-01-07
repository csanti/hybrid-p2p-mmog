package edu.upc.tfg.core.packets.client;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.MasterGameInstance;
import edu.upc.tfg.core.packets.ClientPacket;
import edu.upc.tfg.core.utils.Position;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.log4j.Logger;

public class CPlayerPosUpdatePacket extends ClientPacket {
    private static final Logger logger = Logger.getLogger(CPlayerPosUpdatePacket.class.getName());

    private int posX;
    private int posY;

    public CPlayerPosUpdatePacket(Position newPos) {
        this.posX = newPos.getPositionX();
        this.posY = newPos.getPositionY();
    }
    public CPlayerPosUpdatePacket() {

    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        posX = payload.readInt();
        posY = payload.readInt();
    }

    @Override
    public GameMessage write() {
        payload = Unpooled.buffer();
        payload.writeInt(posX);
        payload.writeInt(posY);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ClientConnection conn, MasterGameInstance inst) {
        //logger.info("[RECVD] Player pos update - Username: "+ conn.getPlayer().getName()+" - EntityID: "+conn.getPlayer().getEntityId());
        //logger.info("        x: "+posX+" y: "+posY);
        inst.updateEntityPosition(conn.getPlayer().getEntityId(), new Position(posX, posY));
    }
}
