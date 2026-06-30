# Weather Dashboard — Assignment

## Overview
Build a Spring Boot REST API that integrates with the **OpenWeatherMap API** to provide weather data for cities around the world.

**External API:** https://openweathermap.org/api
**Free API Key:** aef817ffb48cde95ef91d896e9b383f6
**Base URL:** `https://api.openweathermap.org`

---

## Endpoints to Implement

### 1. Current Weather
`GET /api/weather/current?city=London`
- Call OWM `/data/2.5/weather?q={city}&appid={key}&units=metric`
- Return: city, country, temperature (°C), feelsLike, humidity, windSpeed (m/s), description, iconUrl
- 404 if city not found

### 2. 5-Day Forecast
`GET /api/weather/forecast?city=London&days=5`
- Call OWM `/data/2.5/forecast?q={city}&appid={key}&units=metric`
- OWM returns 40 entries (3-hourly over 5 days) — group them by calendar day
- Return per day: date, tempMin, tempMax, description, iconUrl, precipitationProbability
- days param controls how many days to return (1–5, default 5)

### 3. Compare Cities
`GET /api/weather/compare?cities=London,Paris,Tokyo`
- Accept comma-separated city names
- Fetch current weather for each city (consider parallel WebClient calls)
- Return all cities sorted by temperature descending

### 4. Air Quality Index
`GET /api/weather/air-quality?city=London`
- First geocode: GET `/geo/1.0/direct?q={city}&limit=1&appid={key}` → lat, lon
- Then fetch AQI: GET `/data/2.5/air_pollution?lat={lat}&lon={lon}&appid={key}`
- Return: city, aqi (1–5), aqiLabel (Good/Fair/Moderate/Poor/Very Poor), co, no2, o3, pm2_5, pm10 (μg/m³)

### 5. City Autocomplete
`GET /api/cities/search?q=Lon`
- Call OWM `/geo/1.0/direct?q={query}&limit=5&appid={key}`
- Return: list of { name, country, state, lat, lon }

---

## Key Notes
- Icon URL: `https://openweathermap.org/img/wn/{icon}@2x.png`
- OWM returns UNIX timestamps — convert to LocalDateTime using the city's timezone offset
- For compare, use parallel WebClient calls for performance
- Free tier rate limit: 60 calls/minute

## Swagger UI
Once running: http://localhost:8090/swagger-ui.html
Generate static spec: `mvn verify` → `target/generated-docs/openapi.yaml`
