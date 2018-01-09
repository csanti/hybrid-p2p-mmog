package edu.upc.tfg.core.entities;

import edu.upc.tfg.core.utils.Position;

public class GameEntity {

    protected int entityId;
    protected Position position = new Position();

    public GameEntity(int entityId, Position pos) {
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

    public void setPosition(Position newPos) { this.position = newPos; }
}
