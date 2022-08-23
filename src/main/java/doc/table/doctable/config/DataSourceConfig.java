package doc.table.doctable.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
@Slf4j
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource dataSource(){
        log.info("=======basic.datasource=======");
        log.info("DriverClassName   : {}",dataSourceProperties().getDriverClassName());
        log.info("url               : {}",dataSourceProperties().getUrl());
        log.info("username          : {}",dataSourceProperties().getUsername());
        return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "tDataSourceProperties")
    @ConfigurationProperties(prefix = "table-create")
    public DataSourceProperties tDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name="tDataSource")
    public DataSource tDataSource(){

        log.info("DriverClassName   : {}",tDataSourceProperties().getDriverClassName());
        log.info("url               : {}",tDataSourceProperties().getUrl());
        log.info("username          : {}",tDataSourceProperties().getUsername());
        return tDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(tDataSource());
    }
}
