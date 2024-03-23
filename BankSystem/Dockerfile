# Используем базовый образ с Java
FROM openjdk:17

# Копируем JAR-файл в контейнер
COPY target/BankSystem-1.0-SNAPSHOT-jar-with-dependencies.jar bank.jar

# Запускаем приложение при старте контейнера
ENTRYPOINT ["java", "-jar", "/bank.jar"]