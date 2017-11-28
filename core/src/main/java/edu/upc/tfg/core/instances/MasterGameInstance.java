package edu.upc.tfg.core.instances;

import edu.upc.tfg.core.ClientConnection;
import edu.upc.tfg.core.GameMessage;
import edu.upc.tfg.core.entities.GameEntity;
import edu.upc.tfg.core.entities.Player;
import edu.upc.tfg.core.packets.ServerPacket;
import edu.upc.tfg.core.utils.Position;

public abstract class MasterGameInstance {

    public abstract void spawnEntity(String username, ClientConnection con);

    public abstract void updateEntityPosition(int entityId, Position position);

    public abstract void removeEntity(int entityId);

    public abstract void sendToAllPlayers(ServerPacket packet, int senderEID);

}
