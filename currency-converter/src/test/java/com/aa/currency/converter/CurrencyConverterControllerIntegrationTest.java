package com.aa.currency.converter;

import com.aa.currency.converter.dto.ConversionResponse;
import com.aa.currency.converter.dto.ExchangeRatesResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CurrencyConverterControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldGetLatestRates() {
        webTestClient.get()
                .uri("/api/rates/USD")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeRatesResponse.class)
                .value(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getBaseCurrency()).isNotNull();
                    assertThat(response.getRates()).isNotEmpty();
                    assertThat(response.getLastUpdated()).isNotNull();
                });
    }

    @Test
    void shouldConvertCurrency() {
        webTestClient.get()
                .uri("/api/convert?from=USD&to=EUR&amount=100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ConversionResponse.class)
                .value(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getFrom()).isEqualTo("USD");
                    assertThat(response.getTo()).isEqualTo("EUR");
                    assertThat(response.getAmount()).isEqualTo(100.0);
                    assertThat(response.getConvertedAmount()).isGreaterThan(0);
                    assertThat(response.getRate()).isGreaterThan(0);
                });
    }

    @Test
    void shouldGetSupportedCurrencies() {
        webTestClient.get()
                .uri("/api/currencies")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Object.class)
                .hasSize(1); // Array with 1 item (the currencies object)
    }

    @Test
    void shouldCompareRates() {
        webTestClient.get()
                .uri("/api/compare?base=USD&targets=EUR,GBP,JPY&amount=1000")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.base").isEqualTo("USD")
                .jsonPath("$.baseAmount").isEqualTo(1000)
                .jsonPath("$.conversions").isMap()
                .jsonPath("$.conversions.EUR").isNotEmpty()
                .jsonPath("$.conversions.GBP").isNotEmpty()
                .jsonPath("$.conversions.JPY").isNotEmpty();
    }

    @Test
    void shouldHandleInvalidCurrency() {
        webTestClient.get()
                .uri("/api/convert?from=INVALID&to=EUR&amount=100")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void shouldHandleInvalidTargetCurrency() {
        webTestClient.get()
                .uri("/api/convert?from=USD&to=INVALID&amount=100")
                .exchange()
                .expectStatus().is4xxClientError();
    }
}

