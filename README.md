# OAuth2 авторизація

Тут описано, як виконати авторизацію за допомогою Authorization Code Grant з PKCE для отримання `access_token`. Процес включає два основні кроки:
1. Отримання коду авторизації (`authorization_code`).
2. Обмін `authorization_code` на `access_token`.

Можна спробувати створити Authorization Server самому (наприклад на Auth0) або використати мій (не факт що буде працювати, бо я можу його колись видалити або вже видалив - в залежності коли ви це читаєте).

> `AUTH_SERVER_URL=https://dev-d8rm3bg4cwywnjin.eu.auth0.com/`
> 
> `CLIENT_ID=oGI5PXlHArpbmYf5kJXUZfiCPwIp5Eob`

## 1. Генерація `code_verifier` і `code_challenge`

OAuth2 з PKCE (Proof Key for Code Exchange) вимагає використання `code_verifier` та `code_challenge` для забезпечення безпеки при обміні коду авторизації.

### Приклад значень (можна взяти їх або див. нижче)

- **Code Verifier** — P_94ulbnexe0Ra5nZRbsEQrQsAnjrg2kGI_-CACeLrw
- **Code Challenge** — 86IxSOJpfquxZoociE4u2NgOKS1o5rqBIkbf874Lla0

### Приклад коду для генерації `code_verifier` та `code_challenge`

```javascript
function base64URLEncode(buffer) {
    return btoa(String.fromCharCode.apply(null, new Uint8Array(buffer)))
        .replace(/\+/g, '-')
        .replace(/\//g, '_')
        .replace(/=+$/, '');
}

async function sha256(plain) {
    const encoder = new TextEncoder();
    const data = encoder.encode(plain);
    return await crypto.subtle.digest('SHA-256', data);
}

async function generatePKCECodes() {
    const array = new Uint32Array(32);
    window.crypto.getRandomValues(array);
    const codeVerifier = base64URLEncode(array);
    const hashed = await sha256(codeVerifier);
    const codeChallenge = base64URLEncode(hashed);
    
    console.log("Code Verifier:", codeVerifier);
    console.log("Code Challenge:", codeChallenge);
}

generatePKCECodes();
```

- **Code Verifier** — це випадковий рядок, згенерований для кожного запиту на авторизацію.
- **Code Challenge** — це хешоване значення `code_verifier`, яке надсилається разом із запитом на отримання `authorization_code`.

> Можна запустити цей скрипт у веб-браузері, щоб згенерувати `code_verifier` і `code_challenge`.

## 2. Отримання `authorization_code`

Треба виконати наступний запит, щоб отримати `authorization_code`. Цей запит перенаправляє користувача на сторінку авторизації, де він зможе увійти та дати дозвіл, тому його треба вставити у браузер.

`https://AUTH_SERVER_DOMAIN/authorize?response_type=code&client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&scope=openid profile email&code_challenge=CODE_CHALLENGE&code_challenge_method=S256`

- `AUTH_SERVER_DOMAIN` — лінка на ваш авторизаційний сервер (на лекції показував звідки витянути).
- `response_type=code` — визначає, що запит призначений для отримання коду авторизації.
- `client_id=CLIENT_ID` — ідентифікатор клієнта, виданий авторизаційним сервером.
- `redirect_uri=REDIRECT_URI` — URI, куди буде повернено `authorization_code` (достатньо взяти ось це значення:`http://localhost/callback`).
- `scope=openid profile email` — запитувані права доступу (стандартно без змін).
- `code_challenge=CODE_CHALLENGE` — значення, згенероване з `code_verifier`.
- `code_challenge_method=S256` — метод для створення `code_challenge` (SHA-256).

> **!!!** Треба замінити `AUTH_SERVER_DOMAIN`, `CLIENT_ID`, `REDIRECT_URI` та `CODE_CHALLENGE` на відповідні значення.

> Після того як перейшли на цю лінку, в браузері вас перенаправить на REDIRECT_URI, де буде `code` параметр, 
> який потрібен в наступному кроці (щось типу такого: `http://localhost/callback?code=S-gz1-Ap95iTf_toXIQu9b8wiom3muy4QjCMdTFaSTee4`).
> Якщо після цього всього (коли вже авторизувались через якусь мережу чи логін/пароль) у вас напише помилку `This site can’t be reached` чи щось типу того,
> то це нормально, вам головне знайти зверху де лінка той код

## 3. Обмін `authorization_code` на `access_token`

Після отримання `authorization_code`, треба виконати запит для обміну коду на `access_token`.

**Команда cURL для обміну `authorization_code` на `access_token` (можна в терміналі або в Postman)**:
```bash
curl -X POST "https://AUTH_SERVER_DOMAIN/oauth/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=authorization_code&client_id=CLIENT_ID&code=AUTHORIZATION_CODE&redirect_uri=REDIRECT_URI&code_verifier=CODE_VERIFIER"
```

- `grant_type=authorization_code` — вказує на використання Authorization Code Grant.
- `client_id=CLIENT_ID` — ідентифікатор клієнта.
- `code=AUTHORIZATION_CODE` — код авторизації, отриманий на попередньому кроці.
- `redirect_uri=REDIRECT_URI` — URI, на який був повернутий `authorization_code`.
- `code_verifier=CODE_VERIFIER` — оригінальний код, використаний для генерації `code_challenge`.

### Приклад відповіді:
Після успішного виконання запиту авторизаційний сервер поверне `access_token`:
```json
{
  "access_token": "ACCESS_TOKEN",
  "token_type": "Bearer",
  "expires_in": 86400
}
```

Тепер можна використовувати `access_token`, додаючи його до хедера `Authorization` у запитах до ресурсного сервера:
```plaintext
Authorization: Bearer ACCESS_TOKEN
```

---
