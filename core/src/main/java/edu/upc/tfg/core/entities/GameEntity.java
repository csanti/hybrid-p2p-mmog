package edu.upc.tfg.core.entities;

import edu.upc.tfg.core.utils.Position;

public class GameEntity {

    protected int entityId;
    protected String name;
    protected Position position = new Position();

    public GameEntity(String name, int entityId, Position pos) {
        this.name = name;
        this.entityId = entityId;
        this.position = new Position(pos.getPositionX(), pos.getPositionY());
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

    public void setPosition(Position newPos) { this.position = newPos; }
}
