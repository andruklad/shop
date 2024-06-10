package com.colvir.shop.config;

import com.colvir.shop.repository.CatalogRepository;
import com.colvir.shop.repository.impl.CatalogRepositoryMemoryImpl;
import com.colvir.shop.repository.impl.CatalogRepositoryPostgresImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private final String repositoryMode;

    public Config(
            @Value("${repository.mode}") String repositoryMode) {
        this.repositoryMode = repositoryMode;
    }

    @Bean
    public CatalogRepository getCatalogRepository () {
        return switch (repositoryMode) {
            case "memory" -> new CatalogRepositoryMemoryImpl();
            case "postgres" -> new CatalogRepositoryPostgresImpl();
            default ->
                    throw new RuntimeException(String.format("Режим репозитория %s не поддерживается.", repositoryMode));
        };
    }
}
