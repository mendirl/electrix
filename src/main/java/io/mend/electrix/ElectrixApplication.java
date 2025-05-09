package io.mend.electrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@Modulithic
@SpringBootApplication
public class ElectrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectrixApplication.class, args);
//        var modules = ApplicationModules.of(Application.class);


	}

}
