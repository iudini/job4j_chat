[![Build Status](https://app.travis-ci.com/iudini/job4j_chat.svg?branch=main)](https://app.travis-ci.com/iudini/job4j_chat)

# REST API Chat

### Технологии:

- Spring Boot (Web, Data, Security)
- REST API
- JWT
- PostgreSQL 
- Maven

### Сборка приложения
 - Для сборки приложения на вашем компьютере должны быть установлены:
    - JDK 14+
    - Maven
    - PostgreSQL
 - Скачайте проект к себе на компьютер с помощью команды `git clone https://github.com/iudini/job4j_chat`
 - В PostgreSQL создайте базу с именем "chat"
 - Укажите настройки для подключения к БД в файле `src/main/resources/application.properties`
 - Выполните скрипт `db/schema.sql`
 - Выполните команду `mvn install`
 - Далее `java -jar target/chat-0.0.1-SNAPSHOT.jar`

Адрес сервера по умолчанию: http://localhost:8080/

Адреса /sign-up и /login доступны без аутентификации.

### Регистрация:
```
POST http://localhost:8080/users/sign-up/
Content-Type: application/json
Request body: {"username":"username", "password":"password"}
```
### Аутентификация:
По умолчанию в системе зарегистрирован пользователь "admin:admin", роль "ROLE_ADMIN"
```
POST http://localhost:8080/users/login/  
Header: Content-Type: application/json  
Request body: {"username":"username", "password":"password"}
```
```
Response header: Authorization: Bearer ___jwtToken___
```
### JWT авторизация:
Все остальные запросы должны быть авторизованы, для этого в ваши запросы необходимо добавить
Header полученный после входа
```
Request header: Authorization: Bearer ___jwtToken___
```

### Запросы к "/users"
Получить список всех пользователей, только ROLE_ADMIN
```
GET http://localhost:8080/users/all/  
Header: Authorization: Bearer ___jwtToken___
```

Получить пользователя по id, только ROLE_ADMIN
```
GET http://localhost:8080/users/100
Header: Authorization: Bearer ___jwtToken___
```

Обновить пароль
```
PUT http://localhost:8080/users/update/  
Header: Authorization: Bearer ___jwtToken___
Header: Content-Type: application/json
Request body: {"username":"user", "password":"newPassword"}
```

Удалить пользователя по id, только ROLE_ADMIN
```
DELETE http://localhost:8080/users/delete/100
Header: Authorization: Bearer ___jwtToken___
```

### Запросы к "/role"
только ROLE_ADMIN

Получить список всех ролей
```
GET http://localhost:8080/role/  
Header: Authorization: Bearer ___jwtToken___
```

Получить роль по id
```
GET http://localhost:8080/role/100
Header: Authorization: Bearer ___jwtToken___
```

Добавить роль, используйте шаблон "ROLE_" + имя роли
```
POST http://localhost:8080/role/100
Header: Authorization: Bearer ___jwtToken___
Header: Content-Type: application/json
Request body: {"name":"ROLE_NAME"}
```

Обновить имя роли, используйте шаблон "ROLE_" + имя роли
```
PUT http://localhost:8080/role/  
Header: Authorization: Bearer ___jwtToken___
Header: Content-Type: application/json
Request body: {"id":"100", "name":"name"}
```

Удалить роль по id
```
DELETE http://localhost:8080/role/100
Header: Authorization: Bearer ___jwtToken___
```

### Запросы к "/room"
Получить список всех комнат
```
GET http://localhost:8080/room/  
Header: Authorization: Bearer ___jwtToken___
```

Получить комнату по id
```
GET http://localhost:8080/room/100
Header: Authorization: Bearer ___jwtToken___
```

Добавить комнату
```
POST http://localhost:8080/room/
Header: Authorization: Bearer ___jwtToken___
Header: Content-Type: application/json
Request body: {"name":"name"}
```

Обновить имя комнаты, только ROLE_ADMIN
```
PUT http://localhost:8080/room/  
Header: Authorization: Bearer ___jwtToken___
Header: Content-Type: application/json
Request body: {"id":"1", "name":"name"}
```

Удалить комнату по id, только ROLE_ADMIN
```
DELETE http://localhost:8080/room/100
Header: Authorization: Bearer ___jwtToken___
```

### Запросы к "/message"
Получить список всех сообщений
```
GET http://localhost:8080/message/  
Header: Authorization: Bearer ___jwtToken___
```

Получить сообщение по id
```
GET http://localhost:8080/message/100
Header: Authorization: Bearer ___jwtToken___
```

Добавить сообщение
```
POST http://localhost:8080/message/
Header: Authorization: Bearer ___jwtToken___
Header: Content-Type: application/json
Request body: 
{
    "content":"your message",
    "person":{
        "id":1
    },
    "room":{
        "id":1
    }
}
```

Обновить сообщение
```
PUT http://localhost:8080/message/  
Header: Authorization: Bearer ___jwtToken___
Header: Content-Type: application/json
Request body:
{
    "id":100,
    "content":"your message",
    "person":{
        "id":1
    },
    "room":{
        "id":1
    }
}
```

Удалить сообщение по id
```
DELETE http://localhost:8080/message/100
Header: Authorization: Bearer ___jwtToken___
```
