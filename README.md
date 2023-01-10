# Example RESTdoc with Webflux and Spring Cloud Contract

This repository is effectivly the same as [Spring Cloud Contract Samples](https://github.com/spring-cloud-samples/spring-cloud-contract-samples)
that demonstrates `producer_with_restdocs` - although with WebFlux. I was curious how this works from the standpoint of having an 
existing `restdoc`/`webflux` application and needed some idea how generating stubs and contracts could be achieved.

## Documentation

The [documentation](https://docs.spring.io/spring-cloud-contract/docs/current/reference/html/project-features.html#features-stub-runner-publishing-stubs-as-jars) is very helpful to review when implementing this kind of strategy.
I highly recommend giving it a read!

## Code

This is a maven-based application. use the following command to build and generate stubs:

```bash
mvn clean install
```

## Review of Steps to Develop

Lets review the individual steps you might be interested in taking on your own accord.

### Create the project

Starting at [start.spring.io](https://start.spring.io), create an application with the following parameters:

* JVM 17
* Spring 3.0.1 + 
* Maven
* Dependencies
  * RestDoc
  * WebFlux
  * Cloud Contract Verifier

You will need to remove the entire `plugin` section for `spring-cloud-contract-maven-plugin` since we will be using 
restDocs and [Maven Assembly](https://maven.apache.org/plugins/maven-assembly-plugin/) to generate the contracts, 'assemble' our stub
artifacts.

### Create a REST endpoint

Create some `@RestControllers`. Testing comes next.

### Test the REST endpoints

Tests drive creation of RESTDocs, Contracts, and WireMock stubs.

### Build the project

Build the full artifact containing WireMock stubs by exeucting:

```bash
mvn clean install
```

The next step is to utilize the generated stub with a client project using [Stub Runner](https://docs.spring.io/spring-cloud-contract/docs/current/reference/html/project-features.html#features-stub-runner).

## Conclusion

Creating artifacts for documentation, and client/service validation is easier than thought.
