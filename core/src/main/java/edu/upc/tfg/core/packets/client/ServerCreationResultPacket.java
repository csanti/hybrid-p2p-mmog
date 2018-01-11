package edu.upc.tfg.core.packets.client;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.MainInstance;
import edu.upc.tfg.core.instances.MasterGameInstance;
import edu.upc.tfg.core.packets.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ServerCreationResultPacket extends ClientPacket {
    private static final Logger logger = Logger.getLogger(ServerCreationResultPacket.class.getName());

    private int instanceId;
    private int result; //0 ok 1 nok
    private int ipLength;
    private String ip;
    private int port;

    public ServerCreationResultPacket() {

    }
    public ServerCreationResultPacket(int instanceId, int result) {
        this.instanceId = instanceId;
        this.result = result;
    }
    public ServerCreationResultPacket(int instanceId, int result, String ip, int port) {
        this.instanceId = instanceId;
        this.result  = result;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        instanceId = payload.readInt();
        result = payload.readInt();
        if(result == 0) {
            port = payload.readInt();
            ipLength = payload.readInt();
            ip = payload.readSlice(ipLength).toString(Charset.forName("UTF-8"));
        }
    }

    @Override
    public GameMessage write() {
        payload = Unpooled.buffer();
        payload.writeInt(instanceId);
        payload.writeInt(result);
        if(result == 0) {
            payload.writeInt(port);
            ByteBuf ipBuf;
            try {
                ipBuf = Unpooled.wrappedBuffer(ip.getBytes("UTF-8"));
            } catch(UnsupportedEncodingException ex) {
                logger.error("UnsuportedEncodingException");
                ipBuf = Unpooled.wrappedBuffer(ip.getBytes());
            }
            payload.writeInt(ipBuf.readableBytes());
            payload.writeBytes(ipBuf);
        }
        logger.info("[SEND] ServerCreationResult: "+result);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ClientConnection conn, MasterGameInstance inst) {
        logger.info("[RECV] ServerCreationResult: "+result);
        if(result == 1) {
            logger.warn("Client couldn't create server, aborting delegation");
            ((MainInstance) inst).abortDelegation(instanceId);
        }
        else if(result == 0) {
            ((MainInstance) inst).notifyPlayersOfNewServer(instanceId, ip, port);
        }
    }
}
