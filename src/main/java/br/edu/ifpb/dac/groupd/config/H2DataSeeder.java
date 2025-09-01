package br.edu.ifpb.dac.groupd.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.edu.ifpb.dac.groupd.business.exception.UserEmailInUseException;
import br.edu.ifpb.dac.groupd.business.service.RoleService;
import br.edu.ifpb.dac.groupd.business.service.UserServiceImpl;
import br.edu.ifpb.dac.groupd.model.entities.Role;
import br.edu.ifpb.dac.groupd.model.entities.User;
import br.edu.ifpb.dac.groupd.model.repository.RoleRepository;

@Configuration
@Profile("h2")
public class H2DataSeeder {
    private static final Logger log = LoggerFactory.getLogger(H2DataSeeder.class);

    @Bean
    CommandLineRunner seedH2(RoleRepository roleRepo, RoleService roleService, UserServiceImpl userService) {
        return args -> {
            // Garantir roles padrão com nomes compatíveis com o código ("USER" e "ADMIN")
            if (!roleRepo.findByName("USER").isPresent()) {
                roleRepo.save(new Role("USER"));
                log.info("Seed: role USER criada");
            }
            if (!roleRepo.findByName("ADMIN").isPresent()) {
                roleRepo.save(new Role("ADMIN"));
                log.info("Seed: role ADMIN criada");
            }

            // Criar usuário padrão se não existir
            final String email = "admin@ifence.local";
            try {
                if (userService.getAll().isEmpty()) {
                    User u = new User();
                    u.setName("Administrador");
                    u.setEmail(email);
                    u.setPassword("123456"); // será criptografada pelo service
                    userService.save(u); // adiciona role default (USER)
                    log.info("Seed: usuário padrão criado ({} / 123456)", email);
                }
            } catch (UserEmailInUseException e) {
                log.info("Seed: usuário padrão já existe: {}", email);
            } catch (Exception e) {
                log.warn("Seed: falha ao criar usuário padrão: {}", e.getMessage());
            }
        };
    }
}
