package edu.upc.tfg.common.packets.client;

import edu.upc.tfg.common.Connection;
import edu.upc.tfg.common.packets.ClientPacket;
import edu.upc.tfg.common.packets.GamePacket;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

public class HelloPacket extends ClientPacket {
    private static final Logger logger = Logger.getLogger(HelloPacket.class.getName());


    @Override
    public void read(ByteBuf payload) throws Exception {

    }

    @Override
    public GamePacket write() {
        return null;
    }

    @Override
    public void handle(Connection conn) {
        logger.info("Hello");
    }
}
