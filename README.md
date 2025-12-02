# Farm Produce Logistics API

A Spring Boot REST API for tracking farm produce logistics, managing inventory, shipments, and delivery schedules.

## Features

- **User Management**: Role-based access (Admin, Farmer, Distributor).
- **Inventory Management**: Farmers can add and manage produce.
- **Shipment Tracking**: Distributors can create and track shipments.
- **Delivery Scheduling**: Schedule vehicles and drivers for shipments.
- **Security**: JWT-based authentication and authorization.
- **Deployment**: Dockerized application and MySQL database.

## Prerequisites

- [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/)
- Java 17 (only if running locally without Docker)
- Maven (only if running locally without Docker)

## Getting Started



### 1. Run with Docker

The easiest way to run the application is using Docker Compose. This will start both the API and the MySQL database.

```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080`.

### 2. Run Locally (Optional)

If you prefer to run locally without Docker for the app (you still need a database):

1.  Start a MySQL database (or use the one from docker-compose).
2.  Update `src/main/resources/application.properties` with your database credentials.
3.  Run the application:
    ```bash
    mvn spring-boot:run
    ```

## API Documentation

### Authentication

All endpoints except `/api/auth/**` require a valid JWT token in the `Authorization` header: `Bearer <token>`.

#### Register User
**POST** `/api/auth/register`

```json
{
  "username": "farmer_john",
  "email": "john@farm.com",
  "password": "securepassword",
  "role": ["ROLE_FARMER"]
}
```
*Roles: `ROLE_FARMER`, `ROLE_DISTRIBUTOR`, `ROLE_ADMIN`*

#### Login
**POST** `/api/auth/login`

```json
{
  "username": "farmer_john",
  "password": "securepassword"
}
```
**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "farmer_john",
  "role": "ROLE_FARMER"
}
```

### Produce (Farmers)

#### Add Produce
**POST** `/api/produce`
*Requires: `ROLE_FARMER`*

```json
{
  "name": "Potatoes",
  "type": "Vegetable",
  "quantity": 500,
  "pricePerUnit": 1.50
}
```

#### Get All Produce
**GET** `/api/produce`

### Shipments (Distributors)

#### Create Shipment
**POST** `/api/shipments`
*Requires: `ROLE_DISTRIBUTOR`*

```json
{
  "produceId": 1,
  "quantity": 100
}
```

#### Update Status
**PATCH** `/api/shipments/{id}/status`
*Requires: `ROLE_DISTRIBUTOR`*

```json
{
  "status": "IN_TRANSIT"
}
```
*Statuses: `PENDING`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED`*

### Delivery

#### Schedule Delivery
**POST** `/api/delivery/schedule/{shipmentId}`
*Requires: `ROLE_DISTRIBUTOR`*

```json
{
  "vehicleNumber": "TRUCK-001",
  "driverName": "Mike Driver",
  "driverContact": "555-0199",
  "scheduledPickupTime": "2023-12-01T10:00:00",
  "estimatedDeliveryTime": "2023-12-02T14:00:00"
}
```

## Database Access

The MySQL database runs on port `3307` (mapped from container 3306).

- **URL**: `jdbc:mysql://localhost:3307/farm_logistics`
- **Username**: `root`
- **Password**: `root`

## Project Structure

- `src/main/java/com/farm/logistics`
    - `config`: Security configuration.
    - `controller`: REST API endpoints.
    - `model`: JPA Entities.
    - `repository`: Database access.
    - `security`: JWT implementation.
    - `service`: Business logic.
