package dev.example.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "dev.example.db.domain")
@EnableJpaRepositories(basePackages = "dev.example.db.repository")
public class JpaConfig {
}
