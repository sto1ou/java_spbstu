## Tasks Application

___

### Deploy Dockerized Application:

If they are not installed yet, download and install them:

Java 17 or later: https://adoptium.net

Apache Maven: https://maven.apache.org/download.cgi

After installation, verify the versions in the terminal:

* run `java -version`
* run `mvn -v`


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
