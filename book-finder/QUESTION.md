# Book Finder — Assignment

## Overview
Build a Spring Boot REST API that integrates with the **Open Library API** to search and browse books — no API key required.

**External API:** https://openlibrary.org/developers/api
**No API Key Required** (public, open API)
**Base URL:** `https://openlibrary.org`

---

## Endpoints to Implement

### 1. Search Books by Query
`GET /api/books/search?q=tolkien`
- Call `/search.json?q={query}`
- Open Library returns a large object with `numFound` and a `docs` array
- When `numFound` is 0 (or `docs` is empty) — return an empty list, not 404
- Return: list of { key, title, authorName, firstPublishYear, coverId }
  - `key` ← `docs[].key` (looks like `/works/OL45883W`)
  - `authorName` ← first entry of `docs[].author_name` (may be absent)
  - `firstPublishYear` ← `docs[].first_publish_year`
  - `coverId` ← `docs[].cover_i`
- Search results are large — map ONLY the fields listed above

### 2. Work (Book) Details
`GET /api/books/{workId}`
- Call `/works/{workId}.json` (e.g. `workId = OL45883W`)
- Return: { key, title, description, subjects, coverId }
- ⚠️ `description` may be a plain **String** OR an **object** `{ "value": "..." }` —
  handle both shapes and always return a plain string (or null)
- `subjects` ← `subjects[]` (list of strings, may be absent)
- `coverId` ← first entry of `covers[]` (may be absent)
- 404 if workId not found

### 3. Author Details
`GET /api/authors/{authorId}`
- Call `/authors/{authorId}.json` (e.g. `authorId = OL23919A`)
- Return: { key, name, birthDate, bio, topWork }
- ⚠️ `bio` has the SAME string-or-object quirk as `description` above
- `birthDate` ← `birth_date`
- `topWork` ← `top_work`
- 404 if authorId not found

### 4. Editions of a Work
`GET /api/books/{workId}/editions`
- Call `/works/{workId}/editions.json`
- Return summary list only from `entries[]`: { key, title, publishDate, numberOfPages }
  - `publishDate` ← `entries[].publish_date`
  - `numberOfPages` ← `entries[].number_of_pages`

### 5. Cover Image URL
- Covers are served at: `https://covers.openlibrary.org/b/id/{coverId}-L.jpg`
  (sizes: `S`, `M`, `L`)
- Endpoints that expose a `coverId` may also include the assembled cover URL

---

## Key Notes
- Open Library keys are returned WITH a prefix, e.g. `/works/OL45883W` or `/authors/OL23919A`.
  Path params take just the id (`OL45883W`) — strip/add the prefix as needed when calling the API.
- The `description` and `bio` fields are polymorphic (String **or** `{ value }`) — this is the
  trickiest part of the assignment; handle it cleanly (e.g. a custom deserializer or `JsonNode`).
- No authentication required.

---

## Resiliency Requirements
Open Library is a free third-party API — treat it as **unreliable**: it can be slow, time out,
return 5xx errors, or be briefly unavailable. Your service must degrade gracefully rather than hang
or propagate raw failures. Use **Resilience4j** (already on the classpath) to wrap the outbound calls.

Apply the following four patterns to the external calls (e.g. on the client/service methods):

1. **Timeout (TimeLimiter)** — bound how long you wait on Open Library. A hung dependency must not
   hang your endpoint. Suggested: ~2s per call.
2. **Retry** — re-attempt only transient failures (timeouts, 5xx, connection errors) with a short
   backoff. Do **not** retry on a clean 404. Suggested: 3 attempts, exponential backoff.
3. **Circuit Breaker** — once Open Library is failing past a threshold, fail fast (open the circuit)
   instead of hammering it, and recover automatically via a half-open probe.
4. **Fallback** — when a call ultimately fails (retries exhausted / circuit open), return a sensible
   degraded response instead of a 500:
   - search / editions → empty list
   - work / author details → `503 Service Unavailable` with a clear message (do not fabricate data)

### Notes
- Prefer configuring instances in `application.yaml` (`resilience4j.retry`, `.circuitbreaker`,
  `.timelimiter`) over hard-coded values, and apply them with annotations
  (`@Retry`, `@CircuitBreaker`, `@TimeLimiter`) or programmatically.
- Be explicit about **what counts as retryable** vs. a real client error (404). This distinction
  matters most.
- Expose circuit-breaker state via Actuator (`/actuator/health`) so the breaker is observable.
- A short test that simulates a slow/failing Open Library (e.g. stubbed client) and asserts the
  fallback fires is highly valued.

## Swagger UI
Once running: http://localhost:8096/swagger-ui.html
Generate static spec: `mvn verify` → `target/generated-docs/openapi.yaml`
