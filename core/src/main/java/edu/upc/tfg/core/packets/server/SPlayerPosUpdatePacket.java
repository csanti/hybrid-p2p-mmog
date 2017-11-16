package edu.upc.tfg.core.packets.server;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.LocalClientInstance;
import edu.upc.tfg.core.packets.ServerPacket;
import edu.upc.tfg.core.utils.Position;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class SPlayerPosUpdatePacket extends ServerPacket {
    private static final Logger logger = Logger.getLogger(SPlayerPosUpdatePacket.class.getName());

    private int posX;
    private int posY;

    public SPlayerPosUpdatePacket(Position newPos) {
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
    public void handle(ChannelHandlerContext ctx, LocalClientInstance inst) {
        //logger.info("ClientPosUpdate x: "+posX+" y: "+posY);
    }
}
