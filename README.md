# ManagerOfPasswordSPRING
Приложение для хранения записей от аккаунтов (Например: эл. почта, соц. сети и прочие сервисы) в рамках компании или квартиры.

## Используемые технологии
### Spring Boot
### Spring Security
### JWT
### Mapstruct
### Lombok
### JsonPatch

## Цель 
Реализовать менеджер паролей для организации. 
Имееются отделы (Department), в которых хранятся ссылки на пользователей (User).
Пользователь может быть только в одном отделе. У приложения имеются роли (Roles - ADMIN и USER)
У администратора имеется отдельный контроллер для редактирования данных отделов, пользователей и записей.
Если при создания пользователя или записи, пароль нулевой, то приложение генерирует сам пароль.

## Модели
![alt text](https://github.com/darkdeaddaset/ManagerOfPasswordSPRING/blob/main/models/department.png)
![alt text](https://github.com/darkdeaddaset/ManagerOfPasswordSPRING/blob/main/models/records.png)
![alt text](https://github.com/darkdeaddaset/ManagerOfPasswordSPRING/blob/main/models/roles.png)
![alt text](https://github.com/darkdeaddaset/ManagerOfPasswordSPRING/blob/main/models/users.png)
