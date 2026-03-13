import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container">
      <div class="card starter">
        <h1>Angular Candidate Starter</h1>
        <p>This project is intentionally incomplete.</p>

        <h2>Your task</h2>
        <ol>
          <li>Create a flight search component.</li>
          <li>Create a flight service.</li>
          <li>Read from <code>src/assets/mock/flights.json</code>.</li>
          <li>Render a form and a results list.</li>
        </ol>

        <h2>Files to create</h2>
        <pre>src/app/components/flight-search/flight-search.component.ts
src/app/components/flight-search/flight-search.component.html
src/app/services/flight.service.ts</pre>

        <h2>Acceptance criteria</h2>
        <ul>
          <li>App runs with <code>npm start</code></li>
          <li>User can search by route</li>
          <li>Matching flights are displayed</li>
          <li>Code is readable and organized</li>
        </ul>

        <h2>UI mockup</h2>
        <p>Use the images below as reference for the search form and results view.</p>
        <div class="mockup">
          <div class="mockup-card">
            <h3>Search form</h3>
            <img src="assets/search-form.png" alt="Search form mockup" />
          </div>
          <div class="mockup-card">
            <h3>Results</h3>
            <img src="assets/result.png" alt="Results mockup" />
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .starter {
      padding: 24px;
      margin-top: 32px;
    }
    h1, h2 {
      margin-top: 0;
    }
    p, li {
      line-height: 1.5;
    }
    pre {
      background: #111827;
      color: #f9fafb;
      padding: 16px;
      border-radius: 8px;
      overflow: auto;
      white-space: pre-wrap;
    }
    code {
      background: #eef2ff;
      padding: 2px 6px;
      border-radius: 4px;
    }
  `]
})
export class AppComponent {}
