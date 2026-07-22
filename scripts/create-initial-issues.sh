#!/usr/bin/env bash

set -euo pipefail

REPO="${1:-}"

if ! command -v gh >/dev/null 2>&1; then
  echo "Error: GitHub CLI is not installed."
  echo "Install it with: brew install gh"
  exit 1
fi

if ! gh auth status >/dev/null 2>&1; then
  echo "Error: GitHub CLI is not authenticated."
  echo "Run: gh auth login"
  exit 1
fi

repo_args=()

if [[ -n "$REPO" ]]; then
  repo_args=(--repo "$REPO")
fi

create_issue() {
  local title="$1"
  local labels="$2"
  local body="$3"

  echo "Creating issue: $title"

  gh issue create \
    "${repo_args[@]}" \
    --title "$title" \
    --label "$labels" \
    --body "$body"
}

echo "Creating labels..."

gh label create backend \
  "${repo_args[@]}" \
  --description "Backend development" \
  --color "1D76DB" \
  --force

gh label create frontend \
  "${repo_args[@]}" \
  --description "Frontend development" \
  --color "5319E7" \
  --force

gh label create simulation \
  "${repo_args[@]}" \
  --description "Financial simulation engine" \
  --color "FBCA04" \
  --force

gh label create domain \
  "${repo_args[@]}" \
  --description "Domain modelling and business rules" \
  --color "0E8A16" \
  --force

gh label create documentation \
  "${repo_args[@]}" \
  --description "Documentation changes" \
  --color "0075CA" \
  --force

gh label create infrastructure \
  "${repo_args[@]}" \
  --description "Build, CI and local infrastructure" \
  --color "BFDADC" \
  --force

gh label create technical-experiment \
  "${repo_args[@]}" \
  --description "Technically interesting experiment" \
  --color "D93F0B" \
  --force

gh label create personal-value \
  "${repo_args[@]}" \
  --description "Direct personal financial value" \
  --color "C2E0C6" \
  --force

create_issue \
  "Bootstrap Spring Boot backend" \
  "backend" \
  "$(cat <<'EOF'
## Goal

Create the initial Spring Boot backend application.

## Scope

- Java 21
- Spring Boot
- Maven
- package root: `com.mathieu.financialcopilot`
- health endpoint: `GET /api/health`
- one automated test
- startup instructions in the README

## Acceptance criteria

- [ ] The backend project exists under `backend/`
- [ ] `mvn verify` succeeds
- [ ] The application starts locally
- [ ] `GET /api/health` returns HTTP 200
- [ ] The response contains `{"status":"UP"}`
- [ ] Startup instructions are documented

## Out of scope

- Database
- Authentication
- Docker
- Angular
- Financial calculations

## AI instructions

Read `AGENTS.md` and `README.md` before implementation.

Implement only this issue.
EOF
)"

create_issue \
  "Define initial FIRE scenario domain model" \
  "backend,domain,simulation,personal-value" \
  "$(cat <<'EOF'
## Goal

Define the input model required to run the first FIRE simulation.

## Required fields

- current age
- retirement age
- simulation end age
- current financial portfolio
- monthly contribution before retirement
- monthly retirement expenses
- expected monthly pension
- pension start age
- expected annual return
- annual volatility
- annual inflation
- simulation count
- random seed

## Acceptance criteria

- [ ] An immutable FIRE scenario model exists
- [ ] Invalid age ranges are rejected
- [ ] Negative monetary amounts are rejected where inappropriate
- [ ] Rates and assumptions are explicit
- [ ] Unit tests cover valid and invalid scenarios
- [ ] An anonymised JSON example is included

## Out of scope

- Persistence
- REST endpoint
- Monte-Carlo calculations
- Angular form
EOF
)"

create_issue \
  "Implement deterministic FIRE projection" \
  "backend,simulation,personal-value,technical-experiment" \
  "$(cat <<'EOF'
## Goal

Implement a deterministic annual financial projection for a FIRE scenario.

## Behaviour

The projection must support:

- portfolio growth before retirement
- monthly contributions before retirement
- withdrawals after retirement
- expense inflation
- pension income from the configured pension age
- portfolio depletion detection
- annual projection results

## Acceptance criteria

- [ ] The engine is independent from Spring and HTTP
- [ ] The same input always produces the same result
- [ ] Results include one entry per projected year
- [ ] Retirement transition is tested
- [ ] Pension transition is tested
- [ ] Zero-return and negative-return cases are tested
- [ ] Portfolio depletion is tested
- [ ] Monetary and rounding assumptions are documented

## Out of scope

- Random returns
- Monte-Carlo simulation
- Persistence
- Frontend
EOF
)"

create_issue \
  "Implement Monte-Carlo simulation engine" \
  "backend,simulation,personal-value,technical-experiment" \
  "$(cat <<'EOF'
## Goal

Implement reproducible Monte-Carlo simulations for FIRE scenarios.

## Behaviour

The engine must:

- run a configurable number of trajectories
- generate variable annual returns
- use a configurable random seed
- report success and failure rates
- calculate final portfolio percentiles
- expose representative trajectories
- report depletion ages when applicable

## Acceptance criteria

- [ ] A fixed seed produces reproducible results
- [ ] The simulation count is configurable
- [ ] Success rate is calculated
- [ ] Median final portfolio is calculated
- [ ] 10th and 90th percentiles are calculated
- [ ] Tests cover percentile calculations
- [ ] Performance is measured for at least 10,000 simulations
- [ ] Statistical assumptions are documented

## Out of scope

- Historical market data
- Correlated asset classes
- REST endpoint
- Frontend charts
EOF
)"

create_issue \
  "Expose FIRE simulation REST API" \
  "backend,simulation" \
  "$(cat <<'EOF'
## Goal

Expose the FIRE simulation engine through a REST endpoint.

## Endpoint

`POST /api/simulations/fire`

## Acceptance criteria

- [ ] The endpoint accepts a valid FIRE scenario
- [ ] Invalid requests return HTTP 400
- [ ] The response includes success rate
- [ ] The response includes final portfolio percentiles
- [ ] The response includes representative trajectories
- [ ] HTTP integration tests are present
- [ ] A request and response example is documented

## Out of scope

- Authentication
- Persistence
- Rate limiting
- AI integration
EOF
)"

create_issue \
  "Bootstrap Angular frontend" \
  "frontend" \
  "$(cat <<'EOF'
## Goal

Create the initial Angular frontend application.

## Scope

- Angular application under `frontend/`
- development configuration
- basic application shell
- backend health status display

## Acceptance criteria

- [ ] The Angular application starts locally
- [ ] The application can call the backend health endpoint
- [ ] Loading and error states are displayed
- [ ] Frontend startup instructions are documented
- [ ] Lint and tests pass

## Out of scope

- FIRE scenario form
- Financial charts
- Authentication
- Design system
EOF
)"

create_issue \
  "Create FIRE scenario form" \
  "frontend,simulation,personal-value" \
  "$(cat <<'EOF'
## Goal

Allow the user to enter the assumptions required for a FIRE simulation.

## Acceptance criteria

- [ ] All initial scenario fields can be entered
- [ ] Monetary fields show their units
- [ ] Percentage fields show their units
- [ ] Age constraints are validated
- [ ] Invalid forms cannot be submitted
- [ ] The form calls the FIRE simulation endpoint
- [ ] API errors are displayed clearly

## Out of scope

- Scenario persistence
- User accounts
- Excel import
EOF
)"

create_issue \
  "Display FIRE simulation results" \
  "frontend,simulation,personal-value,technical-experiment" \
  "$(cat <<'EOF'
## Goal

Display FIRE simulation results in a useful and understandable dashboard.

## Acceptance criteria

- [ ] Success rate is prominently displayed
- [ ] Median final portfolio is displayed
- [ ] Prudent and favourable percentiles are displayed
- [ ] Median portfolio trajectory is charted
- [ ] Prudent and favourable trajectories are charted
- [ ] Retirement age is visible on the chart
- [ ] Pension start age is visible on the chart
- [ ] Nominal or inflation-adjusted values are clearly identified

## Out of scope

- Advanced design system
- Saved dashboards
- AI explanations
EOF
)"

create_issue \
  "Add anonymised reference FIRE scenario" \
  "simulation,documentation,personal-value" \
  "$(cat <<'EOF'
## Goal

Add an anonymised reference scenario that demonstrates the first complete FIRE simulation.

## Acceptance criteria

- [ ] A public example scenario exists
- [ ] The scenario contains no real private financial data
- [ ] The example can be executed locally
- [ ] Expected output characteristics are documented
- [ ] The README explains how to run the example

## Privacy

Do not commit actual account balances, salary documents, account numbers or identifiable financial records.
EOF
)"

create_issue \
  "Add GitHub Actions build pipeline" \
  "infrastructure" \
  "$(cat <<'EOF'
## Goal

Add continuous integration for the backend and frontend.

## Acceptance criteria

- [ ] The backend build runs on pull requests
- [ ] Backend tests run on pull requests
- [ ] The frontend build runs on pull requests
- [ ] Frontend lint runs on pull requests
- [ ] Frontend tests run on pull requests
- [ ] The workflow fails when a required step fails

## Out of scope

- Deployment
- Sonar
- Container publication
- Kubernetes
EOF
)"

echo
echo "Initial issues created successfully."
echo
gh issue list "${repo_args[@]}" --limit 20
