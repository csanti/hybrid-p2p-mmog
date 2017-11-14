import com.sun.javafx.scene.layout.region.Margins;
import edu.upc.tfg.common.packets.PacketMapping;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.bootstrap.Bootstrap;
import org.apache.log4j.Logger;

public class GameClient {
    private static final Logger logger = Logger.getLogger(GameClientHandler.class.getName());


    public static void main(final String[] args) {
        logger.info("Entry point");
        int numClients = 2;

        if (args.length == 0) {
            logger.info("No has introducido argumentos");
        }
        else {
            numClients = Integer.parseInt(args[0]);
        }

        PacketMapping.mapClientPackets();
        PacketMapping.mapServerPackets();

        try {
            for(int i = 0; i < numClients; i++) {
                logger.info("Iniciando cliente nº"+i);
                new SimpleBot("localhost", 8182).run();
                Thread.sleep(10000);
            }
        } catch(Exception ex) {
            logger.error("Error ", ex);
        }


    }

}
