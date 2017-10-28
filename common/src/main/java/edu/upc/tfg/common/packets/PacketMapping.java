package edu.upc.tfg.common.packets;

import edu.upc.tfg.common.packets.client.ConnectPacket;

import java.util.HashMap;
import java.util.Map;

public class PacketMapping {

    public static Map<Integer, Class<? extends Packet>> clientPackets = new HashMap<Integer,Class<? extends Packet>>();

    public static void mapClientPackets() {
        clientPackets.put(0x01, ConnectPacket.class);
    }
}
