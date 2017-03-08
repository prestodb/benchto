#!/bin/bash

sleep 20
curl "http://admin:admin@benchto-grafana:3000/api/datasources" \
  -X POST \
  -H 'Content-Type: application/json;charset=UTF-8' \
  --data-binary \
  '{"name":"Benchto graphite","type":"graphite","url":"http://benchto-graphite:80","access":"proxy","isDefault":true, "basicAuth":true, "basicAuthUser":"guest","basicAuthPassword":"guest"}'
