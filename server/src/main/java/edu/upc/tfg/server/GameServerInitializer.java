package edu.upc.tfg.server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;


public class GameServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(final SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        channel.config().setAutoRead(true);

        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.nulDelimiter()));
        pipeline.addLast("decoder", new GamePacketDecoder());

        pipeline.addLast("handler", new GameServerHandler());
    }
}
