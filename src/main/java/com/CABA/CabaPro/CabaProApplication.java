package com.CABA.CabaPro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.CABA.CabaPro.model.RolEnum;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.repository.UsuarioRepository;

@SpringBootApplication
public class CabaProApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabaProApplication.class, args);
	}

	@Bean
	public CommandLineRunner createDefaultAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			String adminEmail = "admin@gmail.com";
			String adminPass = "Marcelo1*";
			if (usuarioRepository.findByCorreo(adminEmail).isEmpty()) {
				Usuario admin = new Usuario();
				admin.setCorreo(adminEmail);
				admin.setNombre("Administrador");
				admin.setContrasena(passwordEncoder.encode(adminPass));
				admin.setRol(RolEnum.ADMIN);
				usuarioRepository.save(admin);
				System.out.println("Admin user created: " + adminEmail);
			} else {
				System.out.println("Admin user already exists: " + adminEmail);
			}
		};
	}

	// Seed some sample arbitros if none exist (non-destructive)
	// This seeder should only run in 'dev' profile so it doesn't run in production
	@Bean
	@org.springframework.context.annotation.Profile("dev")
	public CommandLineRunner seedSampleArbitros(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			java.util.List<com.CABA.CabaPro.model.Usuario> existentes = usuarioRepository.findByRol(com.CABA.CabaPro.model.RolEnum.ARBITRO);
			if (existentes == null || existentes.isEmpty()) {
				System.out.println("No arbitros found — creating sample arbitros for testing");
				com.CABA.CabaPro.model.Usuario a1 = new com.CABA.CabaPro.model.Usuario();
				a1.setCorreo("arbitro1@example.com");
				a1.setNombre("Arbitro Uno");
				a1.setContrasena(passwordEncoder.encode("Arbitro1!"));
				a1.setRol(com.CABA.CabaPro.model.RolEnum.ARBITRO);
				a1.setEspecialidad(com.CABA.CabaPro.model.EspecialidadEnum.PRINCIPAL.name());
				usuarioRepository.save(a1);

				com.CABA.CabaPro.model.Usuario a2 = new com.CABA.CabaPro.model.Usuario();
				a2.setCorreo("arbitro2@example.com");
				a2.setNombre("Arbitro Dos");
				a2.setContrasena(passwordEncoder.encode("Arbitro2!"));
				a2.setRol(com.CABA.CabaPro.model.RolEnum.ARBITRO);
				a2.setEspecialidad(com.CABA.CabaPro.model.EspecialidadEnum.ASISTENTE.name());
				usuarioRepository.save(a2);

				System.out.println("Sample arbitros created: arbitro1@example.com, arbitro2@example.com");
			} else {
				System.out.println("Arbitros already exist, skipping sample seeder");
			}
		};
	}

}
