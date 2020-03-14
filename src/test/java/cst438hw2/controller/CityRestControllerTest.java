package cst438hw2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import cst438hw2.domain.*;
import cst438hw2.service.CityService;
import cst438hw2.service.WeatherService;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

	@MockBean
	private CityService cityService;
	
	@MockBean
	private WeatherService weatherService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<CityInfo> json;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void getCityInfo() throws Exception {
	   
	   CityInfo attempt = new CityInfo(1, "test", "testCountry", "TestC", "testDistrict", 5, 75.00, "5");
	   CityInfo expected = new CityInfo(1, "test", "testCountry", "TestC", "testDistrict", 5, 75.00, "5");
	   
	   given(cityService.getCityInfo(attempt.getName())).willReturn(expected);
	   
	   // when
	   MockHttpServletResponse response = mvc.perform(
	         post("api/cities/{city}").contentType(MediaType.APPLICATION_JSON)
	            .content(json.write(attempt).getJson()))
	         .andReturn().getResponse();
	   
	   // then
	   assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
      assertThat(response.getContentAsString()).isEqualTo(
            json.write(expected).getJson());

	}
}
