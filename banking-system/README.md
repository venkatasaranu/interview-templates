# 🏦 Banking System – Backend Exercise

## 👋 Welcome!

Thank you for taking the time to complete this technical exercise!

The goal of this assessment is to evaluate your:

✅ Object-Oriented Programming (OOP) skills

✅ Understanding of RESTful API design

✅ Ability to structure clean, maintainable backend code using Java and Spring Boot
___
## 🧠 What We're Looking For
* Clean and maintainable OOP design
* Proper use of RESTful conventions
* Separation of concerns and modular architecture
* Code readability and clarity
___
## ⚙️ Technical Expectations
The provided project uses **Java 21** and **Spring Boot 3.x**

🗃️ Use in-memory storage (e.g., Map, List) — no need for a database

📦 Use Java DTOs AND Domains if desired

🔄 Include DTO-to-Entity mapping where applicable

🛡️ Implement input validation and exception handling
___
## 🏧 Challenge Overview
You are building a RESTful backend for a **banking system**.

### The bank has:
* Customers who can hold multiple accounts
* Account types: `CHECKING`, `SAVINGS`, `MONEY_MARKET`, `FIXED_DEPOSIT`
* Transaction tracking: deposits, withdrawals, and transfers between accounts
* Daily transfer limits and fraud detection mechanisms
___
## 📡 API Specifications

### 1. Register Customer
**Endpoint**: `POST` `/api/customers`

**Request Body**:
```json
{
  "name": "Alice Johnson",
  "email": "alice@email.com",
  "phone": "555-0200",
  "address": "789 Pine St, Chicago, IL 60601"
}
```
**Response**: `201 Created` with customerId
___
### 2. Open Account
**Endpoint**: `POST` `/api/accounts`

**Request Body**:
```json
{
  "customerId": "cust-001",
  "accountType": "SAVINGS",
  "initialDeposit": 500.00,
  "currency": "USD"
}
```
**Response**:
* `201 Created` with accountId and account details
* `400 Bad Request` if initial deposit is below minimum
___
### 3. Get Balance
**Endpoint**: `GET` `/api/accounts/{accountId}/balance`

**Response**: Current balance, account type, and currency
___
### 4. Deposit
**Endpoint**: `POST` `/api/accounts/{accountId}/deposit`

**Request Body**:
```json
{
  "amount": 1000.00,
  "description": "Paycheck deposit"
}
```
**Response**: `200 OK` with transaction details and new balance
___
### 5. Withdraw
**Endpoint**: `POST` `/api/accounts/{accountId}/withdraw`

**Request Body**:
```json
{
  "amount": 250.00,
  "description": "ATM withdrawal"
}
```
**Response**:
* `200 OK` with transaction details and new balance
* `400 Bad Request` if insufficient funds or account locked
___
### 6. Transfer
**Endpoint**: `POST` `/api/transfers`

**Request Body**:
```json
{
  "fromAccountId": "acc-001",
  "toAccountId": "acc-002",
  "amount": 500.00,
  "description": "Rent payment"
}
```
**Response**:
* `200 OK` with transaction details for both accounts
* `400 Bad Request` if insufficient funds or exceeds daily transfer limit ($10,000)
___
### 7. Transaction History
**Endpoint**: `GET` `/api/accounts/{accountId}/transactions`

**Query Parameters**: `from` (date, optional), `to` (date, optional), `type` (optional)

**Response**: List of transactions sorted by date descending
___
### 8. Monthly Statement
**Endpoint**: `GET` `/api/accounts/{accountId}/statement`

**Query Parameters**: `month` (1–12), `year`

**Response**: All transactions for the month with opening/closing balance and summary
___
## 🌐 Bonus Task: Fraud Detection
Flag suspicious activity automatically:
* Any single transaction above `$10,000` is flagged for review
* More than 3 failed withdrawal attempts locks the account
* Rapid successive transactions (>5 in 1 minute) trigger a review flag

> [!NOTE]
> Fraud thresholds are pre-configured in `application.yaml`.
___
## 🧩 Interview Questions to Consider
1. How would you ensure atomicity for a transfer operation (debit one, credit another)?
2. How would you prevent race conditions when two withdrawals happen simultaneously?
3. What strategy would you use to calculate interest on savings accounts?
4. How would you design an audit trail that cannot be tampered with?
5. How would you handle currency conversion for international transfers?
