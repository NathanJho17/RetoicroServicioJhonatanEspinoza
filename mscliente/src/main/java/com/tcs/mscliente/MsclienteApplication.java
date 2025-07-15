package com.tcs.mscliente;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MsclienteApplication {

	 @Autowired
    private Environment env;
	public static void main(String[] args) {
		SpringApplication.run(MsclienteApplication.class, args);
	}
	 @PostConstruct
    public void printActiveProfiles() {
        System.out.println("🔍 PERFIL ACTIVO: " + Arrays.toString(env.getActiveProfiles()));
    }

}
