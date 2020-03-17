package cst438hw2.service;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.anyString;

import cst438hw2.domain.*;
 
@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;
	
	@Test
	public void testCityFound() throws Exception {
	   City testCity = new City((long)1, "testCity", "testCountry", "testDistrict", 5);
		//mock repository and weather service
		((BDDMockito) ((CityRepository) given(cityRepository)).findByName("testCity")).willReturn(testCity);
		given(cityService.getCityInfo("testCity")).willReturn(new CityInfo(testCity, "testCountry", 75.00, "1:15pm"));

		CityInfo actual = cityService.getCityInfo("testCity");
		
		// Assertions
		assertThat( actual.getName()).isEqualTo("testCity1");
		assertThat( actual.getCountryCode()).isEqualTo("testCountry");
		assertThat( actual.getId()).isEqualTo((long)1);
		assertThat( actual.getDistrict()).isEqualTo("testDistrict");
		assertThat( actual.getPopulation()).isEqualTo(5);
		assertThat( actual.getTemp()).isEqualTo(75.00);
		assertThat ( actual.getTime()).isEqualTo("1:15pm");
		
	}
	
	@Test 
	public void  testCityNotFound() {
      City testCity = new City((long)1, "testCity1", "testCountry", "testDistrict", 5);
      //mock repository and weather service
      ((BDDMockito) ((CityRepository) given(cityRepository)).findByName("testCity")).willReturn(testCity);
      given(cityService.getCityInfo("testCity")).willReturn(new CityInfo(testCity, "testCountry", 75.00, "1:15pm"));

      CityInfo actual = cityService.getCityInfo("testCity2");
      
      // Assertions
      assertThat( actual.getName()).isEqualTo("testCity1");
      assertThat( actual.getCountryCode()).isEqualTo("testCountry");
      assertThat( actual.getId()).isEqualTo((long)1);
      assertThat( actual.getDistrict()).isEqualTo("testDistrict");
      assertThat( actual.getPopulation()).isEqualTo(5);
      assertThat( actual.getTemp()).isEqualTo(75.00);
      assertThat ( actual.getTime()).isEqualTo("1:15pm");
	}
	
	@Test 
	public void  testCityMultiple() {
      City testCity = new City((long)1, "Los Angeles", "testCountry", "testDistrict", 5);
      //mock repository and weather service
      ((BDDMockito) ((CityRepository) given(cityRepository)).findByName("testCity")).willReturn(testCity);
      given(cityService.getCityInfo("testCity")).willReturn(new CityInfo(testCity, "testCountry", 75.00, "1:15pm"));

      CityInfo actual = cityService.getCityInfo("testCity2");
      
      // Assertions
      assertThat( actual.getName()).isEqualTo("Los Angeles");
      assertThat( actual.getCountryCode()).isEqualTo("testCountry");
      assertThat( actual.getId()).isEqualTo((long)1);
      assertThat( actual.getDistrict()).isEqualTo("testDistrict");
      assertThat( actual.getPopulation()).isEqualTo(5);
      assertThat( actual.getTemp()).isEqualTo(75.00);
      assertThat ( actual.getTime()).isEqualTo("1:15pm");

	}
}
