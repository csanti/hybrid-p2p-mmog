package edu.upc.tfg.server;


import edu.upc.tfg.common.GamePacketDecoder;
import edu.upc.tfg.common.GamePacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;


public class GameServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(final SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //channel.config().setAutoRead(true);

        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.nulDelimiter()));

        pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(30));
        pipeline.addLast("decoder", new GamePacketDecoder());
        pipeline.addLast("encoder", new GamePacketEncoder());
        pipeline.addLast(new GameServerHandler());

    }
}
