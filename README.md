# SmartBankingBackend

# SmartBankingBackend

A RESTful banking backend built with **Spring Boot** and **MySQL**. It exposes APIs for the core operations of a digital banking system: managing users, bank accounts, transactions (deposit / withdraw / transfer), digital wallets, beneficiaries, and scheduled payments.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen)
![Build](https://img.shields.io/badge/Build-Maven-blue)
![Database](https://img.shields.io/badge/Database-MySQL-blue)

---

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Data Model](#data-model)
- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [Getting Started](#getting-started)
- [API Reference](#api-reference)
- [Business Rules & Validations](#business-rules--validations)
- [Example Requests](#example-requests)
- [Notes & Limitations](#notes--limitations)
- [License](#license)

---

## Overview

SmartBankingBackend is a Spring Boot REST API organized in a classic layered architecture
(**Controller → Service → Repository → Entity**). It uses Spring Data JPA / Hibernate for
persistence against a MySQL database, and Lombok to reduce boilerplate in the entity classes.

The base package is `com.sbb.api` and the application entry point is
`SmartBankingBackendApplication`. All endpoints are served under the `/api` path prefix.

## Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 21 |
| Framework | Spring Boot 4.1.0 |
| Web | `spring-boot-starter-web` (Spring MVC, REST) |
| Persistence | `spring-boot-starter-data-jpa` (Hibernate) |
| Database | MySQL (`mysql-connector-j`) |
| Boilerplate | Project Lombok |
| Dev tooling | `spring-boot-devtools` |
| Testing | `spring-boot-starter-test` |
| Build | Maven (Maven Wrapper included: `mvnw` / `mvnw.cmd`) |

> Versions are taken directly from `pom.xml`. The Spring Boot version is inherited from
> `spring-boot-starter-parent` (4.1.0) and the Java version is set via `java.version=21`.

## Features

- **User Management** – Create, read, update, and delete bank customers.
- **Account Management** – Open accounts linked to a user, look them up by ID or account
  number, check balances, update details, and activate/deactivate accounts.
- **Transactions** – Deposit, withdraw, and transfer money between accounts, with balance,
  limit, and minimum-balance validation. Every transaction is recorded with a unique
  reference, type, status, and timestamp.
- **Wallet** – Each user can have one digital wallet. Supports add money, withdraw, and
  wallet-to-wallet transfers with active-status and limit checks.
- **Beneficiaries** – Save and manage payees (beneficiaries) per user.
- **Scheduled Payments** – Create and manage recurring/scheduled payments per user, with
  activate/deactivate support and an auto-generated payment reference.

## Architecture

```
Client (HTTP/JSON)
        │
        ▼
  Controller layer        (@RestController — REST endpoints under /api/**)
        │
        ▼
  Service layer           (@Service — business logic & validation)
        │
        ▼
  Repository layer (DAO)   (Spring Data JPA — JpaRepository interfaces)
        │
        ▼
  Entity layer            (@Entity — JPA-mapped domain models)
        │
        ▼
     MySQL database
```

## Project Structure

```
SmartBankingBackend-main/
├── pom.xml
├── mvnw / mvnw.cmd                 # Maven wrapper
├── .mvn/wrapper/maven-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/com/sbb/api/
│   │   │   ├── SmartBankingBackendApplication.java   # Main entry point
│   │   │   ├── controller/
│   │   │   │   ├── AccountController.java
│   │   │   │   ├── BeneficiaryController.java
│   │   │   │   ├── ScheduledPaymentController.java
│   │   │   │   ├── TransactionController.java
│   │   │   │   ├── UserController.java
│   │   │   │   └── WalletController.java
│   │   │   ├── service/
│   │   │   │   ├── AccountService.java
│   │   │   │   ├── BeneficiaryService.java
│   │   │   │   ├── ScheduledPaymentService.java
│   │   │   │   ├── TransactionService.java
│   │   │   │   ├── UserService.java
│   │   │   │   └── WalletService.java
│   │   │   ├── dao/                # Spring Data JPA repositories
│   │   │   │   ├── AccountRepository.java
│   │   │   │   ├── BeneficiaryRepository.java
│   │   │   │   ├── ScheduledPaymentRepository.java
│   │   │   │   ├── TransactionRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── WalletRepository.java
│   │   │   └── entity/
│   │   │       ├── Account.java
│   │   │       ├── Beneficiary.java
│   │   │       ├── ScheduledPayment.java
│   │   │       ├── Transaction.java
│   │   │       ├── User.java
│   │   │       └── Wallet.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/sbb/api/
│           └── SmartBankingBackendApplicationTests.java
```

## Data Model

All entities use an auto-generated `Long id` (`@GeneratedValue(strategy = IDENTITY)`).

### User
| Field | Type |
|-------|------|
| id | Long |
| firstName | String |
| lastName | String |
| email | String |
| phoneNumber | String |
| address | String |
| city | String |
| state | String |
| pincode | String |
| customerId | String |
| dateOfBirth | LocalDate |
| active | Boolean |
| createdAt | LocalDateTime |
| updatedAt | LocalDateTime |

**Relationships:** One-to-Many → `Account`, `Beneficiary`, `ScheduledPayment`; One-to-One → `Wallet` (cascade ALL, orphan removal).

### Account
| Field | Type |
|-------|------|
| id | Long |
| accountNumber | String |
| accountType | String |
| balance | BigDecimal |
| minimumBalance | BigDecimal |
| dailyTransferLimit | BigDecimal |
| withdrawalLimit | BigDecimal |
| active | Boolean |
| branchName | String |
| ifscCode | String |
| openedAt | LocalDateTime |
| updatedAt | LocalDateTime |

**Relationships:** Many-to-One → `User` (`user_id`); One-to-Many → `Transaction`.

### Transaction
| Field | Type |
|-------|------|
| id | Long |
| transactionReference | String |
| transactionType | String |
| amount | BigDecimal |
| availableBalance | BigDecimal |
| remarks | String |
| status | String |
| transactionTime | LocalDateTime |

**Relationships:** Many-to-One → `Account` (`account_id`).

### Wallet
| Field | Type |
|-------|------|
| id | Long |
| walletNumber | String |
| walletBalance | BigDecimal |
| dailyLimit | BigDecimal |
| active | Boolean |
| createdAt | LocalDateTime |
| updatedAt | LocalDateTime |

**Relationships:** One-to-One → `User` (`user_id`).

### Beneficiary
| Field | Type |
|-------|------|
| id | Long |
| beneficiaryName | String |
| accountNumber | String |
| bankName | String |
| ifscCode | String |
| nickname | String |
| active | Boolean |
| createdAt | LocalDateTime |
| updatedAt | LocalDateTime |

**Relationships:** Many-to-One → `User` (`user_id`).

### ScheduledPayment
| Field | Type |
|-------|------|
| id | Long |
| paymentReference | String |
| paymentTitle | String |
| amount | BigDecimal |
| beneficiaryAccountNumber | String |
| ifscCode | String |
| nextPaymentDate | LocalDate |
| frequency | String |
| active | Boolean |
| createdAt | LocalDateTime |
| updatedAt | LocalDateTime |

**Relationships:** Many-to-One → `User` (`user_id`).

## Prerequisites

- **JDK 21** or later
- **MySQL** server (running locally or reachable)
- **Maven** (optional — the included Maven Wrapper `./mvnw` can be used instead)

## Configuration

Configuration lives in `src/main/resources/application.properties`:

```properties
spring.application.name=SmartBankingBackend
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/smartbank
spring.datasource.username=<your-mysql-username>
spring.datasource.password=<your-mysql-password>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

- The app runs on **port 8080**.
- It expects a MySQL database named **`smartbank`** at `localhost:3306`.
- `spring.jpa.hibernate.ddl-auto=update` lets Hibernate create/update the schema automatically.
- SQL logging is enabled (`show-sql=true`, formatted output).

> **Security note:** Set your own database username/password. Avoid committing real
> credentials to version control — prefer environment variables or an untracked local
> properties file.

Create the database before starting the app:

```sql
CREATE DATABASE smartbank;
```

## Getting Started

```bash
# 1. Clone the repository
git clone <repository-url>
cd SmartBankingBackend-main

# 2. Configure your MySQL credentials in
#    src/main/resources/application.properties

# 3. Run the application (Maven Wrapper)
./mvnw spring-boot:run         # Linux / macOS
mvnw.cmd spring-boot:run       # Windows

# --- or build a JAR and run it ---
./mvnw clean package
java -jar target/SmartBankingBackend-0.0.1-SNAPSHOT.jar
```

Once started, the API is available at: **http://localhost:8080**

## API Reference

Base URL: `http://localhost:8080`

### Users — `/api/users`
| Method | Endpoint | Body / Params | Description |
|--------|----------|---------------|-------------|
| POST | `/api/users` | `User` (JSON) | Create a new user |
| GET | `/api/users/{id}` | — | Get user by ID |
| GET | `/api/users` | — | Get all users |
| PUT | `/api/users/{id}` | `User` (JSON) | Update a user |
| DELETE | `/api/users/{id}` | — | Delete a user |

### Accounts — `/api/accounts`
| Method | Endpoint | Body / Params | Description |
|--------|----------|---------------|-------------|
| POST | `/api/accounts/userId/{userId}` | `Account` (JSON) | Create an account for a user |
| GET | `/api/accounts/{id}` | — | Get account by ID |
| GET | `/api/accounts` | — | Get all accounts |
| PUT | `/api/accounts/{id}` | `Account` (JSON) | Update an account |
| DELETE | `/api/accounts/{id}` | — | Delete an account |
| GET | `/api/accounts/number/{accountNumber}` | — | Get account by account number |
| GET | `/api/accounts/balance/{accountNumber}` | — | Get the balance for an account number |
| PUT | `/api/accounts/{id}/activate` | — | Activate an account |
| PUT | `/api/accounts/{id}/deactivate` | — | Deactivate an account |

### Transactions — `/api/transactions`
| Method | Endpoint | Body / Params | Description |
|--------|----------|---------------|-------------|
| POST | `/api/transactions/deposit` | query: `accountNumber`, `amount`, `remarks` | Deposit money into an account |
| POST | `/api/transactions/withdraw` | query: `accountNumber`, `amount`, `remarks` | Withdraw money from an account |
| POST | `/api/transactions/transfer` | query: `fromAccount`, `toAccount`, `amount`, `remarks` | Transfer money between accounts |
| GET | `/api/transactions/{id}` | — | Get transaction by ID |
| GET | `/api/transactions` | — | Get all transactions |
| GET | `/api/transactions/account/{accountNumber}` | — | Get all transactions for an account number |
| DELETE | `/api/transactions/{id}` | — | Delete a transaction |

### Wallets — `/api/wallets`
| Method | Endpoint | Body / Params | Description |
|--------|----------|---------------|-------------|
| POST | `/api/wallets/{userId}` | `Wallet` (JSON) | Create a wallet for a user |
| GET | `/api/wallets/{id}` | — | Get wallet by ID |
| POST | `/api/wallets/{id}/add-money` | query: `amount` | Add money to a wallet |
| POST | `/api/wallets/{id}/withdraw` | query: `amount` | Withdraw money from a wallet |
| POST | `/api/wallets/{id}/transfer` | query: `receiverWalletId`, `amount` | Transfer from this wallet to another |
| DELETE | `/api/wallets/{id}` | — | Delete a wallet |

### Beneficiaries — `/api/beneficiaries`
| Method | Endpoint | Body / Params | Description |
|--------|----------|---------------|-------------|
| POST | `/api/beneficiaries/{userId}` | `Beneficiary` (JSON) | Create a beneficiary for a user |
| GET | `/api/beneficiaries/{id}` | — | Get beneficiary by ID |
| GET | `/api/beneficiaries/user/{userId}` | — | Get all beneficiaries for a user |
| PUT | `/api/beneficiaries/{id}` | `Beneficiary` (JSON) | Update a beneficiary |
| DELETE | `/api/beneficiaries/{id}` | — | Delete a beneficiary |

### Scheduled Payments — `/api/scheduled-payments`
| Method | Endpoint | Body / Params | Description |
|--------|----------|---------------|-------------|
| POST | `/api/scheduled-payments/userId/{userId}` | `ScheduledPayment` (JSON) | Create a scheduled payment for a user |
| GET | `/api/scheduled-payments/{id}` | — | Get scheduled payment by ID |
| GET | `/api/scheduled-payments/user/{userId}` | — | Get all scheduled payments for a user |
| PUT | `/api/scheduled-payments/{id}` | `ScheduledPayment` (JSON) | Update a scheduled payment |
| PUT | `/api/scheduled-payments/{id}/activate` | — | Activate a scheduled payment |
| PUT | `/api/scheduled-payments/{id}/deactivate` | — | Deactivate a scheduled payment |
| DELETE | `/api/scheduled-payments/{id}` | — | Delete a scheduled payment |

## Business Rules & Validations

These rules are enforced in the service layer (invalid operations throw a runtime error
with a descriptive message):

**Transactions (account-based):**
- Deposit: account must be **active**; amount must be **greater than 0**. Adds to balance.
- Withdraw: account **active**; amount **> 0**; **sufficient balance**; amount within the
  account's **withdrawal limit**; remaining balance must stay at or above the
  **minimum balance**.
- Transfer: both accounts **active**; cannot transfer to the **same account**;
  **sufficient balance**; amount within the **daily transfer limit**; sender must maintain
  **minimum balance**.
- Each transaction gets a unique `transactionReference` (UUID), a `transactionType`
  (`DEPOSIT` / `WITHDRAW` / `TRANSFER`), a `status` of `SUCCESS`, and a timestamp.

**Wallet:**
- Created with `walletNumber` = `"WAL" + <timestamp>` and an initial balance of `0`.
- Add money / withdraw / transfer require the wallet to be **active** and the amount **> 0**.
- Withdraw and transfer check for **sufficient balance** and the wallet's **daily limit**.
- Wallet transfer cannot target the **same wallet** and runs within a transaction
  (`@Transactional`).

**Scheduled Payment:**
- Created with `paymentReference` = `"SP" + <timestamp>`.

**Accounts / Users / Beneficiaries:**
- Creating an account, wallet, beneficiary, or scheduled payment requires an existing
  `userId` (otherwise "User not found").
- `createdAt` / `updatedAt` (and `openedAt` for accounts) are set automatically by the server.

## Example Requests

**Create a user**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Aisha",
        "lastName": "Khan",
        "email": "aisha@example.com",
        "phoneNumber": "9876543210",
        "address": "12 MG Road",
        "city": "Pune",
        "state": "Maharashtra",
        "pincode": "411001",
        "customerId": "CUST001",
        "dateOfBirth": "1996-05-20",
        "active": true
      }'
```

**Create an account for user 1**
```bash
curl -X POST http://localhost:8080/api/accounts/userId/1 \
  -H "Content-Type: application/json" \
  -d '{
        "accountNumber": "SB0001",
        "accountType": "SAVINGS",
        "balance": 10000,
        "minimumBalance": 1000,
        "dailyTransferLimit": 50000,
        "withdrawalLimit": 25000,
        "active": true,
        "branchName": "Pune Main",
        "ifscCode": "SBIN0000123"
      }'
```

**Deposit (query parameters)**
```bash
curl -X POST "http://localhost:8080/api/transactions/deposit?accountNumber=SB0001&amount=5000&remarks=Salary"
```

**Wallet-to-wallet transfer**
```bash
curl -X POST "http://localhost:8080/api/wallets/1/transfer?receiverWalletId=2&amount=500"
```

## Notes & Limitations

- **No authentication/authorization layer** is currently implemented — all endpoints are
  publicly accessible. (No Spring Security dependency is present.)
- **Validation errors** are raised as `RuntimeException` with descriptive messages; there
  is no custom global exception handler, so failures return Spring's default error response.
- **Schema management** is handled automatically by Hibernate (`ddl-auto=update`).
- Controllers print method-entry log lines to the console (`System.out.println`).

## License

No license is specified in `pom.xml`. Add a license file if you intend to distribute this project.
