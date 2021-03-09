package ar.edu.itba.ss.y2021_q1_g123;

import ar.edu.itba.ss.y2021_q1_g123.models.Particle;
import ar.edu.itba.ss.y2021_q1_g123.models.ParticleSystem;
import ar.edu.itba.ss.y2021_q1_g123.parsers.CommandParser;
import ar.edu.itba.ss.y2021_q1_g123.parsers.ParticleParser;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.Collection;

public class CellIndexMethod {
    public static void main(String[] args) throws IOException, ParseException {
        CommandParser.getInstance().parse(args);

        ParticleSystem system = ParticleParser.parseStatic(CommandParser.getInstance().getStaticPath());
        ParticleParser.parseDynamic(CommandParser.getInstance().getDynamicPath(), system, CommandParser.getInstance().getParseVelocity());

        long startTime = System.currentTimeMillis();
        Collection<Particle>[][] test = system.createMatrix(CommandParser.getInstance().getMatrixSize());
        long endTime = System.currentTimeMillis();

        ParticleSystemSerializer.serialize(endTime - startTime, system, CommandParser.getInstance().getOutputPath());

        System.out.println("TEST");
    }
}
