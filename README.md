# gestion-usuarios

  * Java 8
  * SpringBoot 2.7.16
  * 
## Postman Collection

### Sign Up

- **Endpoint:** `POST - http://localhost:8081/users/sign-up`

**Request Body:**

```json
{
  "name": "Ejemplo Usuario",
  "email": "usuario51@example.com",
  "password": "Aa123456789",
  "phones": [
    {
      "number": "123456789",
      "cityCode": "123",
      "countryCode": "+3"
    },
    {
      "number": "987654321",
      "cityCode": "456",
      "countryCode": "+12"
    }
  ]
}

```

**Response Body:**

```json
{
  "id": "c195bbf0-4801-4f9f-bcec-545a174e519c",
  "created": "2023-10-02T14:30:00",
  "lastLogin": "2023-10-02T14:45:00",
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJnZXN0aW9uLXVzdWFyaW9zIiwiVXNlcklkIjoiYzE5NWJiZjAtNDgw1b5FnW0e/2H9Nshlf2LeahrsQKJHxTFH3w==.Rwb_RW75mCZ-6e-gKcnS4x5qONEnlAhZJ6fRszAJ69mR7jri-1bdqf9AwVGwMeJD7Ib49houe8WDi66SdJIkpA",
  "active": true
}
```

### Login

- **Endpoint:** `GET - http://localhost:8081/users/login`

**Request Headers:**

**Request Headers:**

- Authorization:

- bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJnZXN0aW9uLXVzdWFyaW9zIiwiVXNlcklkIjoiYzE5NWJiZjAtNDgwMS00ZjlmLWJjZWMtNTQ1YTE3NGU1MTljIiwiaWF0IjoxNjk4MTM1MDI5LCJleHAiOjE2OTgxMzg2Mjl9.Rwb_RW75mCZ-6e-gKcnS4x5qONEnlAhZJ6fRszAJ69mR7jri-1bdqf9AwVGwMeJD7Ib49houe8WDi66SdJIkpA


**Request Body:**

```json
{
    "id": "c195bbf0-4801-4f9f-bcec-545a174e519c",
    "created": "2023-10-02T14:30:00",
    "lastLogin": "2023-10-24T05:10:41.4646501",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJnZXN0aW9uLXVzdWFyaW9zIiwiVXNlcklkIjoiYzE5NWJiZjAtNDgwMS00ZjlmLWJjZWMtNTQ1YTE3NGU1MTljIiwiaWF0IjoxNjk4MTM1MDQxLCJleHAiOjE2OTgxMzg2NDF9.Id5DPwrvvcyiqISoQIRPIvfQp--jvBnC6kP1jNJyTBLsJ_YRkSSWjSsgiu7uIzTP9ET9sB2PDuI4148iw7BbxQ",
    "name": "Ejemplo Usuario",
    "email": "usuario@example.com",
    "password": "$2a$10$KBw3KThWFyFLiYwCqmJ3D.ma829Q401s7YjjPm5lVIf5iTvKgyRlu",
    "phones": [
        {
            "number": 123456789,
            "cityCode": 123,
            "countryCode": "+1"
        },
        {
            "number": 987654321,
            "cityCode": 456,
            "countryCode": "+1"
        }
    ],
    "active": true
}
```
