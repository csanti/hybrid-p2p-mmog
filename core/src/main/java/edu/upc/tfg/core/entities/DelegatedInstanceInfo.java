package edu.upc.tfg.core.entities;

import edu.upc.tfg.core.instances.DelegatedInstance;

import java.util.ArrayList;
import java.util.List;

public class DelegatedInstanceInfo {

    private int instanceId;
    private List<Player> playerList = new ArrayList<Player>();
    private int serverEntityId;
    private String serverIp;
    private int serverPort;

    public DelegatedInstanceInfo(int instanceId, List<Player> playerList, int serverEntityId) {
        this.instanceId = instanceId;
        this.playerList = playerList;
        this.serverEntityId = serverEntityId;
    }
    public DelegatedInstanceInfo() {

    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public int getServerEntityId() {
        return serverEntityId;
    }

    public void setServerEntityId(int serverEntityId) {
        this.serverEntityId = serverEntityId;
    }
}
