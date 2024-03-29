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

    public Particle copy() {
        Particle particle = new Particle(this.id, this.radius, this.property);

        if (this.position != null)
            particle.setPosition(this.position.copy());
        if (this.position != null)
            particle.setVelocity(this.velocity.copy());

        return particle;
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


    /*
    https://en.wikipedia.org/wiki/Periodic_boundary_conditions
     */
    public double periodicDistanceTo(Particle other, double l) {
        double dx = Math.abs(this.position.getX() - other.position.getX());
        double dy = Math.abs(this.position.getY() - other.position.getY());
        dx = (dx > l / 2) ? l - dx : dx;
        dy = (dy > l / 2) ? l - dy : dy;
        double ctr_dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return ctr_dist == 0 ? 0 : ctr_dist - this.getRadius() - other.getRadius();
    }

    public double distanceTo(Particle other) {
        double ctr_dist =
                Math.sqrt(
                        Math.pow(Math.abs(this.position.getX() - other.position.getX()), 2) +
                                Math.pow(Math.abs(this.position.getY() - other.position.getY()), 2)
                );

        return ctr_dist - this.getRadius() - other.getRadius();
    }
}
