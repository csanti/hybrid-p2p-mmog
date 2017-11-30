package edu.upc.tfg.core.entities;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.utils.Position;

public class Player extends GameEntity{

    private ClientConnection con;
    private String status;
    private int instanceId;

    public Player(String username, int entityId, Position pos, ClientConnection con) {
        super(username,entityId,pos);
        this.con = con;
    }

    public ClientConnection getCon() {
        return con;
    }

    public void setCon(ClientConnection con) {
        this.con = con;
    }

    public void updateStatusAndInstanceId(String status, int instanceId) {
        this.status = status;
        this.instanceId = instanceId;
    }

    public String getStatus() {
        return status;
    }

    public int getInstanceId() {
        return instanceId;
    }
}
