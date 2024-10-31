package com.ak.bkdprocess.service;

import com.ak.common.exceptions.WeatherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class WeatherApiServiceTest {

  private static final String WEATHER_API_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/forecast.php?lang=eng";
  @Mock
  private HttpClient httpClient;
  @Mock
  private HttpResponse<String> httpResponse;
  @InjectMocks
  private WeatherApiService weatherApiService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    // Set the WEATHER_API_URL field in the WeatherApiService instance
    ReflectionTestUtils.setField(weatherApiService, "WEATHER_API_URL", WEATHER_API_URL);
  }

  @Test
  public void testFetchWeatherXMLData_Success() throws Exception {
    // Arrange
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(WEATHER_API_URL))
        .GET()
        .build();

    // Mocking the behavior of the clientWithRetry
    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);
    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn(sampleXMLData());

    // Act
    String result = weatherApiService.fetchWeatherXMLData();

    // Assert
    assertEquals(sampleXMLData(), result);
  }

  @Test
  public void testFetchWeatherXMLData_Failure() throws Exception {
    // Arrange
    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);
    when(httpResponse.statusCode()).thenReturn(500); // Simulate server error

    // Act & Assert
    Exception exception = assertThrows(WeatherException.class, () -> {
      weatherApiService.fetchWeatherXMLData();
    });

    // Assert
    assertEquals("Failed to fetch weather data. Status code: 500", exception.getMessage());
  }

  @Test
  public void testFetchWeatherXMLData_Exception() throws Exception {
    // Arrange
    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenThrow(new RuntimeException("Network error"));

    // Act & Assert
    Exception exception = assertThrows(WeatherException.class, () -> {
      weatherApiService.fetchWeatherXMLData();
    });

    // Assert
    assertTrue(exception.getMessage().contains("Error fetching weather data: Network error"));
  }


  private String sampleXMLData() {
    return """
        <?xml version="1.0" encoding="UTF-8" ?>
        <forecasts>
        	<forecast date="2024-10-31">
        		<night>
        			<phenomenon>Light shower</phenomenon>
        			<tempmin>0</tempmin>
        			<tempmax>9</tempmax>
        			<text>Slightly and partly cloudy. Locally rain showers, possible sleet. West, northwest wind 5-9, in gusts up to 14, on coast 8-15, in gusts up to 20 m/s, by morning decreasing. Air temperature 0..+5, on coast up to 9°C.</text>
        			<place>
        				<name>Harku</name>
        				<phenomenon>Variable clouds</phenomenon>
        				<tempmin>5</tempmin>
        			</place>
        			<place>
        				<name>Jõhvi</name>
        				<phenomenon>Few clouds</phenomenon>
        				<tempmin>1</tempmin>
        			</place>
        			<place>
        				<name>Tartu</name>
        				<phenomenon>Variable clouds</phenomenon>
        				<tempmin>3</tempmin>
        			</place>
        			<place>
        				<name>Pärnu</name>
        				<phenomenon>Variable clouds</phenomenon>
        				<tempmin>5</tempmin>
        			</place>
        			<place>
        				<name>Kuressaare</name>
        				<phenomenon>Variable clouds</phenomenon>
        				<tempmin>7</tempmin>
        			</place>
        			<place>
        				<name>Türi</name>
        				<phenomenon>Light shower</phenomenon>
        				<tempmin>2</tempmin>
        			</place>
        			<wind>
        				<name>Kuusiku</name>
        				<direction>Northwest wind</direction>
        				<speedmin>5</speedmin>
        				<speedmax>9</speedmax>
        				<gust>13</gust>
        			</wind>
        			<wind>
        				<name>Väike-Maarja</name>
        				<direction>Northwest wind</direction>
        				<speedmin>5</speedmin>
        				<speedmax>9</speedmax>
        				<gust>12</gust>
        			</wind>
        			<wind>
        				<name>Võrtsjärv</name>
        				<direction>West wind</direction>
        				<speedmin>5</speedmin>
        				<speedmax>9</speedmax>
        				<gust>13</gust>
        			</wind>
        			<sea>Oct 31, 2024 3:55 UTC
                    
        Forecast for Baltic Sea valid 24 hours from 07:00 UTC Oct 31 to 07:00 UTC Nov 1, 2024.
                    
        Warnings:\s
        Gulf of Finland: northwest in gusts to 16 m/s, by day turning to west, southwest. Wave height 1-2 m.
                    
        Weather summary: in forenoon a Ridge of High over the Baltic Sea, in afternoon a new Trough of Low is spreading to the sea from Scandinavia. Next night active Low is moving from Bay of Bothnia to Southern Finland.  \s
                    
        Northern Baltic: in forenoon westerly 5-10, in gusts up to 13 m/s, in afternoon from western waters southwest, in evening also west increasing 10-16, in gusts up to 22 m/s, in morning west, northwest 14-18, in gusts to 25 m/s. Wave height 1.5-3.5 m. In morning locally rain showers, by day dense rain arrives and moving to east, next night showers in many places. Visibility moderate.
                    
        Western Gulf of Finland: northwest, west, in afternoon west, southwest 6-11, in gusts up to 16 m/s, in late evening southwest, west increasing 10-16, in gusts up to 21 m/s. Wave height 1-2 m, at night 1.5-3 m. Locally rain showers, dense rain from afternoon. Visibility moderate.
                    
        Eastern Gulf of Finland: northwest, west 6-11, in gusts up to 16 m/s, in evening backing southwest, south, after midnight to west, northwest. Wave height 1-2 m. Locally rain showers, possible sleet, in evening dense rain. Visibility moderate.
                    
        Moonsund, Northern Gulf of Riga: in forenoon west, northwest 4-9, in gusts to 12 m/s, around noon increasing west, southwest 6-11, in gusts up to 16 m/s, in evening increasing 10-15, in gusts to 21, in morning in Gulf of Riga 14-18, in gusts up to 25 m/s. Wave height 1-2 m, in Moonsund 0.5-1 m, at night 1.5-3 m, in Moonsund 0.7-1.5 m. Locally rain showers, dense rain from afternoon. Visibility moderate.
        </sea>
        			<peipsi>West, northwest 5-9, in gusts up to 14 m/s. Wave height 0.8-1.5 m. Mostly dry. Visibility good. Air temperature 3..5°C.</peipsi>
        		</night>
        		<day>
        			<phenomenon>Moderate shower</phenomenon>
        			<tempmin>6</tempmin>
        			<tempmax>11</tempmax>
        			<text>From west the cloud cover is thickening. Before noon in some places rain showers, in northeastern part also sleet possible. In the afternoon thicker area of rain reaching islands and spreading over the mainland eastwards. In inland westerly wind 4-9, in gusts 12 m/s, in the evening backing to south, on coast in the forenoon west, northwest, in the afternoon west, southwest wind 6-11, in gusts up to 16 m/s, in the evening 8-15, in gusts up to 21 m/s. Air temperature 6..11°C.</text>
        			<place>
        				<name>Harku</name>
        				<phenomenon>Moderate rain</phenomenon>
        				<tempmax>9</tempmax>
        			</place>
        			<place>
        				<name>Jõhvi</name>
        				<phenomenon>Moderate shower</phenomenon>
        				<tempmax>6</tempmax>
        			</place>
        			<place>
        				<name>Tartu</name>
        				<phenomenon>Light shower</phenomenon>
        				<tempmax>7</tempmax>
        			</place>
        			<place>
        				<name>Pärnu</name>
        				<phenomenon>Moderate rain</phenomenon>
        				<tempmax>9</tempmax>
        			</place>
        			<place>
        				<name>Kuressaare</name>
        				<phenomenon>Moderate rain</phenomenon>
        				<tempmax>11</tempmax>
        			</place>
        			<place>
        				<name>Türi</name>
        				<phenomenon>Light rain</phenomenon>
        				<tempmax>7</tempmax>
        			</place>
        			<wind>
        				<name>Kuusiku</name>
        				<direction>Southwest wind</direction>
        				<speedmin>4</speedmin>
        				<speedmax>9</speedmax>
        				<gust>12</gust>
        			</wind>
        			<wind>
        				<name>Väike-Maarja</name>
        				<direction>West wind</direction>
        				<speedmin>4</speedmin>
        				<speedmax>8</speedmax>
        				<gust>11</gust>
        			</wind>
        			<wind>
        				<name>Võrtsjärv</name>
        				<direction>West wind</direction>
        				<speedmin>4</speedmin>
        				<speedmax>9</speedmax>
        				<gust>12</gust>
        			</wind>
        			<sea></sea>
        			<peipsi>In morning northwest, west, by day west, southwest wind 4-9, in gusts to 12 m/s, in the evening backing to south. Wave height 0.6-1.2 m. Locally rain showers, around evening an area of dense rain arrives. Visibility from good to moderate. Air temperature 6..7°C.</peipsi>
        		</day>
        	</forecast>
        	<forecast date="2024-11-01">
        		<night>
        			<phenomenon>Moderate rain</phenomenon>
        			<tempmin>4</tempmin>
        			<tempmax>11</tempmax>
        			<text>Overcast and rainy. Before midnight south, southwest wind 5-12, on coast in gusts 18, after midnight southwest, west wind increasing 7-12, in Southern Estonia in gusts 17, on islands and coast 12-17, in gusts 22, in the morning in area Gulf of Riga up to 25 m/s. Air temperature 4..9, on coast to 11°C.</text>
        		</night>
        		<day>
        			<phenomenon>Moderate rain</phenomenon>
        			<tempmin>4</tempmin>
        			<tempmax>13</tempmax>
        			<text>Mainly overcast. Rain at times, in the evening locally sleet too. West, northwest wind 7-15, in Southern Estonia in gusts up to 20, on islands and coast up to 25 m/s. At noon from north northwest, north wind slowly decreasing, but in the evening on islands increasing again northwest wind in gusts 23 m/s. Air temperature 4..10, before noon locally to 13°C.</text>
        		</day>
        	</forecast>
        	<forecast date="2024-11-02">
        		<night>
        			<phenomenon>Moderate sleet</phenomenon>
        			<tempmin>-2</tempmin>
        			<tempmax>6</tempmax>
        			<text>Cloudy with clear spells. Rain and sleet, in the northeastern regions snow too. In mainland northerly wind 3-9, in the morning on northern coast in gusts 14, on Western Estonian islands and coast northwest wind 12-18, in gusts 23 m/s. Air temperature -2..+2, on coast up to +6°C. Risk of slippery roads!</text>
        		</night>
        		<day>
        			<phenomenon>Moderate sleet</phenomenon>
        			<tempmin>0</tempmin>
        			<tempmax>6</tempmax>
        			<text>Variable cloud cover. At times sleet, also rain, in eastern part snow too. North, northwest wind 6-11, in gusts 15, on islands and coast 12-17, in gusts up to 23 m/s. Air temperature 0..+6°C. Risk of slippery roads!</text>
        		</day>
        	</forecast>
        	<forecast date="2024-11-03">
        		<night>
        			<phenomenon>Light sleet</phenomenon>
        			<tempmin>-2</tempmin>
        			<tempmax>7</tempmax>
        			<text>Slightly and partly cloudy. Locally rain and sleet, in northeastern part sleet or snow. Northwest, west wind 5-12, on coast 10-15, in gusts 20 m/s, by morning somewhat decreasing. Air temperature -2..+2, on coast up to +7°C. Risk of slippery roads!</text>
        		</night>
        		<day>
        			<phenomenon>Light rain</phenomenon>
        			<tempmin>3</tempmin>
        			<tempmax>11</tempmax>
        			<text>Overcast, in the evening in Western Estonia clear spells. Rain and sleet in may places. West, southwest wind 6-12, on coast in gusts 15, in the afternoon in inland in gusts 16, on coast 12-17, in gusts 22 m/s. Air temperature 3..9, on coast up to 11°C.</text>
        		</day>
        	</forecast>
        </forecasts>""";
  }
}
