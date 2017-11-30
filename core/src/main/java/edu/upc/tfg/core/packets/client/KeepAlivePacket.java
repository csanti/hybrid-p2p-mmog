package edu.upc.tfg.core.packets.client;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.MasterGameInstance;
import edu.upc.tfg.core.packets.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class KeepAlivePacket extends ClientPacket {
    private static final Logger logger = Logger.getLogger(KeepAlivePacket.class.getName());

    private String status;
    private int instanceId;

    public KeepAlivePacket() {

    }

    public KeepAlivePacket(String status, int instanceId) {
        this.status = status;
        this.instanceId = instanceId;
    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        int statusLength = payload.readInt();
        status = payload.readSlice(statusLength).toString(Charset.forName("UTF-8"));
        instanceId = payload.readInt();
    }

    @Override
    public GameMessage write() {
        payload = Unpooled.buffer();
        ByteBuf statusBuf;
        try {
            statusBuf = Unpooled.wrappedBuffer(status.getBytes("UTF-8"));
        } catch(UnsupportedEncodingException ex) {
            logger.error("UnsuportedEncodingException");
            statusBuf = Unpooled.wrappedBuffer(status.getBytes());
        }
        payload.writeInt(statusBuf.readableBytes());
        payload.writeBytes(statusBuf);
        payload.writeInt(instanceId);
        logger.info("[SEND] KeepAlive - Status: "+status+" instanceId: "+instanceId);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ClientConnection conn, MasterGameInstance inst) {
        logger.info("[RECV] KeepAlive - Status: "+status+" instanceId: "+instanceId);
    }
}
