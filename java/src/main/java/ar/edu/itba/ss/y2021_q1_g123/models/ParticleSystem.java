package ar.edu.itba.ss.y2021_q1_g123.models;

import java.util.*;

public class ParticleSystem implements Iterable<Particle> {
    private final int length;
    private final Queue<Particle> particles;
    private int timeZero;

    public ParticleSystem(int length, int totalParticles) {
        this.length = length;
        this.particles = new ArrayDeque<>(totalParticles);
    }

    public int getLength() {
        return this.length;
    }

    public int getTimeZero() {
        return this.timeZero;
    }

    public int getTotalParticles() {
        return this.particles.size();
    }

    public void setTimeZero(int timeZero) {
        this.timeZero = timeZero;
    }

    public void enqueueParticle(Particle particle) {
        this.particles.add(particle);
    }

    public Particle dequeueParticle() {
        return this.particles.poll();
    }

    public Collection<Particle>[][] createMatrix(int size, boolean periodic, double radius) {
        Collection<Particle>[][] matrix = (Collection<Particle>[][]) new Collection[size][size];

        ParticleSystem.initializeMatrix(matrix);
        this.populateMatrix(matrix);
        ParticleSystem.calculateNeighbors(matrix, periodic, this.length, radius);

        return matrix;
    }

    @Override
    public Iterator<Particle> iterator() {
        return this.particles.iterator();
    }

    private void populateMatrix(Collection<Particle>[][] matrix) {
        double cellSize = this.length / (double) matrix.length;

        for (Particle particle : this.particles) {
            int xCell = (int) Math.floor(particle.getPosition().getX() / cellSize);
            int yCell = (int) Math.floor(particle.getPosition().getY() / cellSize);

            matrix[xCell][yCell].add(particle);
        }
    }

    private static void initializeMatrix(Collection<Particle>[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = new LinkedList<>();
            }
        }
    }

    private static void calculateNeighbors(
            Collection<Particle>[][] matrix,
            boolean isPeriodic,
            int length,
            double radius)
    {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Collection<Collection<Particle>> neighbors = ParticleSystem.getNeighbors(matrix, i, j, isPeriodic);
                ParticleSystem.putNeighbors(matrix[i][j], neighbors, isPeriodic, length, radius);
            }
        }
    }

    private static Collection<Collection<Particle>> getNeighbors(
            Collection<Particle>[][] matrix,
            final int i,
            final int j,
            boolean isPeriodic)
    {
        Collection<Collection<Particle>> neighbors = new LinkedList<>();

        if (isPeriodic) {
            L.POSITIONS.forEach(position -> {
                neighbors.add(matrix
                        [Math.floorMod(i + (int) position.getX(), matrix.length)]
                        [Math.floorMod(j + (int) position.getY(), matrix.length)]
                );
            });
        } else {
            L.POSITIONS.forEach(position -> {
                if (
                        (i + position.getX() >= 0)
                        && (i + position.getX() < matrix.length)
                        && (j + position.getY() >= 0)
                        && (j + position.getY() < matrix.length))
                {
                    neighbors.add(matrix[i + (int) position.getX()][j + (int) position.getY()]);
                }
            });
        }

        return neighbors;
    }

    private static void putNeighbors(
            Collection<Particle> particles,
            Collection<Collection<Particle>> neighborsList,
            boolean isPeriodic,
            int length,
            double radius)
    {
        for (Particle particle : particles) {
            for (Collection<Particle> neighbors : neighborsList) {
                neighbors.forEach(neighbor -> {
                    if (isNeighborInsideRadius(particle, neighbor, isPeriodic, length, radius)) {
                        particle.getNeighbors().add(neighbor);
                        neighbor.getNeighbors().add(particle);
                    }
                });
            }
        }
    }

    private static boolean isNeighborInsideRadius(
            Particle particle,
            Particle neighbor,
            boolean isPeriodic,
            int length,
            double radius)
    {
        double distance = isPeriodic ? particle.periodicDistanceTo(neighbor, length) : particle.distanceTo(neighbor);
        return !(distance > radius);
    }
}
