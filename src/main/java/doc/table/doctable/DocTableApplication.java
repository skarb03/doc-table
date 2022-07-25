package doc.table.doctable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class DocTableApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocTableApplication.class, args);
    }

}
