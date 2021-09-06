package com.asatisamaj.matrimony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@EnableEncryptableProperties
@SpringBootApplication
public class MatrimonyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatrimonyApplication.class, args);
	}
}
