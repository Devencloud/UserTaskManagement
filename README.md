# User Task Management API

A Spring Boot backend project that provides **secure task management with role-based access control** using **JWT authentication**.  

At the moment, the API exposes an **open login endpoint** that issues a JWT token, which is then required for accessing protected resources (user and admin pages).

---

## 🚀 Features (Current)

- **Login with email & password** → returns a **JWT token**.  
- Passwords are securely stored using **BCrypt hashing**.  
- JWT token contains user role information (`USER` or `ADMIN`).  
- This token must be included in all further requests to access protected resources.  

---

## 🔑 Authentication Flow

1. A user sends a `POST` request to the login endpoint with **email** and **password**. The open endpoint is /api/login
2. If valid, the server returns a **JWT token**.  
3. This token is then used in the `Authorization` header for protected requests:  

