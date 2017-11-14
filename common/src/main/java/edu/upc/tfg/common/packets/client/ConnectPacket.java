package edu.upc.tfg.common.packets.client;

import edu.upc.tfg.common.ClientConnection;
import edu.upc.tfg.common.gameserver.World;
import edu.upc.tfg.common.packets.ClientPacket;
import edu.upc.tfg.common.GameMessage;
import edu.upc.tfg.common.entities.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.log4j.Logger;

public class ConnectPacket extends ClientPacket {
    private static final Logger logger = Logger.getLogger(ConnectPacket.class.getName());

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
        logger.info("[SEND] ClientConnection request with username: "+clientName);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ClientConnection conn) {
        logger.info("[RECV] ClientConnection request from: "+clientName);
        World.getInstance().spawnPlayer(new Player(clientName, conn));
    }
}
