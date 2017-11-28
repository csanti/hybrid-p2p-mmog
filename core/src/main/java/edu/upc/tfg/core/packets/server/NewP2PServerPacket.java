package edu.upc.tfg.core.packets.server;

import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.instances.LocalClientInstance;
import edu.upc.tfg.core.packets.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;


public class NewP2PServerPacket extends ServerPacket {

    private static final Logger logger = Logger.getLogger(NewP2PServerPacket.class.getName());

    private int instanceId;
    private int port;
    private int maxConnections;
    private int duration;

    public NewP2PServerPacket() {

    }
    public NewP2PServerPacket(int instanceId, int port, int maxConnections, int duration) {
        this.instanceId = instanceId;
        this.port = port;
        this.maxConnections = maxConnections;
        this.duration = duration;
    }

    @Override
    public void read(ByteBuf payload) throws Exception {
        instanceId = payload.readInt();
        port = payload.readInt();
        maxConnections = payload.readInt();
        duration = payload.readInt();
    }

    @Override
    public GameMessage write() {
        payload = Unpooled.buffer();
        payload.writeInt(instanceId);
        payload.writeInt(port);
        payload.writeInt(maxConnections);
        payload.writeInt(duration);
        logger.info("[SEND] NewP2PServerPacket request with instanceId: "+instanceId);
        return buildGameMessage(this.getClass());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, LocalClientInstance inst) {
        logger.info("[RECV] Request to create P2P server - instanceId: "+instanceId);
        inst.initNewDelegatedInstance(instanceId, port, maxConnections,duration);
    }
}
