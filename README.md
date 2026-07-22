# Financial Copilot

Financial Copilot is a personal financial monitoring, simulation and decision-support application.

Its main purpose is to answer one central question:

> Am I financially on track to regain control of my time?

The application will progressively consolidate assets, liabilities, cash flows, investment plans, pensions and financial goals into a single source of truth.

It will provide deterministic projections, Monte-Carlo simulations, scenario comparisons and eventually an AI assistant capable of explaining the results in natural language.

---

## Why this project exists

My financial information is currently spread across:

* spreadsheets;
* loan amortisation schedules;
* investment simulations;
* pension estimates;
* asset valuations;
* one-off calculations;
* personal notes and discussions.

I regularly need to answer questions such as:

* What could investing €3,000 per month produce?
* At what age could I realistically stop working?
* What happens if markets fall shortly before retirement?
* How much capital is needed before pension income begins?
* Can I retire without selling personal or collectible assets?
* Does a new property purchase materially delay financial independence?
* Is my actual financial situation still aligned with the original plan?

Financial Copilot aims to unify these questions, assumptions and calculations within one application.

---

## A second objective

This project also has an equally important personal objective:

> Rediscover the enjoyment of software development.

The project should not become another corporate CRUD application dominated by boilerplate, process and unnecessary infrastructure.

It should create room for:

* financial modelling;
* deterministic projections;
* Monte-Carlo simulations;
* optimisation problems;
* scenario exploration;
* interactive visualisations;
* AI tool calling;
* explainable calculations;
* experimentation with modern Java and Angular;
* building something personally meaningful.

Technical cleanliness matters, but it must support curiosity rather than replace it.

A feature is especially valuable when it provides both:

1. real personal financial value;
2. genuine technical interest.

---

## Product vision

Financial Copilot should become a personal financial digital twin and scenario laboratory.

It will maintain a structured representation of:

* household members;
* financial accounts;
* investment portfolios;
* real estate;
* personal and collectible assets;
* loans and liabilities;
* income;
* expenses;
* pensions;
* savings plans;
* financial goals;
* historical financial snapshots;
* simulated scenarios.

An AI assistant will eventually use this structured information and dedicated calculation tools to answer questions in natural language.

The AI will not perform critical financial calculations itself.

Calculations must be executed by deterministic and probabilistic engines. The AI will select the appropriate tools, generate scenarios and explain the results.

---

## Guiding principles

### Personal value first

Every major feature should answer a real financial question.

### Interesting before exhaustive

The first versions should deliver engaging simulations rather than exhaustive administration features.

### Calculations before AI

The financial engine must remain independent, testable and reproducible.

Artificial intelligence will be added as an interface and explanation layer.

### Probabilities over false precision

Long-term financial projections are uncertain.

Results should use scenarios, distributions and confidence levels instead of presenting one future value as certain.

### Net worth is not FIRE capital

The system must distinguish between:

* total net worth;
* liquid financial assets;
* income-producing assets;
* FIRE-eligible assets;
* personal assets;
* assets that may only be sold in selected scenarios.

A primary residence or classic car must not automatically be treated as retirement funding.

### Real and nominal values must remain explicit

The application must clearly distinguish between:

* future nominal euros;
* inflation-adjusted euros expressed in current purchasing power.

### Clean, not over-engineered

The initial architecture will be a modular monolith.

Microservices, messaging infrastructure and complex deployment systems will only be introduced when they solve a real problem or provide meaningful technical exploration.

### Private by design

Real personal financial data must never be committed to the repository.

Only fictitious, anonymised or rounded examples may be versioned.

---

## Initial use case

The first milestone will answer:

> Based on my current portfolio, monthly investments, expenses and expected pension, what is the probability that my portfolio lasts if I retire at a given age?

The initial application will support:

* a manually defined FIRE scenario;
* a deterministic financial projection;
* reproducible Monte-Carlo simulations;
* configurable return, volatility and inflation assumptions;
* accumulation before retirement;
* withdrawals after retirement;
* pension income from a configured age;
* portfolio survival probability;
* final portfolio percentiles;
* a minimal visual dashboard.

---

## First milestone

The first end-to-end version should allow a user to:

1. enter a FIRE scenario;
2. run a deterministic projection;
3. run several thousand Monte-Carlo simulations;
4. calculate the success and failure rates;
5. calculate useful percentiles;
6. inspect projected portfolio trajectories;
7. compare several retirement ages.

The first version does not need persistent personal data.

A JSON file or manually entered values are sufficient.

---

## Planned architecture

### Backend

* Java 21 or newer;
* Spring Boot;
* Maven;
* PostgreSQL;
* Flyway;
* JUnit;
* AssertJ;
* REST API.

### Frontend

* Angular;
* TypeScript;
* interactive scenario forms;
* financial charts;
* scenario comparison views.

### Infrastructure

* Docker Compose for local dependencies;
* GitHub Actions for builds and tests;
* GitHub Issues and Milestones for planning;
* one modular monolith during the initial phases.

---

## Proposed domain modules

```text
household
portfolio
cashflow
liability
pension
goal
scenario
simulation
reporting
assistant
```

These modules belong to a single deployable backend during the initial phases.

The calculation engine must remain independent from:

* HTTP;
* persistence;
* Angular;
* AI providers;
* external financial services.

---

## Simulation model

The first FIRE scenario should contain:

* current age;
* retirement age;
* simulation end age;
* current financial portfolio;
* monthly contribution before retirement;
* monthly expenses after retirement;
* expected pension;
* pension start age;
* expected annual return;
* annual volatility;
* annual inflation;
* simulation count;
* random seed.

A basic simulation succeeds when the FIRE-eligible portfolio remains above zero until the configured end age.

This definition may evolve as the model becomes more realistic.

---

## Roadmap

### Phase 1 — Simulation prototype

* bootstrap the backend;
* define the initial FIRE scenario;
* build a deterministic projection engine;
* build a Monte-Carlo engine;
* expose a simulation API;
* display the results in a minimal Angular dashboard.

### Phase 2 — Financial source of truth

* model assets and liabilities;
* model loans and repayment schedules;
* model income and expenses;
* calculate net worth;
* calculate FIRE-eligible capital;
* create monthly financial snapshots.

### Phase 3 — Scenario laboratory

* save scenarios;
* compare retirement ages;
* model part-time work;
* model asset sales;
* model property purchases;
* model market crashes;
* model inflation shocks;
* explain the financial impact of decisions.

### Phase 4 — Personal monitoring

* import spreadsheet and CSV data;
* compare actual results with projections;
* track savings rate;
* track debt evolution;
* track investment contributions;
* detect deviations from the target trajectory.

### Phase 5 — AI financial assistant

* ask questions in natural language;
* select and execute calculation tools;
* generate scenarios from a conversation;
* explain changes in the projected FIRE date;
* produce monthly financial summaries;
* highlight risks and uncertain assumptions.

---

## Out of scope for the first milestone

The following features should not be implemented before the initial simulation works:

* authentication;
* multi-user support;
* bank API integration;
* automatic transaction categorisation;
* tax advice;
* investment recommendations;
* AI chat;
* microservices;
* Kafka;
* Kubernetes;
* production cloud deployment.

---

## Data privacy

The repository may contain:

* fictitious data;
* anonymised examples;
* rounded financial values;
* sample scenarios.

The repository must not contain:

* real account numbers;
* tax identifiers;
* bank statements;
* salary certificates;
* loan documents;
* API keys;
* passwords;
* unencrypted exports of personal financial data.

Suggested ignored locations:

```text
datasets-private/
.env
application-local.yml
*.private.json
```

---

## Repository structure

```text
financial-copilot/
├── backend/
├── frontend/
├── docs/
│   ├── decisions/
│   ├── milestones/
│   ├── architecture.md
│   ├── domain-model.md
│   └── product-vision.md
├── datasets/
│   └── example-fire-scenario.json
├── datasets-private/
├── .github/
│   └── ISSUE_TEMPLATE/
├── AGENTS.md
├── README.md
├── docker-compose.yml
└── .gitignore
```

---

## Getting started — Backend

The backend lives under `backend/` and is a standalone Spring Boot / Maven application.

### Prerequisites

* Java 21
* Maven

### Build

```bash
cd backend
mvn verify
```

### Run

```bash
cd backend
mvn spring-boot:run
```

The application starts on port `8666`.

### Health check

```bash
curl http://localhost:8666/api/health
```

Expected response:

```json
{"status":"UP"}
```

---

## Development workflow

Each feature should be developed through a focused GitHub issue.

Before implementing a ticket:

1. read `AGENTS.md`;
2. read the relevant documentation;
3. inspect the existing code;
4. state assumptions;
5. propose a small implementation plan;
6. implement only the requested scope;
7. add or update tests;
8. update documentation when behaviour changes.

Prefer complete vertical slices over several incomplete infrastructure layers.

Every ticket should ideally produce something observable:

* a calculation;
* an API response;
* a chart;
* a scenario comparison;
* a meaningful test.

---

## Success criteria

The project is successful when it does both of the following:

1. it improves understanding of the household's financial trajectory;
2. it makes its owner want to build the next feature.

If the application becomes useful but development stops being enjoyable, the roadmap should be adjusted.

If development is enjoyable but the product does not help answer real financial questions, the model should be adjusted.

---

## Disclaimer

Financial Copilot is a personal simulation and decision-support tool.

It does not provide regulated financial, tax, legal or investment advice.

Its results depend on user-provided assumptions and uncertain future market conditions.
