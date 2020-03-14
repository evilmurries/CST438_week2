package cst438hw2.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
	private WeatherService weatherService;
	
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
