# Challenge app

## Running the app

To run the application: `mvn spring-boot:run`.

Open web browser at `http://localhost:8080/app`.

To run the tests: `mvn test`.

## Performing requests

Test SOAP endpoint with: `curl -d "@src/test/resources/request.xml" -H "Content-type: text/xml" http://localhost:8080/ws/request`.

Test REST endpoint with: `curl -d "@src/test/resources/data.json" -H "Content-Type: application/json" -H "Authorization: Basic YXBpdXNlcjphcGlwd2Q=" -X POST http://localhost:8080/app/nkbirequest`.
