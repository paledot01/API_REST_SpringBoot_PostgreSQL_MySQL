package com.cibertec.shoesformen_api;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder; // <------------------------
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "psqlEntityManagerFactory", // <----
        transactionManagerRef = "psqlTransactionManager", // <----
        basePackages = { "com.cibertec.shoesformen_api.repository" } // <--- REPOSITORIO paquete que necesitara de esta BD
)
public class PostgreSQLConfig {

        // 1. Los 4 metodos se estan utilizando entre SI, verifica bien los nombres
        @Bean // --> ENTIDADES
        @Primary // solo al principal
        public LocalContainerEntityManagerFactoryBean psqlEntityManagerFactory(
                @Qualifier("psqlDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) { // verifica el import debe ser ORM
                return builder.dataSource(psqlDataSource()).packages("com.cibertec.shoesformen_api.model").build();
        }

        @Bean // --> TRANSACCIONES
        @Primary
        public PlatformTransactionManager psqlTransactionManager(
                @Qualifier("psqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean psqlEntityManagerFactory) { // < ---
                return new JpaTransactionManager(Objects.requireNonNull(psqlEntityManagerFactory.getObject())); // <---
        }

        @Bean // --> BASE DE DATOS
        @ConfigurationProperties("psql")
        public DataSourceProperties psqlDataSourceProperties() {
                return new DataSourceProperties();
        }
        @Bean
        @Primary
        @ConfigurationProperties("psql.hikari")
        public DataSource psqlDataSource() {
                return psqlDataSourceProperties()
                        .initializeDataSourceBuilder()
                        .build();
        }

}
