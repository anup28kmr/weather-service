package com.ak.bkdprocess.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

@Slf4j
public class HttpClientWithRetry {

  private final HttpClient httpClient;
  private final int maxRetries;
  private final Duration retryDelay;

  public HttpClientWithRetry(HttpClient httpClient, int maxRetries, Duration retryDelay) {
    this.httpClient = httpClient;
    this.maxRetries = maxRetries;
    this.retryDelay = retryDelay;
  }

  public HttpResponse<String> sendWithRetry(HttpRequest request) throws IOException, InterruptedException {
    int attempt = 1;
    while (attempt <= maxRetries) {
      try {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Check for successful response, or decide to retry based on status
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
          return response; // Successful response
        } else if (response.statusCode() >= 500) {
          log.info("Server error, retrying... Attempt: {}", attempt);
        } else {
          return response; // Non-retryable status
        }
      } catch (HttpTimeoutException e) {
        log.error("Request timed out, retrying... Attempt: {}", attempt);
      } catch (IOException | InterruptedException e) {
        log.error("Network error, retrying... Attempt: {}", attempt);
      }

      attempt++;
      Thread.sleep(retryDelay.toMillis());
    }

    throw new IOException("Failed after " + maxRetries + " attempts");
  }

}
