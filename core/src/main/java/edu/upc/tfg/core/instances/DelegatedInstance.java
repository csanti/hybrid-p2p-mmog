package edu.upc.tfg.core.instances;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.packets.ServerPacket;
import edu.upc.tfg.core.utils.Position;
import org.apache.log4j.Logger;

public class DelegatedInstance extends MasterGameInstance {
    private static final Logger logger = Logger.getLogger(DelegatedInstance.class.getName());

    private int instanceId;

    public DelegatedInstance(int instanceId) {
        this.instanceId = instanceId;
        logger.info("Creating DelegateInstance with id "+instanceId);
    }

    @Override
    public void spawnEntity(String username, ClientConnection con) {

    }

    @Override
    public void sendToAllPlayers(ServerPacket packet, int senderEID) {

    }

    @Override
    public void updateEntityPosition(int entityId, Position position) {

    }

    @Override
    public void removeEntity(int entityId) {

    }
}
