# Spring Boot Demo

Einfache Spring Boot Web Application um Kurse und Studenten zu verwalten.

## Vorraussetzungen:

* PostgreSQL DB
* Java 11+
* gradle

## Datenbankverbindung:

Zugangsdaten hinzufügen unter: `src/main/resources/application.properties`:

```
## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Default: User `postgres`, Password `postgres`, DB `postgres`

Starten z.B. als Container (podman, docker, ...):
```
docker run --rm -it -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB= postgres:latest
```

## Starten des Webservices

```
gradle bootRun
```

## Ausprobieren


Einen neuen Studiengang hinzufügen:
```
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/courses --data '{
  "title": "Software Engineering",
  "description": "Eine spassige Sache!"
}'
```

Response:

```
{
  "createdAt": "2019-10-01T13:24:43.442+0000",
  "updatedAt": "2019-10-01T13:24:43.442+0000",
  "id": 1001,
  "title": "Software Engineering",
  "description": "Eine spassige Sache!"
}
```

Alle Studiengaegne anzeigen:

```
curl -H 'Content-Type: application/json' -i http://localhost:8080/courses
```

## Kontakt

* Christian Bargmann <christian.bargmann@haw-hamburg.de>