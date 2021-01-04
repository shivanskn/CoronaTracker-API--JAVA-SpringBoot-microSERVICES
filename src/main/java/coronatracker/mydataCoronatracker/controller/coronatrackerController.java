package coronatracker.mydataCoronatracker.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import coronatracker.mydataCoronatracker.models.LocationStats;
import coronatracker.mydataCoronatracker.services.CoronaVirusDataService;

@RestController
public class coronatrackerController {

	@Autowired
	private CoronaVirusDataService coronavirusdataservice;

	@RequestMapping("/shiv")
	String test() {
		return "Hello shiv!";
	}

	@RequestMapping("status/{country}")
	public String getcoronaStatus(@PathVariable String country) throws IOException, InterruptedException {
		
//		System.out.println("Here:" +this.coronavirusdataservice.fetchVirusData());
		return this.coronavirusdataservice.fetchVirusData(country);	
	}
	
}
