# Financial Copilot — Frontend

Angular 22 application (standalone components, no NgModules).

## Prerequisites

* Node.js 22.22.3+ (or 24.15+) — the Angular CLI enforces this minimum.
  A `.nvmrc` is provided; if you use [nvm](https://github.com/nvm-sh/nvm),
  run:
  ```bash
  cd frontend
  nvm install
  nvm use
  ```
* The backend running locally on port `8666` (see `backend/README.md` / the
  root `README.md`)

## Install dependencies

```bash
cd frontend
npm install
```

## Run

```bash
cd frontend
npm start
```

The application starts on `http://localhost:4200`. API calls to `/api/*` are
proxied to the backend on `http://localhost:8666` (see `proxy.conf.json`).

## Test

```bash
cd frontend
npm test
```

## Build

```bash
cd frontend
npm run build
```

Build artifacts are output to `dist/frontend`.
