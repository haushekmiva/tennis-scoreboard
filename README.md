# Tennis Scoreboard
Веб-приложение, реализующее табло счёта теннисного матча выполненное по ТЗ из роадмапа [Сергея Жукова](https://zhukovsd.github.io/java-backend-learning-course/projects/tennis-scoreboard/).

<img width="1901" height="907" alt="изображение" src="https://github.com/user-attachments/assets/53eccc8d-2324-4045-92ba-1422263fa18b" />


## Что умеет приложение
- Позволяет создать матч с подсчетом текущих очков для двух игроков
- Посмотреть историю завершенных матчей и выполнить поиск по имени игрока

## Технологии
Backend: Java 17, Servlets, Hibernate 6, HikariCP
Database: H2 (in-memory)
Frontend: JSP, JSTL, CSS

## Запуск проекта

### Требования
- Java 17+
- Maven 3.9+
- Tomcat 11+ (или любой Jakarta EE 10 совместимый сервер)

### Шаги
1. Клонировать репозиторий:
```bash
git clone https://github.com/haushekmiva/tennis-scoreboard.git
cd tennis-scoreboard
```

2. Собрать проект:
```bash
mvn clean package
```

3. Развернуть WAR-файл:
   - Скопировать `target/tennis-scoreboard.war` в `tomcat/webapps/`
   - Запустить Tomcat

4. Открыть в браузере:
```
http://localhost:8080/tennis-scoreboard
```

## Ссылки
- **Live Demo:** http://83.222.24.156:8080/tennis-scoreboard/
- **Roadmap:** [zhukovsd/java-backend-learning-course](https://zhukovsd.github.io/java-backend-learning-course/projects/tennis-scoreboard/)

