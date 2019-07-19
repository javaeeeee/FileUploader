package com.javaeeeee.filemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FilemanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilemanagerApplication.class, args);
    }

}
