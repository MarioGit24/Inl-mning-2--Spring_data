# Fordonskundhantering (Spring Boot REST API)

Detta är ett REST API byggt med Spring Boot 3 för att hantera kunder och deras fordon. Applikationen använder en H2 in-memory databas, vilket innebär att all data raderas vid varje omstart.

## 🛠️ Förutsättningar

* Java Development Kit (JDK) 17+
* Apache Maven
* En REST-klient för testning (t.ex. Insomnia, Postman, eller cURL)

## 🚀 Kompilering och Körning

1.  Öppna din terminal i projektets rotkatalog.
2.  Kör applikationen med Spring Boot Maven plugin:
    ```bash
    ./mvnw spring-boot:run
    ```
Applikationen startar på `http://localhost:8080`.

## ⚙️ REST API Endpoints

Bas-URL: `http://localhost:8080`

### POST Endpoints (Skapa/Ändra data)

| Syfte | Metod | Endpoint | Parametrar |
| :--- | :--- | :--- | :--- |
| **Skapa Kund** | `POST` | `/customers` | `name`, `phoneNumber` |
| **Skapa Fordon** | `POST` | `/vehicles` | `registrationNumber`, `brand`, `model`, `productionYear` |
| **Länka Fordon till Kund** | `POST` | `/customers/{customerId}/vehicles/{vehicleId}` | **Path Variables** (`customerId`, `vehicleId`) |

**Exempel på POST-anrop (för att skapa data):**

1.  **Skapa Kund:** `POST http://localhost:8080/customers?name=Anna%20Svensson&phoneNumber=123456789`
2.  **Skapa Fordon:** `POST http://localhost:8080/vehicles?registrationNumber=ABC123&brand=Volvo&model=V70&productionYear=2024`
3.  **Länka (måste köras sist):** `POST http://localhost:8080/customers/1/vehicles/1`

### GET Endpoints (Hämta data)

| Syfte | Metod | Endpoint | Parametrar |
| :--- | :--- | :--- | :--- |
| **Lista Alla Kunder + Fordon** | `GET` | `/customers`
| **Hämta Kund-ID** | `GET` | `/customers-id` | `name` |
| **Lista Alla Fordon** | `GET` | `/vehicles`
| **Lista Fordon efter Märke** | `GET` | `/vehicles-by-brand` | `brand` |

**Exempel på GET-anrop:**

* **Lista Kunder:** `GET http://localhost:8080/customers`
* **Hämta ID:** `GET http://localhost:8080/customers-id?name=Anna%20Svensson`
* **Lista Fordon:** `GET http://localhost:8080/vehicles-by-brand?brand=Volvo`
