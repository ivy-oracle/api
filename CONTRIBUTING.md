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
web3j generate solidity -o ./src/main/java -p com.ivy.api.contract -a abi/PriceSubmitter.abi
```

## Configure logging level for individual logger

```properties
# application-local.properties
logging.level.root=INFO
logging.level.com.ivy.api.config.SchedulerConfiguration=DEBUG
```
