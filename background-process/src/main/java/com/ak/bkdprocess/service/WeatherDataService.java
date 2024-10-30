package com.ak.bkdprocess.service;

import com.ak.bkdprocess.repository.ForecastRepository;
import com.ak.common.entity.Forecasts;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherDataService {
  private static final Logger logger = LoggerFactory.getLogger(WeatherDataService.class);
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Autowired
  private ForecastRepository forecastRepository;

  @Autowired
  private WeatherApiService weatherApiService;

  @Scheduled(fixedRateString = "${app.scheduled.interval}")
  public void updateWeatherData() {
    logger.info("Starting scheduled weather data update");

    processWeatherData(weatherApiService.fetchWeatherXMLData());
  }

  private void processWeatherData(String xmlString) {
    try {
      JAXBContext jaxbContext;
      try {
        jaxbContext = JAXBContext.newInstance(Forecasts.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Forecasts forecasts = (Forecasts) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
        forecastRepository.saveAll(forecasts.getForecast());

      } catch (JAXBException e) {
        logger.error("something went wrong", e);
      }

    } catch (Exception e) {
      logger.error("Error processing weather data", e);
    }
  }

}