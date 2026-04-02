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

## Swagger UI
Once running: http://localhost:8095/swagger-ui.html
Generate static spec: `mvn verify` → `target/generated-docs/openapi.yaml`
