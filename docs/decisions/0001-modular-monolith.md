# ADR 0001 — Use a modular monolith

## Status

Accepted

## Context

The project contains several business capabilities but is initially built and
operated by one developer for one household.

Microservices would add deployment, communication and operational complexity
without providing immediate product value.

## Decision

The application will initially be implemented as a modular monolith.

Modules will be organised by business capability rather than by technical layer.

## Consequences

- one backend deployment;
- one database initially;
- module boundaries remain explicit;
- domain and simulation code must remain independent from HTTP and persistence;
- microservices require a new accepted ADR.
