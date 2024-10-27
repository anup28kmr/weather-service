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
    public void updateWeatherData() {
        logger.info("Starting scheduled weather data update");

        weatherApiService.fetchWeatherData()
                .doOnNext(data -> logger.debug("Received XML data of length: {}", data.length()))
                .flatMap(this::processWeatherData)
                .doOnError(error -> logger.error("Error updating weather data", error))
                .subscribe(
                        result -> logger.info("Successfully updated weather data"),
                        error -> logger.error("Failed to update weather data", error)
                );
    }

    private Mono<Void> processWeatherData(String xmlString) {
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

            return Mono.empty();
        } catch (Exception e) {
            return Mono.error(new WeatherException("Error processing weather data", e));
        }
    }
    private String getElementTextContent(Element parent, String tagName) {
        NodeList elements = parent.getElementsByTagName(tagName);
        return elements.getLength() > 0 ? elements.item(0).getTextContent() : null;
    }

    private Double parseDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            logger.warn("Failed to parse double value: {}", value);
            return null;
        }
    }

    private Integer parseInt(String value) {
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            logger.warn("Failed to parse integer value: {}", value);
            return null;
        }
    }

    private LocalDateTime parseForecastTime(String value) {
        try {
            return value != null ? LocalDateTime.parse(value, DATE_TIME_FORMATTER) : null;
        } catch (Exception e) {
            logger.warn("Failed to parse datetime value: {}", value);
            return null;
        }
    }
}