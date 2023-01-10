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

### Setup Assembly Plugin

The Maven Assembly Plugin can be configured to read in our assembly configuration at `src/assembly/stub.xml`:

The following is the plugin configuration listing found in `pom.xml`:

```xml
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-assembly-plugin</artifactId>
				<executions>
					<execution>
						<id>stub</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>single</goal>
						</goals>
						<inherited>false</inherited>
						<configuration>
							<attach>true</attach>
							<descriptors>
								${basedir}/src/assembly/stub.xml
							</descriptors>
						</configuration>
					</execution>
				</executions>
			</plugin>
```

Create a directory `src/assembly` and create an exmpty XML file so we can configure the Assembly plugin.

The Configuration for this project looks like the following:

```xml
<assembly
        xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.3"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.3 https://maven.apache.org/xsd/assembly-1.1.3.xsd">
    <id>stubs</id>
    <formats>
        <format>jar</format>
    </formats>
    <includeBaseDirectory>false</includeBaseDirectory>
    <fileSets>
        <fileSet>
            <directory>src/main/java</directory>
            <outputDirectory>/</outputDirectory>
            <includes>
                <include>**com/example/restdoc/*.*</include>
            </includes>
        </fileSet>
        <fileSet>
            <directory>${project.build.directory}/classes</directory>
            <outputDirectory>/</outputDirectory>
            <includes>
                <include>**com/example/restdoc/*.*</include>
            </includes>
        </fileSet>
        <fileSet>
            <directory>${project.build.directory}/snippets/stubs</directory>
            <outputDirectory>META-INF/${project.groupId}/${project.artifactId}/${project.version}/mappings</outputDirectory>
            <includes>
                <include>**/*</include>
            </includes>
        </fileSet>
        <fileSet>
            <directory>${project.build.directory}/snippets/contracts</directory>
            <outputDirectory>META-INF/${project.groupId}/${project.artifactId}/${project.version}/contracts</outputDirectory>
            <includes>
                <include>**/*.groovy</include>
            </includes>
        </fileSet>
    </fileSets>
</assembly>
```

### Connect the restdocs in build

Configure `restdocs` generation in  pom.xml:

```xml
			<plugin>
				<groupId>org.asciidoctor</groupId>
				<artifactId>asciidoctor-maven-plugin</artifactId>
				<version>1.5.8</version>
				<executions>
					<execution>
						<id>generate-docs</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>process-asciidoc</goal>
						</goals>
						<configuration>
							<attributes>
								<snippets>${project.build.directory}/generated-snippets</snippets>
							</attributes>
							<backend>html</backend>
							<doctype>book</doctype>
						</configuration>
					</execution>
				</executions>
				<dependencies>
					<dependency>
						<groupId>org.springframework.restdocs</groupId>
						<artifactId>spring-restdocs-asciidoctor</artifactId>
						<version>${spring-restdocs.version}</version>
					</dependency>
				</dependencies>
			</plugin>
```

### Build the project

Build the full artifact containing WireMock stubs by exeucting:

```bash
mvn clean install
```

The next step is to utilize the generated stub with a client project using [Stub Runner](https://docs.spring.io/spring-cloud-contract/docs/current/reference/html/project-features.html#features-stub-runner).

## Conclusion

Creating artifacts for documentation, and client/service validation is easier than thought.

# Conclusion and Links

[Add Stubs to GIT](https://docs.spring.io/spring-cloud-contract/docs/current/reference/html/howto.html#how-to-use-git-as-storage)