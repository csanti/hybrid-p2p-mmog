package edu.upc.tfg.core.packets;

import edu.upc.tfg.core.packets.client.CPlayerPosUpdatePacket;
import edu.upc.tfg.core.packets.client.ConnectPacket;
import edu.upc.tfg.core.packets.server.NewEntityPacket;
import edu.upc.tfg.core.packets.server.SPlayerPosUpdatePacket;
import edu.upc.tfg.core.packets.server.SpawnPositionPacket;

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
        clientIdPacketMap.put(0x07, CPlayerPosUpdatePacket.class);
        clientPacketIdMap.put(CPlayerPosUpdatePacket.class, 0x07);
    }

    public static void mapServerPackets() {

        serverIdPacketMap.put(0x03, SpawnPositionPacket.class);
        serverPacketIdMap.put(SpawnPositionPacket.class, 0x03);
        serverIdPacketMap.put(0x04, NewEntityPacket.class);
        serverPacketIdMap.put(NewEntityPacket.class, 0x04);
        serverIdPacketMap.put(0x05, SPlayerPosUpdatePacket.class);
        serverPacketIdMap.put(SPlayerPosUpdatePacket.class, 0x05);
    }
}
