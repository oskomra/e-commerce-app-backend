# 🛍️ E-Commerce Backend API (Java + Spring Boot)

This is the backend API for a full-stack e-commerce web application, built with **Java** and **Spring Boot**, connected to a **Next.js frontend**. It handles product management, user authentication, orders, and more.

🔗 [Live Frontend](https://e-commerce-app-frontend-beta.vercel.app)  
📦 Frontend Repo: [nextjs-ecommerce-frontend](https://github.com/oskomra/e-commerce-app-frontend)

---

## ✨ Features

- RESTful API for e-commerce operations
- JWT-based authentication and authorization
- User roles: Admin & User
- CRUD operations for:
  - Products
  - Categories
  - Orders
- Order management and history
- Image upload and storage via Cloudinary
- Integration with SQL database (PostgreSQL)

---

## 🛠 Tech Stack

| Layer         | Technology         |
|---------------|--------------------|
| Language       | Java 21           |
| Framework      | Spring Boot       |
| Database       | PostgreSQL        |
| Security       | Spring Security + JWT |
| Build Tool     | Maven             |
| Deployment     | Railway          |

---

## User Roles and Admin Access

- By default, all newly registered users are assigned the **user** role.
- For demo purposes, the application includes **one pre-created admin user** with elevated privileges.

### Admin Credentials

- **Email:** admin@gmail.com  
- **Password:** admin123

You can use these credentials to log in as an admin and access admin-specific features such as managing products and orders.

---
