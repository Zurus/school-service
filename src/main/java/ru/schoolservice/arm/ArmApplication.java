package ru.schoolservice.arm;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class ArmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArmApplication.class, args);
	}
}
