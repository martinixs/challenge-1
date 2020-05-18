# Challenge application

## Running

To run the application use `> mvn spring-boot:run`

Link to UI
    http://localhost:8080/app

## Example requests

Use command for testing SOAP endpoint
    curl -d "@request.xml" -H "Content-type: text/xml" http://localhost:8080/ws/request

Use command for testing REST request
    curl -d "@data.json" -H "Content-Type: application/json" -H "Authorization: Basic YXBpdXNlcjphcGlwd2Q=" -X POST http://localhost:8080/app/nkbirequest

## Credentials for REST API:
User name : apiuser
User password: apipwd
Base64 encode access token: YXBpdXNlcjphcGlwd2Q=



