package com.vicayala.demotravel;

import com.vicayala.demotravel.domain.repositories.mongo.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class Application implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AppUserRepository appUserRepository;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* Used for get encoder pass - It is not necessary in implementation environment - only academic */
			this.appUserRepository.findAll()
					.forEach(user->log.info("client {} password : {}",
							user.getUsername(), this.bCryptPasswordEncoder.encode(user.getPassword())));
	}
}
