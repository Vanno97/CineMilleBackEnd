package it.micael.vanini.cinemille;

import it.micael.vanini.cinemille.model.Sala;
import it.micael.vanini.cinemille.model.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class CineMilleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CineMilleApplication.class, args);
	}

	@Component
	static class Start implements ApplicationRunner {
		private static final Logger LOGGER = LoggerFactory.getLogger(CineMilleApplication.class);

		private final SalaRepository salaRepository;

		public Start(SalaRepository salaRepository) {
			this.salaRepository = salaRepository;
		}

		@Override
		public void run(ApplicationArguments args) throws Exception {
			LOGGER.info("Inserting sale if not exists");
			List<Sala> salaList = new ArrayList<>();
			Random random = new Random();
			random.nextInt(50,251);
			for (int i = 1; i <= 12; i++) {
				salaList.add(new Sala(null, "SALA " + ((i<10) ? "00" + i : "0"+i), random.nextInt(50,251), i==3 || i == 8));
			}

			for (Sala sala : salaList) {
				if (!this.salaRepository.existsByNomeSala(sala.getNomeSala())) {
					this.salaRepository.save(sala);
				}
			}
		}
	}
}
