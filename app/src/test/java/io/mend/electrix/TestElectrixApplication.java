package io.mend.electrix;

import org.springframework.boot.SpringApplication;

public class TestElectrixApplication {

	static void main(String[] args) {
		SpringApplication.from(ElectrixApplication::main)
						 .with(TestcontainersConfiguration.class)
						 .run(args);
	}

}
