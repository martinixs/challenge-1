Credential for apiuser:apipwd -> YXBpdXNlcjphcGlwd2Q=

echo -n apiuser:apipwd | base64

Authorization: Basic YXBpdXNlcjphcGlwd2Q=

curl -d "@data.json" -H "Content-Type: application/json" -H "Authorization: Basic YXBpdXNlcjphcGlwd2Q=" -X POST http://localhost:8080/app/nkbirequest
curl -d "@request.xml" -H "Content-type: text/xml" http://localhost:8080/ws/request