package com.exercise.MVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MvcApplication {

	public interface SaySomethingService{
		public String saySomething();
	}

	@Component
	@Qualifier("Nudel")
	public class SayHelloService implements SaySomethingService{

		@Override
		public String saySomething() {
			return "Huhu Du Schwanz!!";
		}
	}

	@Configuration
	public class SayConfig{
		@Bean
		@Primary
		public SaYConfigService saYConfigService (){
			SaYConfigService saYConfigService1 = new SaYConfigService();
			saYConfigService1.setSay("Goodbye Dick!!");
			return saYConfigService1;
		}

	}

	public class SaYConfigService implements SaySomethingService{

		private String say = "";

		public String getSay() {
			return say;
		}

		public void setSay(String say) {
			this.say = say;
		}

		@Override
		public String saySomething() {
			return say;
		}
	}

	@RestController
	public class CityController{
		CityRepository cityRepository;

		public CityController(CityRepository cityRepository) {
			this.cityRepository = cityRepository;
		}

		@GetMapping("/berlin")
		public City berlin(){
			return cityRepository.findByName("Berlin");
		}
	}

	@RestController
	public class SayController{
		@Autowired
		@Qualifier("Nudel")
		SaySomethingService saySomethingService;

		@GetMapping("/")
		public String home() {
			return saySomethingService.saySomething();
		}
	}

	public static void main(String[] args) {
	ConfigurableApplicationContext applicationContext = SpringApplication.run(MvcApplication.class, args);
		/* SaySomethingService saySomethingService = applicationContext.getBean(SaySomethingService.class);
		System.out.println(saySomethingService.saySomething());

		City berlin = new City();
		berlin.setName("Berlin");
		berlin.setCapitol(true);

		City duesseldorf = new City();
		duesseldorf.setName("Düsseldorf");
		duesseldorf.setCapitol(false);

		CityRepository cityRepository = applicationContext.getBean(CityRepository.class);
		cityRepository.save(berlin);
		cityRepository.save(duesseldorf);
		Set<City> cities = new HashSet<>();
		cityRepository.findAll().iterator().forEachRemaining(cities::add);
		for (City city : cities){
			System.out.println(city.getName());
			System.out.println(city.isCapitol());
			System.out.println();
			System.out.println();
		}
		 */
	}

	@Bean
	public CorsFilter corsFilter(){
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedOrigins(Arrays.asList("Origin", "Access-Control-Allow-Origin","Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
				"Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin","Content-Type","Accept","Authorization",
				"Access-Control-Allow-Origin","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}



}
