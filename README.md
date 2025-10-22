## Tasks Application

___

### Deploy Dockerized Application:

* check docker is ready: `docker info`

* run `mvn clean compile -f pom.xml`

* run `docker-compose up --build`

* try sending the request:

```bash
curl --location 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--data '{
    "username": "admin",
    "password": "admin"
}'
```
___
