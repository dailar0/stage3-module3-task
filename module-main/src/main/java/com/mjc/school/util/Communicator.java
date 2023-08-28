package com.mjc.school.util;

import com.mjc.school.hadler.EntityProcessorFactory;
import com.mjc.school.hadler.EntityProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Communicator {

    private final EntityProcessorFactory factory;

    public Communicator(EntityProcessorFactory factory) {
        this.factory = factory;
    }

    public void process() {
        while (true) {
            factory.getProcessorsMap().forEach((integer, entityProcessor) -> System.out.println(integer + "." + entityProcessor));
            System.out.printf("pick 1..%d or 0 for exit%n", factory.getProcessorsMap().size());
            Scanner scanner = new Scanner(System.in);
            int commandNumber = scanner.nextInt();
            scanner.nextLine();
            if (commandNumber == 0) break;
            EntityProcessor strategy = factory.getProcessor(commandNumber);
            strategy.process();
        }
    }
}
