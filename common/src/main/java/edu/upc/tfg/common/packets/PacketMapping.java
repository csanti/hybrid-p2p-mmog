package edu.upc.tfg.common.packets;

import edu.upc.tfg.common.packets.client.ConnectPacket;
import edu.upc.tfg.common.packets.client.HelloPacket;
import edu.upc.tfg.common.packets.server.ConnectConfirmationPacket;

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
        clientIdPacketMap.put(0x01, HelloPacket.class);
        clientPacketIdMap.put(HelloPacket.class, 0x01);
    }

    public static void mapServerPackets() {
        serverIdPacketMap.put(0x01, ConnectConfirmationPacket.class);
        serverPacketIdMap.put(ConnectConfirmationPacket.class, 0x01);
    }
}
