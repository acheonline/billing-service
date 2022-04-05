


| #   | Имя параметра                       | По умолчанию            | Обязательность | Описание                                            |
|-----|-------------------------------------|-------------------------|:--------------:|-----------------------------------------------------|
| 1   | ACCOUNT_CREATE_TOPIC                | `create`                |      Yes       | Топик, откуда приходит событие на создание аккаунта |
| 2   | KAFKA_CLIENT_ID                     | `billing-service`       |       No       | client.id kafka producer                            |
| 3   | SERVER_PORT                         | `8080`                  |      Yes       | Порт приложения                                     |
| 4   | KAFKA_BOOTSTRAP_SERVERS             | `localhost:9092`        |      Yes       | Хосты Kafka broker                                  |
| 5   | KAFKA_PROTOCOL                      | `PLAINTEXT`             |       No       | Соединение с kafka без шифрования                   |
