# Milestone 1 — First FIRE Simulation

## Objective

Deliver the first end-to-end FIRE simulation.

The user must be able to provide a manually defined scenario and receive a deterministic and probabilistic projection.

## User question

> Based on the current portfolio, monthly investments, expenses and expected pension, what is the probability that the portfolio lasts if retirement starts at a given age?

## Inputs

The initial scenario should support:

* current age;
* retirement age;
* simulation end age;
* current financial portfolio;
* monthly contribution before retirement;
* monthly expenses after retirement;
* expected monthly pension;
* pension start age;
* expected annual investment return;
* annual investment volatility;
* annual inflation;
* simulation count;
* random seed.

## Outputs

The simulation should provide:

* success rate;
* failure rate;
* median final portfolio;
* 10th percentile final portfolio;
* 90th percentile final portfolio;
* median portfolio trajectory;
* prudent trajectory;
* favourable trajectory;
* depletion age distribution where applicable.

## Initial success definition

A trajectory succeeds when the FIRE-eligible financial portfolio remains above zero until the configured simulation end age.

## Delivery sequence

1. bootstrap the backend;
2. define the scenario model;
3. implement deterministic projection;
4. implement Monte-Carlo simulation;
5. expose the REST endpoint;
6. bootstrap the Angular application;
7. create the scenario form;
8. display simulation results and trajectories;
9. run an anonymised reference scenario.

## Out of scope

* persistent household data;
* Excel imports;
* bank integrations;
* authentication;
* AI chat;
* production deployment;
* tax calculations;
* product recommendations.
