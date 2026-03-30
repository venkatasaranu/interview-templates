# Space Explorer — Assignment

## Overview
Build a Spring Boot REST API that integrates with **NASA Open APIs** to serve astronomy pictures, Mars rover photos, and near-Earth object data.

**External API:** https://api.nasa.gov
**Free API Key:** Sign up at https://api.nasa.gov (or use `DEMO_KEY` for 30 req/hour)
**Base URL:** `https://api.nasa.gov`

---

## Endpoints to Implement

### 1. Astronomy Picture of the Day (APOD)
`GET /api/apod?date=2024-01-15`
- Call `/planetary/apod?api_key={key}&date={date}`
- date is optional — defaults to today if omitted
- Return: date, title, explanation, mediaType ("image" or "video"), url, hdUrl, thumbnailUrl, copyright
- 400 if date format is invalid

### 2. APOD Date Range
`GET /api/apod/range?startDate=2024-01-01&endDate=2024-01-30`
- Call `/planetary/apod?api_key={key}&start_date={start}&end_date={end}`
- 400 if range exceeds 30 days
- Return: list of APOD entries

### 3. Mars Rover Photos
`GET /api/mars/photos?rover=curiosity&sol=1000&camera=FHAZ`
- Call `/mars-photos/api/v1/rovers/{rover}/photos?sol={sol}&camera={camera}&api_key={key}`
- Valid rovers: curiosity, opportunity, spirit
- 400 if rover name is invalid
- Return: list of { id, sol, cameraName, imgSrc, earthDate, roverName }

### 4. Near-Earth Objects Summary
`GET /api/neo?startDate=2024-01-01&endDate=2024-01-07`
- Call `/neo/rest/v1/feed?start_date={start}&end_date={end}&api_key={key}`
- 400 if date range exceeds 7 days
- NEO response is a map keyed by date — flatten all dates into one list
- Return: totalCount, hazardousCount, closestApproach { name, distanceKm, velocityKph, date }

### 5. Asteroid Details
`GET /api/neo/{asteroidId}`
- Call `/neo/rest/v1/neo/{asteroidId}?api_key={key}`
- 404 if asteroid not found
- Return: id, name, isPotentiallyHazardous, estimatedDiameter { minKm, maxKm }, closeApproachData list

---

## Key Notes
- DEMO_KEY limits: 30 req/hour, 50 req/day — get a free key for 1,000 req/hour
- Mars camera abbreviations: FHAZ, RHAZ, MAST, CHEMCAM, NAVCAM, PANCAM, MINITES
- NEO feed response: `{ "near_earth_objects": { "2024-01-01": [...], ... } }` — flatten

## Swagger UI
Once running: http://localhost:8091/swagger-ui.html
Generate static spec: `mvn verify` → `target/generated-docs/openapi.yaml`
