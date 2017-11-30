package edu.upc.tfg.core.packets;

import edu.upc.tfg.core.packets.client.CPlayerPosUpdatePacket;
import edu.upc.tfg.core.packets.client.ConnectPacket;
import edu.upc.tfg.core.packets.client.KeepAlivePacket;
import edu.upc.tfg.core.packets.client.ServerCreationResultPacket;
import edu.upc.tfg.core.packets.server.*;

import java.util.HashMap;
import java.util.Map;

public class PacketMapping {

    public static Map<Integer, Class<? extends ClientPacket>> clientIdPacketMap = new HashMap<Integer,Class<? extends ClientPacket>>();
    public static Map<Class<? extends ClientPacket>, Integer> clientPacketIdMap = new HashMap<Class<? extends ClientPacket>, Integer>();

    public static Map<Integer, Class<? extends ServerPacket>> serverIdPacketMap = new HashMap<Integer,Class<? extends ServerPacket>>();
    public static Map<Class<? extends ServerPacket>, Integer> serverPacketIdMap = new HashMap<Class<? extends ServerPacket>, Integer>();

    public static void mapClientPackets() {
        clientIdPacketMap.put(0x02, ConnectPacket.class);
        clientPacketIdMap.put(ConnectPacket.class, 0x02);
        clientIdPacketMap.put(0x06, CPlayerPosUpdatePacket.class);
        clientPacketIdMap.put(CPlayerPosUpdatePacket.class, 0x06);
        clientIdPacketMap.put(0x0A, ServerCreationResultPacket.class);
        clientPacketIdMap.put(ServerCreationResultPacket.class, 0x0A);
        clientIdPacketMap.put(0x0B, KeepAlivePacket.class);
        clientPacketIdMap.put(KeepAlivePacket.class, 0x0B);
    }

    public static void mapServerPackets() {

        serverIdPacketMap.put(0x03, SpawnPositionPacket.class);
        serverPacketIdMap.put(SpawnPositionPacket.class, 0x03);
        serverIdPacketMap.put(0x04, NewEntityPacket.class);
        serverPacketIdMap.put(NewEntityPacket.class, 0x04);
        serverIdPacketMap.put(0x05, SPlayerPosUpdatePacket.class);
        serverPacketIdMap.put(SPlayerPosUpdatePacket.class, 0x05);
        serverIdPacketMap.put(0x07, NewP2PServerPacket.class);
        serverPacketIdMap.put(NewP2PServerPacket.class, 0x07);
        serverIdPacketMap.put(0x08, ChangeServerPacket.class);
        serverPacketIdMap.put(ChangeServerPacket.class, 0x08);
    }
}
