# Currency Converter — Assignment

## Overview
Build a Spring Boot REST API that integrates with the **ExchangeRate-API** (open endpoint) to provide live exchange rates and conversions — no API key required.

**External API:** https://www.exchangerate-api.com/docs/free
**No API Key Required** for the open endpoint
**Base URL:** `https://open.er-api.com/v6`
**Rate Limit:** 1,500 requests/month

---

## Endpoints to Implement

### 1. Latest Exchange Rates
`GET /api/rates/{baseCurrency}`
- Call `/latest/{baseCurrency}` (e.g., `/latest/USD`)
- Response: `{ "result":"success", "base_code":"USD", "rates":{ "EUR":0.91, ... }, "time_last_update_utc":"..." }`
- Return: baseCurrency, Map of all rates (160+ currencies), lastUpdated

### 2. Convert Currency
`GET /api/convert?from=USD&to=EUR&amount=100`
- Fetch rates for `from`, compute: `convertedAmount = amount * rates[to]`
- Return: from, to, amount, convertedAmount, rate, lastUpdated

### 3. Supported Currencies
`GET /api/currencies`
- Call `/codes`
- Response: `{ "result":"success", "supported_codes":[["USD","United States Dollar"],...] }`
- Return: list of { code, name }

### 4. Compare Against Multiple Targets
`GET /api/compare?base=USD&targets=EUR,GBP,JPY&amount=1000`
- Fetch rates for `base`, filter to only the requested `targets`
- Return: base, baseAmount, Map of { targetCurrency → convertedAmount }, lastUpdated

---

## Key Notes
- Open endpoint URL is `open.er-api.com` (different from the paid `v6.exchangerate-api.com`)
- `/codes` response uses a list of [code, name] pairs, not objects — map accordingly
- Invalid currency codes return `{ "result":"error", "error-type":"unsupported-code" }` — return 400

## Swagger UI
Once running: http://localhost:8097/swagger-ui.html
Generate static spec: `mvn verify` → `target/generated-docs/openapi.yaml`
