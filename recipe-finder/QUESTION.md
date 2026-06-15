# Recipe Finder — Assignment

## Overview
Build a Spring Boot REST API that integrates with **TheMealDB API** to search and browse recipes — no API key required.

**External API:** https://www.themealdb.com/api.php
**No API Key Required** (public test key "1" is embedded in the base URL)
**Base URL:** `https://www.themealdb.com/api.json/v1/1`

---

## Endpoints to Implement

### 1. Search Recipes by Name
`GET /api/recipes/search?q=chicken`
- Call `/search.php?s={query}`
- TheMealDB returns `{ "meals": null }` when nothing matches — return empty list, not 404
- Return: list of { id, name, thumbnailUrl }

### 2. Recipe Details
`GET /api/recipes/{mealId}`
- Call `/lookup.php?i={mealId}`
- Return full recipe: id, name, category, area, instructions, thumbnailUrl, youtubeUrl, tags
- Include ingredients as Map<String, String> (ingredient → measure)
- TheMealDB stores ingredients as flat fields: strIngredient1..20 + strMeasure1..20
- Skip entries where ingredient is blank/null
- 404 if mealId not found

### 3. Random Recipe
`GET /api/recipes/random`
- Call `/random.php`
- Return the same full recipe structure as endpoint #2

### 4. Recipes by Category
`GET /api/recipes/category/{category}`
- Call `/filter.php?c={category}`
- Return summary list only: id, name, thumbnailUrl
- Example categories: Seafood, Chicken, Beef, Pasta, Dessert

### 5. All Categories
`GET /api/categories`
- Call `/categories.php`
- Return: list of { id, name, thumbnailUrl, description }

---

## Key Notes
- All TheMealDB responses are wrapped in `{ "meals": [...] }` or `{ "categories": [...] }`
- Ingredients mapping: zip strIngredient1–20 with strMeasure1–20, skip blank entries
- The filter endpoint returns summaries only — use lookup.php for full detail
- No authentication required

---

## Resiliency Requirements
TheMealDB is a free third-party API — treat it as **unreliable**: it can be slow, time out, return
5xx errors, or be briefly unavailable. Your service must degrade gracefully rather than hang or
propagate raw failures. Use **Resilience4j** (already on the classpath) to wrap the outbound calls.

Apply the following four patterns to the external calls (e.g. on the client/service methods):

1. **Timeout (TimeLimiter)** — bound how long you wait on TheMealDB. A hung dependency must not
   hang your endpoint. Suggested: ~2s per call.
2. **Retry** — re-attempt only transient failures (timeouts, 5xx, connection errors) with a short
   backoff. Do **not** retry on a clean 404. Suggested: 3 attempts, exponential backoff.
3. **Circuit Breaker** — once TheMealDB is failing past a threshold, fail fast (open the circuit)
   instead of hammering it, and recover automatically via a half-open probe.
4. **Fallback** — when a call ultimately fails (retries exhausted / circuit open), return a sensible
   degraded response instead of a 500:
   - search / category / categories → empty list
   - random / details → `503 Service Unavailable` with a clear message (do not fabricate a recipe)

### Notes
- Prefer configuring instances in `application.yaml` (`resilience4j.retry`, `.circuitbreaker`,
  `.timelimiter`) over hard-coded values, and apply them with annotations
  (`@Retry`, `@CircuitBreaker`, `@TimeLimiter`) or programmatically.
- Be explicit about **what counts as retryable** vs. a real client error (404). This distinction
  matters most.
- Expose circuit-breaker state via Actuator (`/actuator/health`) so the breaker is observable.
- A short test that simulates a slow/failing TheMealDB (e.g. stubbed client) and asserts the
  fallback fires is highly valued.

## Swagger UI
Once running: http://localhost:8095/swagger-ui.html
Generate static spec: `mvn verify` → `target/generated-docs/openapi.yaml`
