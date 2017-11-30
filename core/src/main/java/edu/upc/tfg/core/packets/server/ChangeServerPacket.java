package edu.upc.tfg.core.packets.server;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.LocalClientInstance;
import edu.upc.tfg.core.packets.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ChangeServerPacket extends ServerPacket {

    private static final Logger logger = Logger.getLogger(NewP2PServerPacket.class.getName());

    private int op;
    private String serverIp;
    private int serverPort;
    private int instanceId;

    public ChangeServerPacket() {

    }

    public ChangeServerPacket(int op, String serverIp, int serverPort, int instanceId) {
        this.op = op;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.instanceId = instanceId;
    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        op = payload.readInt();
        int ipStringLength = payload.readInt();
        serverIp = payload.readSlice(ipStringLength).toString(Charset.forName("UTF-8"));
        serverPort = payload.readInt();
        instanceId = payload.readInt();
    }

    @Override
    public GameMessage write() {
        payload = Unpooled.buffer();
        payload.writeInt(op);
        ByteBuf ipBuf;
        try {
            ipBuf = Unpooled.wrappedBuffer(serverIp.getBytes("UTF-8"));
        } catch(UnsupportedEncodingException ex) {
            logger.error("UnsuportedEncodingException");
            ipBuf = Unpooled.wrappedBuffer(serverIp.getBytes());
        }
        payload.writeInt(ipBuf.readableBytes());
        payload.writeBytes(ipBuf);
        payload.writeInt(serverPort);
        payload.writeInt(instanceId);
        logger.info("[SEND] ChangeServerPacket");
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, LocalClientInstance inst) {
        logger.info("[RECV] ChangeServerPacket to - op:"+op+" ip: "+serverIp+" port: "+serverPort+" instanceId: "+instanceId);
        if(op == 0) {
            inst.stablishConnectionWithNewP2PServer(serverIp, serverPort, instanceId);
        }

    }
}
