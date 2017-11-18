package edu.upc.tfg.core.packets.server;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.LocalClientInstance;
import edu.upc.tfg.core.packets.ServerPacket;
import edu.upc.tfg.core.utils.Position;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class NewEntityPacket extends ServerPacket {
    private static final Logger logger = Logger.getLogger(NewEntityPacket.class.getName());

    private int entityId;
    private int nameLenght;
    private String name;
    private int initialPosX;
    private int initialPosY;

    public NewEntityPacket(int entityId, String name, Position spawnPosition) {
        this.entityId = entityId;
        this.name = name;
        this.initialPosX = spawnPosition.getPositionX();
        this.initialPosY = spawnPosition.getPositionY();
    }
    public NewEntityPacket() {}

    @Override
    public void read(ByteBuf payload) throws Exception {
        entityId = payload.readInt();
        nameLenght = payload.readInt();
        name = payload.readSlice(nameLenght).toString(Charset.forName("UTF-8"));
        initialPosX = payload.readInt();
        initialPosY = payload.readInt();
    }

    @Override
    public GameMessage write() {
        payload = Unpooled.buffer();
        payload.writeInt(entityId);
        ByteBuf nameBuf;
        try {
            nameBuf = Unpooled.wrappedBuffer(name.getBytes("UTF-8"));
        } catch(UnsupportedEncodingException ex) {
            logger.error("UnsuportedEncodingException");
            nameBuf = Unpooled.wrappedBuffer(name.getBytes());
        }
        payload.writeInt(nameBuf.readableBytes());
        payload.writeBytes(nameBuf);
        payload.writeInt(initialPosX);
        payload.writeInt(initialPosY);
        logger.info("[SEND] New entity - EID: "+entityId+" name: "+name+" x: "+initialPosX+" y: "+initialPosY);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, LocalClientInstance isnt) {
        logger.info("[RECV] New entity - EID: "+entityId+" name: "+name+" x: "+initialPosX+" y: "+initialPosY);
    }
}
