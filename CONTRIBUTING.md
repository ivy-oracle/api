# Contributing to Ivy API

This page is a work in progress

## Start dev server

```sh
export spring_profiles_active=local
./mvnw spring-boot:run
```

## Generate contract class from ABI

```sh
web3j generate solidity -o ./src/main/java -p com.ivy.api.contract -a abi/PriceSubmitter.abi
```
