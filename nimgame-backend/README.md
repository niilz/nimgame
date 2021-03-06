# NimGame - API

### Preconditions

Java (JDK) version 18

### Build Instructions

Use the Maven-Wrapper (UNIX: mvnw | Windows: mvnw.bat)

- Run Test-Suite: `./mvnw clean test`
- Compiling runnable artefact: `./mvnw clean package -DskipTests` or `./mvnw clean package` if test-suite should be run aswell

### Execution Instructions

- Compile Sources (see _Build Instructions_) & Execute/Launch NimGame-Api: `java -jar target/nimgame-0.0.1-SNAPSHOT.jar`
- Alternatively execute/launch precompiled NimGame-Api: `java -jar ./nimgame-0.0.1-SNAPSHOT.jar`
- Stop Application with **<Ctrl-C>**

### Api-Specification

- Launch Application (see _Execution Instructions_)
- Visit [OpenAPI URL: http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- Alternatively import `./NimGame_API-Definition.postman_collection.json` into Postman
