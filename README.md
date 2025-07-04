# 📘 Address Book API

A modular, production-ready Spring Boot-based RESTful Address Book service that supports:

- 📥 Create multiple contacts
- 📝 Update contact details
- ❌ Delete contacts by ID
- 🔍 Search contacts by name or email (O(1) indexed search)

---

## 📦 Tech Stack

- Java 23
- Spring Boot
- Maven
- Lombok
- In-memory data storage (`ConcurrentHashMap`)
- JDK-internal concurrent utilities

---

## 🚀 Getting Started

### 1️⃣ Prerequisites

Ensure the following tools are installed:

| Tool      | Version   | Notes                                 |
|-----------|-----------|---------------------------------------|
| Java JDK  | 17, 21, or 23 | ✅ Java 17+ is required               |
| Maven     | 3.8+      | [Download](https://maven.apache.org/) |
| IntelliJ  | Any       | ✅ Enable Lombok plugin in settings    |

---

### 2️⃣ Setup Instructions

```bash
# 1. Clone the repository
git clone https://github.com/abhishek-0106/address-book


# 2. Verify Maven is working
mvn -v

# 3. Build the project
mvn clean install

# 4. Run the application
mvn spring-boot:run
By default, the application will start at:
📍 http://localhost:5000

📡 API Contracts
All APIs accept and return JSON.


1. ✅ Create Contacts
Endpoint: /create

Method: POST

Description: Create one or more contacts.

Request :

[
  {
    "name": "Alice Smith",
    "phone": "1234567890",
    "email": "alice@example.com"
  },
  {
    "name": "Bob Jones",
    "phone": "2345678901",
    "email": "bob@example.com"
  }
]

Response :
[
  {
    "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "name": "Alice Smith",
    "phone": "1234567890",
    "email": "alice@example.com"
  },
  {
    "id": "e3b0c442-98fc-1c14-9af5-abc12d3e4d59",
    "name": "Bob Jones",
    "phone": "2345678901",
    "email": "bob@example.com"
  }
]

2. ✏️ Update Contacts
Endpoint: /update

Method: PUT

Description: Update existing contact(s) using ID.

Request:
[
    {
        "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
        "phone": "9999999999"
    },
    {
        "id": "e3b0c442-98fc-1c14-9af5-abc12d3e4d59",
        "email": "newbob@example.com"
    }
]

Response:
[
    {
        "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
        "name": "Alice Smith",
        "phone": "9999999999",
        "email": "alice@example.com"
    },
    {
        "id": "e3b0c442-98fc-1c14-9af5-abc12d3e4d59",
        "name": "Bob Jones",
        "phone": "2345678901",
        "email": "newbob@example.com"
    }
]

3. ❌ Delete Contacts
Endpoint: /delete

Method: DELETE

Description: Delete contact(s) by their ID.

Request:
[
  "f47ac10b-58cc-4372-a567-0e02b2c3d479",
  "e3b0c442-98fc-1c14-9af5-abc12d3e4d59"
]
Response:
{
  "deleted": 2
}

4. 🔍 Search Contacts
Endpoint: /search

Method: POST

Description: Search contacts by name or email.

Request:
{
  "query": "alice"
}

Response:
[
  {
    "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "name": "Alice M. Smith",
    "phone": "1234567899",
    "email": "alice.new@example.com"
  }
]
🧪 Testing API
Use tools like:

✅ Postman

✅ curl

Example:

bash: 
curl -X POST http://localhost:5000/search \
     -H "Content-Type: application/json" \
     -d '{"query": "alice"}'