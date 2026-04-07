package com.comunidad.comunidad_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ComunidadBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComunidadBackendApplication.class, args);
	}

}
