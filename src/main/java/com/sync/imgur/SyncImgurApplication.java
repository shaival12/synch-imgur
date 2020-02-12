package com.sync.imgur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sync.imgur.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SyncImgurApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncImgurApplication.class, args);
	}

}
