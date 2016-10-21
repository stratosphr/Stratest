import algorithms.computers.EUAComputer;
import algorithms.computers.OldEUAComputer;
import algorithms.outputs.JSCATS;
import algorithms.outputs.JSCATSStatisticsReporter;
import algorithms.tools.AbstractStatesComputer;
import eventb.Machine;
import eventb.expressions.bool.Predicate;
import eventb.parsers.EBMParser;
import graphs.AbstractState;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 10/10/16.
 * Time : 06:20
 */
public class Main {

    private static void save(File outputFolder, Machine machine, JSCATS eua0, JSCATS eua1, JSCATSStatisticsReporter eua0Report, JSCATSStatisticsReporter eua1Report) {
        try {
            outputFolder.mkdirs();
            Files.write(Paths.get(outputFolder.getAbsolutePath() + "/" + machine.getName() + "_noHeuristics.txt"), eua0Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(outputFolder.getAbsolutePath() + "/" + machine.getName() + "_noHeuristics.dot"), eua0.getTestsDOTFormatting(eua0Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(outputFolder.getAbsolutePath() + "/" + machine.getName() + "_noHeuristics.table"), eua0Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(outputFolder.getAbsolutePath() + "/" + machine.getName() + "_heuristics.txt"), eua1Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(outputFolder.getAbsolutePath() + "/" + machine.getName() + "_heuristics.dot"), eua1.getTestsDOTFormatting(eua1Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(outputFolder.getAbsolutePath() + "/" + machine.getName() + "_heuristics.table"), eua1Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new Error("\n***********************************************************************\n| Stratest requires three arguments:\n| \t- The event-B program in \".ebm\" format (see examples)\n| \t- The abstraction predicates in \".ap\" format (see examples).\n| \t- The output folder (created if not existing).\n***********************************************************************\n| The command should follow the pattern bellow:\n| $> stratest eventBFile.ebm abstractionPredicatesFile.ap outputFolder\n***********************************************************************\n");
        } else {
            File eventBFile = new File(args[0]);
            File abstractionPredicatesFile = new File(args[1]);
            if (!eventBFile.exists()) {
                throw new Error("\nThe event-b file \"" + eventBFile + "\" does not exist.");
            } else if (!abstractionPredicatesFile.exists()) {
                throw new Error("\nThe abstraction predicates file \"" + abstractionPredicatesFile + "\" does not exist.");
            } else {
                Machine machine = new EBMParser().parseMachine(eventBFile);
                Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(abstractionPredicatesFile));
                List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
                JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
                JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
                JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), new HashSet<>(), -1);
                JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), new HashSet<>(), -1);
                save(new File(args[2]), machine, eua0, eua1, eua0Report, eua1Report);
            }
        }
    }

}
