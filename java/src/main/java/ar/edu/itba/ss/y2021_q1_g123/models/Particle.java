package ar.edu.itba.ss.y2021_q1_g123.models;

import java.util.*;

public class Particle {
    private final int id;
    private final double radius;
    private final double property;
    private final Set<Particle> neighbors;
    private Position position;
    private Velocity velocity;

    public Particle(int id, double radius, double property) {
        this.id = id;
        this.radius = radius;
        this.property = property;
        this.neighbors = new HashSet<>();
    }

    public int getId() {
        return this.id;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getProperty() {
        return this.property;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public Collection<Particle> getNeighbors() {
        return this.neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return this.getId() == particle.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
