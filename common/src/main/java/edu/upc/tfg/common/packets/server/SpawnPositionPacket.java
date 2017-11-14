package edu.upc.tfg.common.packets.server;

import edu.upc.tfg.common.GameMessage;
import edu.upc.tfg.common.gameclient.LocalWorld;
import edu.upc.tfg.common.packets.ServerPacket;
import edu.upc.tfg.common.utils.Position;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class SpawnPositionPacket extends ServerPacket {
    private static final Logger logger = Logger.getLogger(SpawnPositionPacket.class.getName());

    private int entityId;
    private int positionX, positionY;

    public SpawnPositionPacket() {
    }
    public SpawnPositionPacket(int entityId, Position spawnPos) {
        this.entityId = entityId;
        this.positionX = spawnPos.getPositionX();
        this.positionY = spawnPos.getPositionY();
    }

    public void read(ByteBuf payload) throws Exception {
        entityId = payload.readInt();
        positionX = payload.readInt();
        positionY = payload.readInt();
    }

    public GameMessage write() {
        payload = Unpooled.buffer();
        payload.writeInt(entityId);
        payload.writeInt(positionX);
        payload.writeInt(positionY);
        logger.info("[SEND] Spawn position - EID: "+entityId+" x: "+positionX+" y: "+positionY);
        return buildGameMessage(this.getClass());
    }

    public void handle(ChannelHandlerContext ctx) {
        logger.info("[RECV] Spawn position - EID: "+entityId+" x: "+positionX+" y: "+positionY);
        LocalWorld.getInstance().spawnLocalPlayer(positionX, positionY, entityId);

    }
}
