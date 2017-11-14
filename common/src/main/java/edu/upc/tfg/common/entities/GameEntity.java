package edu.upc.tfg.common.entities;

import edu.upc.tfg.common.utils.Position;

public class GameEntity {

    private int entityId;
    private String name;
    private Position position = new Position();

    public GameEntity(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
