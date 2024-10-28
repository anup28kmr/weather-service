package com.ak.bkdprocess.service;

import com.ak.common.dto.Forecast;
import com.ak.common.dto.Forecasts;
import com.ak.common.exceptions.WeatherException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.xml.sax.InputSource;
import reactor.core.publisher.Mono;

@Service
public class WeatherDataService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherDataService.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final WeatherApiService weatherApiService;
//    private final WeatherRepository weatherRepository;

    public WeatherDataService(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
//        this.weatherRepository = weatherRepository;
    }

    @Scheduled(fixedRate = 60 * 1000) // 30 minutes
    public void updateWeatherData() throws Exception {
        logger.info("Starting scheduled weather data update");

        processWeatherData(weatherApiService.fetchWeatherXMLData());
    }

    private void processWeatherData(String xmlString) {
        try {
            JAXBContext jaxbContext;
            try {
                jaxbContext = JAXBContext.newInstance(Forecasts.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                Forecasts employee = (Forecasts) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
                employee.forecast.forEach(data->System.out.println(data.night));
            } catch (JAXBException e) {
                e.printStackTrace();
            }

//            return Mono.empty();
        } catch (Exception e) {
            logger.error("Error processing weather data", e);
        }
    }

}