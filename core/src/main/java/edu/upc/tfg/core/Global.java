package edu.upc.tfg.core;

public class Global {
    private static Global instance;

    public String P2P_SERVER_IP;

    private Global() {

    }

    public static Global getInstance() {
        if(instance == null) {
            instance = new Global();
            return instance;
        }
        else {
            return instance;
        }
    }


}
