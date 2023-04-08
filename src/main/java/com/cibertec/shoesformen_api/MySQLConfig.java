package com.cibertec.shoesformen_api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "msqlEntityManagerFactory", // <----
        transactionManagerRef = "msqlTransactionManager", // <----
        basePackages = { "com.cibertec.shoesformen_api.a_empresa" } // <-- REPOSITORIO Y MODEL EN MI CASO ESTAN EL MISMO PAQUETE
)
public class MySQLConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean msqlEntityManagerFactory(
            @Qualifier("msqlDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) { // verifica el import debe ser ORM
        return builder.dataSource(msqlDataSource()).packages("com.cibertec.shoesformen_api.a_empresa").build();
    }

    @Bean
    public PlatformTransactionManager msqlTransactionManager(
            @Qualifier("msqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean msqlEntityManagerFactory) { // < ---
        return new JpaTransactionManager(Objects.requireNonNull(msqlEntityManagerFactory.getObject())); // <---
    }

    @Bean
    @ConfigurationProperties("msql")
    public DataSourceProperties msqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean // @Primary solo se añade a la base de datos principal
    public DataSource msqlDataSource() {
        return msqlDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

}
