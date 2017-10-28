package edu.upc.tfg.common;

import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class Connection {
    private static final Logger logger = Logger.getLogger(Connection.class.getName());

    private ChannelHandlerContext ctx;
    private String username;

    public Connection(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        //logger.info("Setting username to "+username);
        this.username = username;
    }
}
