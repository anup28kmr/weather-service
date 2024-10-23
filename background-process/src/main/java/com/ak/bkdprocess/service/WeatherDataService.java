package com.ak.bkdprocess.service;

import com.ak.bkdprocess.repository.WeatherRepository;
import com.ak.common.entity.WeatherEntity;
import com.ak.common.exceptions.WeatherException;
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
    private final WeatherRepository weatherRepository;

    public WeatherDataService(WeatherApiService weatherApiService, WeatherRepository weatherRepository) {
        this.weatherApiService = weatherApiService;
        this.weatherRepository = weatherRepository;
    }

    @Scheduled(fixedRate = 30 * 60 * 1000) // 30 minutes
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

    private Mono<Void> processWeatherData(String xmlData) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

            NodeList observations = doc.getElementsByTagName("observation");
            LocalDateTime timestamp = LocalDateTime.now();

            for (int i = 0; i < observations.getLength(); i++) {
                Element observation = (Element) observations.item(i);
                NodeList stations = observation.getElementsByTagName("station");

                for (int j = 0; j < stations.getLength(); j++) {
                    Element station = (Element) stations.item(j);
                    WeatherEntity entity = parseStationData(station, timestamp);
                    weatherRepository.save(entity);
                }
            }

            return Mono.empty();
        } catch (Exception e) {
            return Mono.error(new WeatherException("Error processing weather data", e));
        }
    }

    private WeatherEntity parseStationData(Element station, LocalDateTime timestamp) {
        return WeatherEntity.builder()
                .location(getElementTextContent(station, "name"))
                .temperature(parseDouble(getElementTextContent(station, "airtemperature")))
                .phenomenon(getElementTextContent(station, "phenomenon"))
                .humidity(parseInt(getElementTextContent(station, "relativehumidity")))
                .precipitationProbability(parseInt(getElementTextContent(station, "precipitationprobability")))
                .timestamp(timestamp)
                .forecastTime(parseForecastTime(getElementTextContent(station, "forecasttime")))
                .build();
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