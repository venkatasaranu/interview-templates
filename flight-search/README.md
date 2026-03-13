# Angular Candidate Template

This is a runnable Angular + TypeScript starter project for a coding interview.

## Goal

Build a small feature that reads from the mock file below and renders the UI yourself.

### Mock data provided
- `src/assets/mock/flights.json`

## Your task

Create the missing files and wire them into the app:

- `src/app/components/flight-search/flight-search.component.ts`
- `src/app/components/flight-search/flight-search.component.html`
- `src/app/services/flight.service.ts`

## Suggested requirements

1. Load flights from the mock JSON file
2. Show a simple search form with:
   - from
   - to
   - depart date
3. Filter matching flights
4. Display a results list with:
   - airline
   - flight number
   - from / to
   - departure time
   - arrival time
   - price

## Run

```bash
npm install
npm start
```

Then open:

```bash
http://localhost:4200
```

## Notes

- The project is intentionally incomplete.
- It still runs so the candidate can start coding immediately.
- The mock data file is preserved.
