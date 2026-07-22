# AGENTS.md

## Project

This repository contains Financial Copilot, a personal financial monitoring, simulation and decision-support application.

Before modifying the codebase, read:

1. `README.md`
2. `docs/product-vision.md`
3. `docs/architecture.md`
4. `docs/domain-model.md`
5. `docs/roadmap.md`
6. the relevant files under `docs/decisions/`

## Primary objectives

The project has two equally important objectives.

### Financial objective

Create a reliable personal financial source of truth that can monitor:

* financial accounts;
* investment portfolios;
* real estate;
* collectible and personal assets;
* loans and liabilities;
* income and expenses;
* pensions;
* savings plans;
* FIRE objectives;
* historical financial snapshots;
* deterministic and probabilistic scenarios.

The application should answer questions such as:

* At what age could the household reasonably stop working?
* What is the impact of investing €3,000 per month?
* What happens if markets fall shortly before retirement?
* How much capital is required before pension income begins?
* Can retirement happen without selling personal or collectible assets?
* How does a new purchase or loan affect the FIRE date?
* Is the current financial trajectory still aligned with the plan?

### Development objective

The project must help its owner rediscover enjoyment in software development.

Avoid turning the project into a corporate CRUD application dominated by boilerplate, process or unnecessary infrastructure.

Prefer features involving:

* financial modelling;
* deterministic projections;
* Monte-Carlo simulations;
* scenario comparison;
* optimisation;
* interactive visualisations;
* AI tool calling;
* explainable calculations;
* modern Java and Angular experimentation.

A feature is especially valuable when it provides both personal financial value and technical interest.

## Current milestone

The current milestone is:

> Build the first FIRE simulation from manually provided financial assumptions.

The first usable vertical slice must:

1. accept a FIRE scenario;
2. calculate a deterministic projection;
3. run reproducible Monte-Carlo simulations;
4. calculate portfolio survival probability;
5. calculate useful percentiles;
6. expose the results through a REST API;
7. display them in a minimal Angular interface.

Do not implement unrelated modules before this vertical slice works.

## Initial technology choices

### Backend

* Java 21 or newer;
* Spring Boot;
* Maven;
* PostgreSQL;
* Flyway;
* Lombok;
* MapStruct;
* JUnit;
* AssertJ;
* REST APIs.

Use Lombok on domain entities, JPA entities and DTOs instead of hand-written
getters, setters, constructors or `equals`/`hashCode`. Do not hand-write
boilerplate that Lombok already generates.

Use MapStruct for mapping between JPA entities, domain models and DTOs
(request/response objects). Do not hand-write mapping code between these
layers; define a MapStruct mapper interface instead.

### Frontend

* Angular;
* TypeScript;
* standalone components where appropriate;
* interactive financial charts.

### Infrastructure

* Docker Compose for local infrastructure;
* GitHub Actions for build and tests;
* a modular monolith;
* no microservices during the initial phases.

## Architecture rules

Use a modular monolith organised by business capability.

Expected modules include:

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

Do not create microservices unless a documented architectural decision explicitly changes this rule.

Keep the calculation engine independent from:

* HTTP;
* persistence;
* Angular;
* AI providers;
* external financial services.

The simulation engine must be usable directly from unit tests.

Within each module, layer the code as:

```text
Controller  -> Service -> Repository
```

* Controllers must depend only on a service, never directly on a repository.
  They handle HTTP concerns (request/response DTOs, status codes,
  validation) and nothing else.
* Services hold the business logic and the transaction boundary
  (`@Transactional`). They are the only place allowed to use both the
  repository and the mapper.
* Repositories are limited to persistence.
* Do not introduce a service interface separate from its implementation
  unless there is a current reason for more than one implementation
  (avoid premature abstraction).

## Calculation rules

Financial calculations must be:

* deterministic when given a fixed random seed;
* reproducible;
* testable without Spring;
* explicit about assumptions;
* explicit about nominal versus inflation-adjusted values;
* independent from any large language model.

An AI model may select tools and explain results, but it must not be the authoritative calculation engine.

Use `BigDecimal` for stored monetary values and exact contractual calculations.

Simulation internals may use `double` where justified for statistical computations. Document conversions and precision assumptions.

## FIRE definitions

The system must distinguish:

* total net worth;
* liquid financial wealth;
* income-producing assets;
* FIRE-eligible assets;
* personal assets;
* assets that may only be sold in selected scenarios.

A classic car or primary residence must not automatically be treated as retirement funding.

A basic simulation succeeds when the financial portfolio remains above zero until the configured end age.

This definition may evolve, but changes must be documented and tested.

## Security and privacy

Never commit real personal financial data.

Real data must be stored only in ignored local files, local databases or approved encrypted storage.

The repository may contain:

* fictitious data;
* anonymised data;
* rounded examples.

The repository must not contain:

* names linked to financial values;
* account numbers;
* tax identifiers;
* bank exports;
* actual salary documents;
* actual loan documents;
* secrets or API keys.

Use files such as:

```text
datasets/example-fire-scenario.json
```

for public examples.

Use ignored locations such as:

```text
datasets-private/
.env
application-local.yml
```

for personal data.

## Development principles

### Build interesting vertical slices

Prefer a complete thin feature over several incomplete infrastructure layers.

### Avoid premature abstraction

Do not create an interface, factory, provider or generic framework unless there is a current reason for it.

### Avoid unnecessary infrastructure

Do not introduce Kafka, Kubernetes, distributed tracing, service discovery or microservices unless required by an accepted future decision.

### Keep the code clean

Clean does not mean over-engineered.

Use:

* clear names;
* small cohesive classes;
* immutable domain objects where practical;
* focused tests;
* explicit business rules;
* documented assumptions.

### Make progress visible

Each ticket should ideally produce something observable:

* a calculation;
* an API result;
* a chart;
* a scenario comparison;
* a meaningful test.

## Testing expectations

For financial engines:

* test nominal cases;
* test boundary conditions;
* test retirement and pension transition years;
* test portfolio depletion;
* test zero return;
* test negative returns;
* test inflation;
* test fixed random seeds;
* test percentile calculations.

Do not rely only on mocked Spring tests for domain calculations.

Prefer fast unit tests for the engine and limited integration tests for persistence and HTTP.

## Change process

Before implementing a ticket:

1. read the ticket;
2. read the relevant documentation;
3. inspect existing code;
4. state any assumptions;
5. propose a small implementation plan;
6. implement only the requested scope;
7. add or update tests;
8. update documentation when behaviour or architecture changes.

Do not silently make large architectural changes.

If a requested change conflicts with an existing decision, identify the conflict before implementing it.

## Definition of done

A ticket is complete when:

* acceptance criteria are met;
* code compiles;
* relevant tests pass;
* no personal data is committed;
* assumptions are documented;
* the README or technical documentation is updated when needed;
* the implementation remains within the modular monolith boundaries.

## Current exclusions

Unless explicitly requested, do not build:

* authentication;
* multi-user support;
* bank API integration;
* automatic transaction categorisation;
* tax advice;
* investment recommendations;
* AI chat;
* microservices;
* production cloud deployment.

These may be added later after the first simulation vertical slice is complete.
