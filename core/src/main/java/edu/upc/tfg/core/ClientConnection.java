package edu.upc.tfg.core;

import edu.upc.tfg.core.entities.Player;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class ClientConnection {
    private static final Logger logger = Logger.getLogger(ClientConnection.class.getName());

    private ChannelHandlerContext ctx;
    private Player player;

    public ClientConnection(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void sendGameMessage(GameMessage msg) {
        if(this.ctx == null) {
            logger.warn("Ctx no inicilizado");
            return;
        }
        this.ctx.writeAndFlush(msg);
    }
}
