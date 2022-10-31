## АРМ контороля посещений учащихся

---

### Запуск приложения

`
java -jar 'filename'.jar
`
---

### [Пример Basic авторизации]( https://efim360.ru/rfc-2617-http-autentifikatsiya-bazovaya-i-daydzhest-autentifikatsiya/ )

---

### Описание изменений системы:

1)
    * Монолитный проект для взаимодействия со [СКУД "СИГУР"](https://sigur.com/)
    * [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
      , [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.2/reference/htmlsingle/#web)
      , [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.2/reference/htmlsingle/#data.sql.jpa-and-spring-data)
      , [Lombok](https://projectlombok.org/)
    * Добавлена база H2
    * [Добавлена BASIC Авторизация]( https://wp-kama.ru/handbook/rest/basic/authentication/basic)
    * Добавлен SWAGGER
    * Добавлены тесты и кэширование
2)
    * Удалил Hateoas и кэширование
    * Добавлен контроллер Сигур'а
    * Добавлена БД PostgreSQL, H2 перенесена в тесты
    * Удален Spring Actuator

---
~~### Работа с базой H2~~ (Неактуально)

#### Получить доступ ка базе H2

1) Запустить приложение
2) [http://localhost:8081/h2-console]()
3) (URL) => jdbc:h2:mem:voting

--- 

### SWAGGER

* http://localhost:8081/swagger-ui.html
* http://localhost:8081/v3/api-docs

---