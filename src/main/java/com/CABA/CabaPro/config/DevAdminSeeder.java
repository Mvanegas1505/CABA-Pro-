package com.CABA.CabaPro.config;

import com.CABA.CabaPro.model.RolEnum;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Seeds a default admin account in dev profile if none exists.
 * Email: admin@caba.com, Password: Admin#123
 */
@Configuration
@Profile("dev")
public class DevAdminSeeder {

    @Bean
    public CommandLineRunner seedAdmin(UsuarioService usuarioService, PasswordEncoder encoder) {
        return args -> {
            final String email = "admin@caba.com";
            if (usuarioService.findByCorreo(email) == null) {
                Usuario u = new Usuario();
                u.setCorreo(email);
                u.setNombre("Admin");
                u.setRol(RolEnum.ADMIN);
                u.setContrasena(encoder.encode("Admin#123"));
                usuarioService.guardar(u);
                System.out.println("[DevAdminSeeder] Admin creado: " + email + " / Admin#123");
            }
        };
    }
}
