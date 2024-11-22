
# WordQuest Server API
## Описание
Сервер для мобильного приложения WordQuest, предоставляющий API для регистрации, авторизации пользователей и получения слов.


## Методы запросов
| Метод |Описание  |
|--|--|
| `GET` | Используется для получения объекта или коллекции объектов |
|`POST`|Используется для создания нового объекта, пользователя и т.д.|
|`PATCH`|Используется для обновления одного и более полей объекта, например, почты пользователя|
|`PUT`|Используется для замены целого объекта (всех полей) новыми данными|
|`DELETE`|Используется для удаления объекта|

## Endpoints
| **Метод** |**URL**| **Описание** |  
|-------|---|---------|  
| `POST`  |`/api/users/login`|Авторизация пользователя, проверка наличия в базе данных|  
| `POST`  |`/api/users/register`|Регистрация пользователя, добавление в базу данных|
|`POST` |	`/api/word/data/new`|	Получение случайного нового слова, исключая определенные ID|
|`GET` |	`/api/word/data/{word}`|	Обращение к стороннему API. Получение данных по слову параметру и запись в базу данных|
|`GET` |	`/api/word/data/translation`|	Обращение к стороннему API. Получение перевода данных в базе данных и запись в базу данных|

### Метод /api/user/login
#### HTTP-запрос
```
POST http://127.0.0.1:8080/api/users/login
```
#### Параметры в теле запроса
```
{
    "username":  "string",
    "password_hash":  "string"
}
```
|Поле|Описание  |
|--|--|
| username | **string** Обязательное поле. Логин пользователя. Максимальная длина  — 50 символов.|
|password_hash|**string** Обязательное поле. Пароль пользователя. Максимальная длина — 255 символов.|
#### Ответ
##### **HTTP Code: 200 - OK**
```json
    "message": "User successfully logged in."
```
##### **HTTP Code: 400 - Bad Request**
```json
    "error": "User not found"
```

### Метод /api/user/register
#### HTTP-запрос
```
POST http://127.0.0.1:8080/api/users/register
```
#### Параметры в теле запроса
```
{
    "username":  "string",
    "password_hash":  "string",
    "email":  "string"
}
```
|Поле|Описание  |
|--|--|
| username | **string** Обязательное поле. Логин пользователя. Максимальная длина строки в символах 50.|
|password_hash|**string** Обязательное поле. Пароль пользователя. Максимальная длина строки в символах 255.|
|email|**string** Обязательное поле. Почта пользователя. Максимальная длина строки в символах 100.|

#### Ответ
##### **HTTP Code: 201 - Created**
```json
    "message": "User registered successfully"
```
##### **HTTP Code: 400 - Bad Request**

```json
    "error": "Invalid input data"
```
##### **HTTP Code: 401 - Unauthorized**
```json
    "error": "Invalid password"
```
##### **HTTP Code: 409 - Conflict**
```json
    "error": "User with this username already exists"
```
##### **HTTP Code: 500 - Internal Server Error**
### Метод /api/word/data/new
#### HTTP-запрос
```
POST http://127.0.0.1:8080/api/word/data/new
```
#### Параметры в теле запроса
```
{
    "ids": [1, 2]
}
```
|Поле|Описание  |
|--|--|
| username | **ids** Обязательное поле. List of integers Список ID слов, которые необходимо исключить из поиска.|
#### Ответ
##### **HTTP Code: 200 - OK**
###### Body
```json
{
    "wordId": "int",
    "wordText": "string",
    "audioUrl": "string",
    "translation": "string",
    "definition": ["string", "string"],
    "definitionTranslation": ["string", "string"],
    "exampleSentences": ["string", "string"],
    "exampleSentenceTranslations": ["string", "string"]
}
```
###### Example
```json
{
  "wordId": 7,
  "wordText": "hello",
  "audioUrl": "https://example.com/audio/hello.mp3",
  "translation": "Привет",
  "definition": [
    "Greeting used to say hello",
    "Used when answering the phone"
  ],
  "definitionTranslation": [
    "Приветствие, используемое для того, чтобы сказать 'привет'",
    "Используется при ответе на телефонный звонок"
  ],
  "exampleSentences": [
    "Hello, how are you?",
    "Hello, my name is John."
  ],
  "exampleSentenceTranslations": [
    "Привет, как дела?",
    "Привет, меня зовут Джон."
  ]
}
```
##### **HTTP Code: 400 - Bad Request**
```json
    "error": "ERROR"
```

### Метод /api/word/data/{word}
#### HTTP-запрос
```
GET http://127.0.0.1:8080/api/word/data/{word}
```
|Параметр|Описание  |
|--|--|
| `{word}` | **{word}** Обязательный параметр. Слово по которомму находиться иинформация.|
#### Ответ
##### **HTTP Code: 201 - Created**
##### **HTTP Code: 400 - Bad Request**
```json
    "error": "No word specified"
```
### Метод /api/word/data/translation
#### HTTP-запрос
```
GET http://127.0.0.1:8080/api/word/data/translation
```
#### Ответ
##### **HTTP Code: 201 - Created**
##### **HTTP Code: 400 - Bad Request**
```json
    "error": "ERROR"
```