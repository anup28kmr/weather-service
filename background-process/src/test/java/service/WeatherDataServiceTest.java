package service;

import com.ak.bkdprocess.repository.WeatherRepository;
import com.ak.bkdprocess.service.WeatherApiService;
import com.ak.bkdprocess.service.WeatherDataService;
import com.ak.common.entity.WeatherEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WeatherDataServiceTest {
    @Mock
    private WeatherApiService weatherApiService;
    
    @Mock
    private WeatherRepository weatherRepository;
    
    private WeatherDataService weatherDataService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherDataService = new WeatherDataService(weatherApiService);
    }
    
    @Test
    void updateWeatherData_Success() {
        // Sample XML response
        String xmlResponse = """
            <?xml version="1.0" encoding="UTF-8"?>
            <observations>
                <observation>
                    <station>
                        <name>Tallinn</name>
                        <airtemperature>20.5</airtemperature>
                        <phenomenon>Clear</phenomenon>
                        <relativehumidity>65</relativehumidity>
                        <precipitationprobability>10</precipitationprobability>
                        <forecasttime>2024-04-23 12:00:00</forecasttime>
                    </station>
                </observation>
            </observations>
            """;
        
        when(weatherApiService.fetchWeatherData()).thenReturn(Mono.just(xmlResponse));
        when(weatherRepository.save(any(WeatherEntity.class))).thenReturn(new WeatherEntity());
        
        weatherDataService.updateWeatherData();
        
        verify(weatherRepository, atLeastOnce()).save(any(WeatherEntity.class));
    }
}
