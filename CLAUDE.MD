@AGENTS.md

# Claude Code instructions

## Required context

Before starting any implementation task:

1. Read `README.md`.
2. Read the relevant GitHub issue.
3. Read the relevant documentation under `docs/`.
4. Inspect the existing code before proposing changes.
5. Implement only the scope of the current issue.

## Working method

For each issue:

1. Summarise your understanding of the task.
2. Identify the files likely to be created or modified.
3. State any important assumptions.
4. Propose a concise implementation plan.
5. Wait for explicit approval only when the requested change is destructive,
   ambiguous or significantly broader than the issue.
6. Implement the change.
7. Run the relevant build, tests and lint commands.
8. Report failures honestly.
9. Summarise the files changed and the main design decisions.

## GitHub issues

Treat the selected GitHub issue as the source of truth for the immediate scope.

Do not implement future issues pre-emptively.

Do not add infrastructure, abstractions or dependencies unless they are required
by the current issue or by an accepted architectural decision.

## Project priorities

The project has two equally important objectives:

1. Build a useful personal financial monitoring and FIRE simulation application.
2. Restore enjoyment and curiosity in software development.

Prefer solutions that are:

- financially useful;
- technically interesting;
- observable quickly;
- clean but not over-engineered;
- easy to test and evolve.

## First milestone

The current priority is the first FIRE simulation vertical slice:

1. Spring Boot backend;
2. FIRE scenario domain model;
3. deterministic projection;
4. Monte-Carlo simulation;
5. REST API;
6. minimal Angular interface;
7. useful financial visualisation.

Do not start AI integration, bank imports, authentication or production
deployment before this vertical slice works, unless explicitly requested.

## Privacy

Never commit real personal financial information.

Before committing, check that modified files do not contain:

- real account balances linked to a person;
- bank account numbers;
- salary documents;
- loan documents;
- tax information;
- API keys;
- passwords;
- tokens;
- private exports.

Use anonymised or fictitious values in versioned examples.
