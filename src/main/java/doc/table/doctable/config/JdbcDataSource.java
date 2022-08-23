package doc.table.doctable.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
//@EnableJpaRepositories(
//        basePackages = {
//                "doc.table.doctable.repository"
//                ,"doc.table.doctable.entity"
//        }
//)

public class JdbcDataSource {

    @Bean(name = "tDataSourceProperties")
    @ConfigurationProperties(prefix = "table-create")
    public DataSourceProperties tDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name="tDataSource")
//    public DataSource tDataSource(@Qualifier("tDataSourceProperties") DataSourceProperties dataSourceProperties){
    public DataSource tDataSource(){

        log.info("DriverClassName   : {}",tDataSourceProperties().getDriverClassName());
        log.info("url               : {}",tDataSourceProperties().getUrl());
        log.info("username          : {}",tDataSourceProperties().getUsername());
//        Map<String,String> map = new HashMap<>();
//        map.put("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");
//        dataSourceProperties.getXa().setProperties(map);
//        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        return tDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(tDataSource());
    }
}
