package com.ak.bkdprocess.service;

import com.ak.bkdprocess.repository.ForecastRepository;
import com.ak.common.entity.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherDataServiceTest {

  private static final String VALID_XML_RESPONSE = """
      <forecasts>
      <forecast date="2024-10-31">
      <night>
      ...
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
      <sea/>
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
      </forecasts>"""; // Simplified XML
  @Mock
  private ForecastRepository forecastRepository;
  @Mock
  private WeatherApiService weatherApiService;
  @InjectMocks
  private WeatherDataService weatherDataService;

  @BeforeEach
  void setUp() {
    // Set up any common configuration for each test
  }

  @Test
  void testUpdateWeatherData_CallsProcessWeatherDataAndSavesForecasts() {
    when(weatherApiService.fetchWeatherXMLData()).thenReturn(VALID_XML_RESPONSE);

    weatherDataService.updateWeatherData();

    verify(weatherApiService, times(1)).fetchWeatherXMLData();
    verify(forecastRepository, atLeastOnce()).saveAll(any(List.class));
  }

  @Test
  void testProcessWeatherData_InvalidXmlLogsError() {
    String invalidXml = "<Invalid></XML>";

    weatherDataService.processWeatherData(invalidXml);

    verify(forecastRepository, never()).saveAll(any(List.class));
  }

  @Test
  void testSetPlaces_NullPlaces_ReturnsEmptyList() {
    List<Place> result = weatherDataService.setPlaces(null);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}
