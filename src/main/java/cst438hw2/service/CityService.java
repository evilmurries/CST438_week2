package cst438hw2.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import cst438hw2.domain.*;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private FanoutExchange fanout;
	
	@Autowired
	private WeatherService weatherService;
	
	  @Configuration
	   public class ConfigPublisher {
	      @Bean
	      public FanoutExchange fanout() {
	         return new FanoutExchange("city-reservation"); 
	      }
	   }
	
	public void requestReservation (
	      String cityName,
	      String level,
	      String email) {
	   
	     String msg  = "{\"cityName\": \""+ cityName + 
              "\" \"level\": \""+level+
              "\" \"email\": \""+email+"\"}" ;
     System.out.println("Sending message:"+msg);
     rabbitTemplate.convertSendAndReceive(
               fanout.getName(), 
               "",
               msg);
	   
	}
	
	public CityInfo getCityInfo(String cityName) {
		List<City> c = cityRepository.findByName(cityName);
		if (c != null) {
		   City city = c.get(0);
		   TempAndTime cityTemp = weatherService.getTempAndTime(city.getName());
		   Double tempF = (cityTemp.getTemp() - 273.15) * (9.0/5.0) + 32;
		   CityInfo newCityInfo = new CityInfo(city,
		         city.getCountryCode(), 
		         tempF, 
		         Long.toString(cityTemp.getTime()));
		   return newCityInfo;
		   
		} else {
		   return null;
		}    
	}
}
	
