# 🏛 CivicHelp Platform

CivicHelp is a secure, SLA-driven civic issue reporting platform that enables citizens to report public problems,
authorities to manage and resolve them, NGOs to collaborate, and administrators to monitor system performance
through analytics and dashboards.

This project was built to demonstrate **real-world backend system design**, not just CRUD APIs.

---

## 🚀 Key Features

### 🔐 Authentication & Authorization
- JWT-based authentication
- Role-Based Access Control (RBAC)
- Supported roles:
  - Citizen
  - Authority
  - NGO
  - Admin
- Fine-grained endpoint protection using Spring Security

---

### 📝 Structured Issue Reporting
- Citizens can create reports with:
  - Category
  - Location (Governorate → City → District)
  - Priority (LOW / MEDIUM / HIGH / EMERGENCY)
  - Description and optional images
- Reports are validated against active categories and valid locations
- Automatic default priority & SLA assignment

---

### 🔄 Report Lifecycle Management
Strictly enforced status transitions:
OPEN → ASSIGNED → IN_PROGRESS → RESOLVED ↘
REJECTED (Admin only)

- Invalid transitions are blocked at the service layer
- Each status change is validated and audited

---

### ⏱ SLA & Escalation
- SLA duration is defined per category
- SLA deadline is calculated automatically at report creation
- Scheduled background job:
  - Detects overdue reports
  - Escalates priority
  - Flags reports for admin/NGO attention

---

### 🏢 Authority & NGO Collaboration
- Authorities manage reports within their jurisdiction
- NGOs can:
  - View unresolved reports
  - Offer help on specific issues
- Admin oversees assignments and approvals

---

### 📊 Admin Dashboards & Analytics
- KPI summary
- Reports by status
- SLA compliance metrics
- Resolution time analytics
- Authority performance tracking

---

### 🧾 Audit Logging
- Tracks sensitive business actions:
  - Status changes
  - Assignments
  - Admin decisions
- Ensures traceability and accountability

---

### ⚡ Caching & Performance
- Spring Cache abstraction
-Redis cache for development

---

## 🧱 System Architecture

- **Architecture Style:** Modular Monolith
- **Design Principles:**
  - Clean Architecture
  - Domain-Driven Design (DDD)
- **Scalability:** Designed to evolve into microservices if needed

---

## 🛠 Tech Stack

| Layer | Technology |
|-----|-----------|
| Language | Java |
| Framework | Spring Boot |
| Security | Spring Security + JWT |
| ORM | JPA / Hibernate |
| Database | PostgreSQL |
| Caching | Spring Cache (Redis-ready) |
| API Docs | Swagger / OpenAPI |
| Build Tool | Maven |

---

## 🔐 Security Model

| Role | Permissions |
|----|------------|
| Citizen | Create & view own reports |
| Authority | Manage assigned reports |
| NGO | View unresolved reports & offer help |
| Admin | Full system access & analytics |

---

## 📍 Location Hierarchy

Governorate
└── City
└── District

- Fully database-driven
- Seeded with initial Egypt locations

---

## 📈 Future Enhancements

Email & push notifications
Kafka-based event processing
Microservices extraction
Full CI/CD pipeline

---

## 👨‍💻 Author

Mahmoud Mohamed Matar<br>
Backend Engineer<br>
📧 Email: <a src ="mahmoudmatar49@gmail.com"> mahmoudmatar49@gmail.com </a>
