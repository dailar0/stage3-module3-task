package com.mjc.school;

import com.mjc.school.util.Communicator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Stage3Module2TaskApplication implements CommandLineRunner {

	private final Communicator communicator;

	public Stage3Module2TaskApplication(Communicator communicator) {
		this.communicator = communicator;
	}

	public static void main(String[] args) {
		SpringApplication.run(Stage3Module2TaskApplication.class, args);
	}

	@Override
	public void run(String... args) {
		communicator.process();
	}
}
