package org.tbk.sbswdemo.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.tbk.sbswdemo.model.ModelRepository;
import org.tbk.sbswdemo.model.SeriesRepository;
import org.tbk.sbswdemo.model.OemRepository;
import org.tbk.sbswdemo.model.UserRepository;

@Configuration
public class DemoConfig {

    @Bean
    public DemoService demoService(PasswordEncoder passwordEncoder,
                                   UserRepository userRepository,
                                   OemRepository oemRepository,
                                   SeriesRepository listRepository,
                                   ModelRepository itemRepository) {
        return new DemoService(passwordEncoder,
                userRepository,
                oemRepository,
                listRepository,
                itemRepository);
    }

    @Bean
    public CommandLineRunner demo(DemoService demoService) {
        return new CommandLineRunner() {

            @Override
            @Transactional
            public void run(String... args) throws Exception {
                demoService.printGoalsOfDemoUser();
                demoService.createDemoData();
            }
        };
    }
}
