# Contributing to Ivy API

This page is a work in progress

## Development environment setup

- Install Java 17, maven 3.8.6, postgresql 13
- Install project dependencies

  ```sh
  ./mvnw install
  ```

- Create a local application properties file

  Copy and rename [`./application-local-example.properties`](./src/main/resources/application-local-example.properties) as `./application-local.properties`

- Start development server

  ```sh
  export spring_profiles_active=local
  ./mvnw spring-boot:run
  ```

## Generate contract class from ABI

```sh
web3j generate solidity -o ./src/main/java -p com.ivy.api.contract.flare -a abi/flare/PriceSubmitter.abi
```

## Logging

### Configure logging level for individual logger

```properties
# application-local.properties
logging.level.root=INFO
logging.level.com.ivy.api.config.SchedulerConfiguration=DEBUG
```

## Database migration

### Baseline

```
./mvnw flyway:baseline -Dflyway.url=jdbc:postgresql://localhost:5432/ivy-api -Dflyway.user=postgres -Dflyway.password=postgres
```

## Deployment

Build docker image

```sh
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=ivyoracle/spring-boot-api
```

Start docker container locally

```sh
docker run \
  -e "SPRING_PROFILES_ACTIVE=dev" \
  -e "RDS_HOSTNAME=host.docker.internal" \
  -e "RDS_PORT=5432" \
  -e "RDS_DB_NAME=ivy-api" \
  -e "RDS_USERNAME=postgres" \
  -e "RDS_PASSWORD=postgres" \
  -e "SPRING_TASK_SCHEDULING_POOL_SIZE=10" \
  -e "JOB_FTSO_PRICE_ENABLED=true" \
  -e "JOB_DELEGATION_EVENT_ENABLED=true" \
  -e "WEB3_RPC_URL=https://songbird-api.flare.network/ext/bc/C/rpc" \
  --add-host=host.docker.internal:host-gateway \
  -p 8080:5000 \
  -t ivyoracle/spring-boot-api
```
