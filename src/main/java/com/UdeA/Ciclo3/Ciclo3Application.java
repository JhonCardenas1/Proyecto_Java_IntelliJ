package com.UdeA.Ciclo3;

import com.UdeA.Ciclo3.modelos.Empresa;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
public class Ciclo3Application {

	/*@GetMapping("/hello")
	public String hello(){
		return "Hola saldremos vivos de esto";
	}*/
	@GetMapping("/test")
	public String test(){
		Empresa emp = new Empresa( "Solas SAS", "Calle 42", "12344", "1234567");

		emp.setNombre("Solar LTDA");
		//System.out.println("Hasta aqui vamos bien");
		return emp.getNombre();
	}

	public static void main(String[] args) {
		SpringApplication.run(Ciclo3Application.class, args);


	}


}
