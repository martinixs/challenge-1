# Challenge application

## Running

To run the application use `mvn spring-boot:run`.

Open web browser at `http://localhost:8080/app`.

## Testing the endpoints

Test SOAP endpoint with the following command `curl -d "@src/test/resources/request.xml" -H "Content-type: text/xml" http://localhost:8080/ws/request`.

Test REST endpoint with the following command `curl -d "@src/test/resources/data.json" -H "Content-Type: application/json" -H "Authorization: Basic YXBpdXNlcjphcGlwd2Q=" -X POST http://localhost:8080/app/nkbirequest`.
