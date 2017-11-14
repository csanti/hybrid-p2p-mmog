package edu.upc.tfg.common.entities;

import edu.upc.tfg.common.ClientConnection;
import edu.upc.tfg.common.utils.Position;

public class Player {

    private String username;
    private int entityId;
    private Position playerPos = new Position();
    private int instanceId;
    private ClientConnection con;

    public Player(String username, int entityId) {
        this.username = username;
        this.entityId = entityId;
    }
    public Player(String username, ClientConnection con) {
        this.username = username;
        this.con = con;
    }

    public ClientConnection getCon() {
        return con;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Position getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(Position playerPos) {
        this.playerPos = playerPos;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public void setCon(ClientConnection con) {
        this.con = con;
    }
}
