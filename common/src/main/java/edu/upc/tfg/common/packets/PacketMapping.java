package edu.upc.tfg.common.packets;

import edu.upc.tfg.common.packets.client.ConnectPacket;
import edu.upc.tfg.common.packets.client.HelloPacket;

import java.util.HashMap;
import java.util.Map;

public class PacketMapping {

    public static Map<Integer, Class<? extends ClientPacket>> clientPackets = new HashMap<Integer,Class<? extends ClientPacket>>();

    public static void mapClientPackets() {
        clientPackets.put(0x01, ConnectPacket.class);
        clientPackets.put(0x00, HelloPacket.class);
    }

    public static void mapServerPackets() {

    }
}
