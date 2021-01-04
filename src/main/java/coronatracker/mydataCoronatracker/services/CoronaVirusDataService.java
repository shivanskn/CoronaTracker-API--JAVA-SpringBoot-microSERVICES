package coronatracker.mydataCoronatracker.services;

import java.io.FileReader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import coronatracker.mydataCoronatracker.models.LocationStats;


@Service
public class CoronaVirusDataService {
	
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	
	private List<LocationStats> allStats = new ArrayList<>();
	
//	@PostConstruct
//	@Scheduled(cron = " * * 1 * * *")
//	public void fetchVirusData() throws IOException, InterruptedException {
	public String fetchVirusData(String cntry) throws IOException, InterruptedException {
		
		List<LocationStats> newStats = new ArrayList<>();
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(VIRUS_DATA_URL))
				.build();
		
		HttpResponse<String> httpResponse =	client.send(request, HttpResponse.BodyHandlers.ofString());
//		System.out.println(httpResponse.body());
		
		StringReader csvBodyReader = new StringReader(httpResponse.body());		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		for (CSVRecord record : records) {
			
			String province = record.get("Province/State");
		    String country = record.get("Country/Region");
		    if (cntry.equals(country)) {
		    LocationStats locationstats = new LocationStats();
		    locationstats.setState(province);		    
		    locationstats.setCountry(country);
		    locationstats.setLatestTotalCases(Integer.parseInt(record.get(record.size() -1)));
//		    System.out.println("locationstats:" +locationstats);
		    newStats.add(locationstats);
		    }
		}
		this.allStats = newStats;
//		System.out.println("allstats: 1" + allStats);
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.writeValue(System.out, allStats);	    
//	    System.out.println("allstats: " + allStats);
//	    List<Person> ppl2 = Arrays.asList(mapper.readValue(json, Person[].class));
//	    System.out.println("Mapper: " +mapper);
	    String jsonInString = mapper.writeValueAsString(allStats);
	    System.out.println(jsonInString);
//	    System.out.println("String JSON: " +jsonInString);
//	    List<LocationStats> locStatcntl = Arrays.asList(mapper.readValue(jsonInString, LocationStats[].class));
//	    System.out.println("JSON Output:" +locStatcntl);
	    return jsonInString;
	}
	
}
