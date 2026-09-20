# Secret configuration

The backend and mailing service read credentials from environment variables. No real credentials belong in source files, frontend bundles, examples, or deployment manifests.

## Local development (PowerShell)

In each service directory, copy the template and fill in every empty value with new credentials:

```powershell
Copy-Item secrets.example.json secrets.local.json
python setEnv.py mvn.cmd spring-boot:run
```

The launcher passes the JSON values only to the child process. It does not print values or change the parent terminal environment. `secrets.local.json` is ignored by Git; keep it private. Alternatively, supply environment variables directly and run Maven normally. Spring Boot does not automatically load `.env` files.

Backend variables: `MYSQL_URL`, `MYSQL_USERNAME`, `MYSQL_PASSWORD`, `REDISHOST`, `REDISPORT`, `REDISUSER`, `REDISPASSWORD`, `STRIPE_SECRET`, `JWT_SECRET`, `RABBITMQ_HOST`, `RABBITMQ_USERNAME`, `RABBITMQ_PASSWORD`, `RABBITMQ_PORT`, `RABBITMQ_VHOST`.

Mailing variables: `GMAIL_USERNAME`, `GMAIL_PASSWORD`, and the same RabbitMQ variables. Use a Gmail app password. The two services can use separate RabbitMQ accounts with appropriate permissions.

`MYSQL_URL` must be a JDBC URL without embedded credentials. Port defaults are Redis 6379 and RabbitMQ 5672; set the actual hosted-service ports explicitly. RabbitMQ virtual host defaults to `/`.

Generate a new random JWT secret locally (at least 32 bytes); use the same value across backend replicas. For example, put the following generated value directly into your private JSON configuration:

```powershell
python -c "import secrets; print(secrets.token_urlsafe(48))"
```

Missing required variables prevent normal application startup. Blank or short JWT secrets are rejected when the security filter chain is constructed. Changing the JWT secret invalidates existing sessions.

## Deployment

Inject variables from your deployment platform's secret store. The backend Kubernetes deployment references an existing Secret named `tfipcarcare-backend` via `envFrom`. Provision that Secret in the same namespace with the backend variable names above before deploying. Never commit a populated Kubernetes Secret, even if its values are base64 encoded.

## Previously exposed credentials

Rotate/revoke the old MySQL, Redis, RabbitMQ, Gmail app password, Stripe secret key, and JWT signing key. Update all deployed services with the replacements. Existing Git history and previously built images/artifacts may still contain old values. Previous logs may contain Stripe keys, JWTs, and password hashes. Treat these as exposed; restricting or removing old artifacts/logs does not replace credential rotation. This change does not rewrite Git history or rotate provider credentials.
