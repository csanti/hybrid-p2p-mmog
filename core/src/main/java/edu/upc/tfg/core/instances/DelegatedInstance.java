package edu.upc.tfg.core.instances;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.utils.Position;

public class DelegatedInstance extends MasterGameInstance {

    @Override
    public void spawnEntity(String username, ClientConnection con) {

    }

    @Override
    public void sendToAllPlayers(GameMessage msg, int senderEID) {

    }

    @Override
    public void updateEntityPosition(int entityId, Position position) {

    }

    @Override
    public void removeEntity(int entityId) {

    }
}
