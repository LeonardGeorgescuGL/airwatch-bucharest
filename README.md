# 🌍 AirWatch Bucharest

> **An Advanced IoT-ML Cyber-Physical System for Real-Time Air Quality Monitoring, Health Risk Clustering, and Predictive Temporal Forecasting in Bucharest.**
>
> *Developed as an Undergraduate Thesis in Business Computer Science (Licență) at the Faculty of Cybernetics, Statistics and Economic Informatics (CSIE), Bucharest University of Economic Studies (ASE).*

---

[![React](https://img.shields.io/badge/React-19.0-61DAFB?logo=react&logoColor=black&style=for-the-badge)](#)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.9-3178C6?logo=typescript&logoColor=white&style=for-the-badge)](#)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?logo=springboot&logoColor=white&style=for-the-badge)](#)
[![FastAPI](https://img.shields.io/badge/FastAPI-0.109-009688?logo=fastapi&logoColor=white&style=for-the-badge)](#)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16.0-4169E1?logo=postgresql&logoColor=white&style=for-the-badge)](#)
[![Python](https://img.shields.io/badge/Python-3.10+-3776AB?logo=python&logoColor=white&style=for-the-badge)](#)

---

## 📌 Executive Summary & Thesis Context

Rapid urbanization and dense vehicular traffic have made air quality monitoring one of the most critical challenges for modern Smart Cities, particularly in capitals like Bucharest. **AirWatch Bucharest** is a full-stack, microservice-based web application that addresses this issue.

By combining modern software engineering principles with advanced data science techniques, the platform collects micro-level atmospheric data (PM2.5, PM10, CO, NO₂, O₃, SO₂), dynamically segments urban areas using **unsupervised Machine Learning (K-Means + PCA)**, and forecasts AQI levels using **temporal time-series analysis (Meta Prophet)**. Furthermore, the application incorporates **gamification mechanics (Leaderboard & Community Alerts)** to foster citizen science and local eco-activism, translating raw environmental data into actionable civic responses.

---

## 📐 System Architecture

AirWatch Bucharest is built on a decoupled, three-tier microservice architecture designed for high availability, fault tolerance, and separation of concerns:

```
┌─────────────────────────────────────────┐
│           React 19 Frontend             │
│   (Vite + TypeScript + Tailwind CSS)    │
└────────────────────┬────────────────────┘
                     │
       HTTPS Requests│   API / Data / Auth
                     ▼
┌─────────────────────────────────────────┐
│        Spring Boot 3 Backend            │
│       (Security + JPA + Maven)          │
└──────┬──────────────────────────┬───────┘
       │                          │
JPA/JDBC│                          │ REST / HTTP (JSON)
       ▼                          ▼
┌───────────────────┐    ┌───────────────────┐
│  PostgreSQL DB    │    │ FastAPI ML Engine │
│ (Supabase Cloud)  │    │ (scikit-learn,    │
│  Users, Sensors,  │    │  Prophet, Pandas) │
│  Alerts, Points   │    └───────────────────┘
└───────────────────┘
```

1. **Presentation Layer (Client)**: A high-performance Single Page Application (SPA) built using React 19, TypeScript, and Vite. Visually enhanced with Tailwind CSS, Lucide icons, responsive charts via Recharts, and micro-interactions powered by Framer Motion.

2. **Business & Persistence Layer (Server)**: A robust Spring Boot 3 API that handles relational database mapping, transaction management, secure user sessions, role-based authorization, and communication with the Machine Learning microservice.

3. **Data Science & ML Layer (ml-service)**: A dedicated Python FastAPI microservice that processes atmospheric parameters, executes PCA reduction, runs K-Means clustering to identify health risk zones, and trains time-series models (Prophet) on-the-fly.

---

## 🤖 Machine Learning Integration

The core differentiator of this academic project is the deployment of two distinct Machine Learning workflows to transform historical and spatial data into intelligent insights:

### 1. Spatial Health Risk Clustering (K-Means + PCA)

- **Goal**: Dynamically group Bucharest sensor locations into three risk tiers (*Moderate Risk*, *High Risk*, *Severe Risk*) based on multi-pollutant levels.
- **Dimensionality Reduction**: Sensors capture 7 dimensions (PM2.5, PM10, NO₂, O₃, CO, SO₂, and AQI). To avoid the *Curse of Dimensionality* and improve clustering accuracy, the service utilizes **Principal Component Analysis (PCA)** to project features onto 3 principal components.
- **Clustering Engine**: K-Means clustering (k=3) is applied to the reduced PCA space. Clusters are automatically reordered and mapped to semantic health zones based on the centroid's average Air Quality Index (AQI).

### 2. Temporal Forecasting (Meta Prophet)

- **Goal**: Predict AQI trends for the next N days using historical hourly sensor logs.
- **Algorithm**: **Meta's Prophet**, an additive regression model optimized for time-series data with strong seasonal effects (daily, weekly) and non-linear trends.
- **Evaluation Framework**: The model automatically splits historical sensor records (80% training, 20% test). It computes real-time regression metrics (R², MAE, RMSE, and MAPE) to provide a reliability classification (*Excellent, Good, Acceptable, Poor*) before deploying the model trained on the full dataset for future predictions.

---

## ✨ Features Breakdown

### 🗺️ Interactive GIS Dashboard
- Real-time map displaying Bucharest's air quality sensors with pulsing animated markers.
- Interactive markers color-coded by dynamic health risk levels computed by the K-Means service.
- Side panels with granular metrics for PM2.5, PM10, CO, NO₂, O₃, and SO₂.

### 📈 Predictive Analytics & Time-Series
- Interactive historic data interface utilizing Recharts to display pollution curves.
- Predictive forecast toggle displaying the Meta Prophet projection with confidence intervals (upper/lower bounds).
- Full transparency: lists regression metrics (R², Mean Absolute Error) directly to the end-user.

### 📋 Civic Reporting System
- Four report types: **Text Report**, **Geotagged Photo**, **GeoJSON Zone**, **Community Survey**.
- Built-in analytics dashboard with Recharts visualizations per report type.
- Zone selector integrated with the urban area map.

### 📢 Community Alerts
- Real-time neighborhood pollution alerts (traffic congestion, illegal burning, industrial smoke).
- Social upvoting system to increase local alert visibility.

### 🏆 Gamified Eco-Leaderboard
- Citizens earn points for reporting hazards and confirming community warnings.
- Rank system: Bronze → Silver → Gold → Expert with exclusive benefits per tier.
- Neighborhood-based leaderboards highlighting the most active areas in Bucharest.

---

## 🛠️ Tech Stack & Key Libraries

### Client (Frontend)
| Technology | Version | Purpose |
|---|---|---|
| React | 19.0 | Component-driven UI architecture |
| TypeScript | 5.9 | Strict type safety |
| Vite | 5.x | HMR and optimized build |
| Tailwind CSS | 3.x | Utility-first styling |
| Framer Motion | 11.x | Animations & transitions |
| Recharts | 2.x | Responsive data visualization |
| Leaflet + React-Leaflet | 1.x | Interactive GIS map |

### Server (Backend)
| Technology | Version | Purpose |
|---|---|---|
| Java | 17 / 21 | Runtime environment |
| Spring Boot | 3.2 | Enterprise web framework |
| Spring Security | 6.x | Authentication & authorization |
| Spring Data JPA | 3.x | ORM for PostgreSQL |
| Lombok | 1.18.x | Boilerplate reduction |
| Dotenv Java | 3.x | Environment config management |

### ML Service (Python Engine)
| Technology | Version | Purpose |
|---|---|---|
| FastAPI | 0.109 | Async ML API framework |
| scikit-learn | 1.4+ | PCA & K-Means clustering |
| Meta Prophet | 1.1.5 | Time-series forecasting |
| Pandas | 2.x | Data manipulation |
| NumPy | 1.26+ | Numerical computing |

### Infrastructure
| Service | Purpose |
|---|---|
| Supabase (PostgreSQL 16) | Cloud-hosted relational database |
| OpenWeatherMap API | Live air pollution data source |

---

## 🚀 Installation & Running Locally

### Prerequisites

- **Java Development Kit (JDK) 17** or 21
- **Node.js** v18+
- **Python 3.10+** with `pip`
- **PostgreSQL** database (local or Supabase hosted)

### 1. Clone the Repository

```bash
git clone https://github.com/LeonardGeorgescuGL/airwatch-bucharest.git
cd airwatch-bucharest
```

### 2. Environment Configuration

Create a `.env` file in `./server/`:

```properties
DB_PASSWORD=your_supabase_password
OPENWEATHER_API_KEY=your_openweather_key
```

Create a `.env` file in `./client/`:

```
REACT_APP_API_URL=http://localhost:8080/api
```

### 3. Database Setup

Run the SQL schema in your PostgreSQL instance or Supabase SQL Editor:

```bash
# Schema file located at:
./server/src/main/resources/schema.sql
```

### 4. Start All Services (PowerShell)

A pre-configured PowerShell orchestration script is provided:

```powershell
./start-all.ps1
```

This will sequentially start:

| Service | URL |
|---|---|
| Spring Boot Backend | http://localhost:8080 |
| FastAPI ML Service | http://localhost:8000 |
| React Frontend | http://localhost:5173 |

---

airwatch-bucharest/
│
├── client/                 # React 19 Frontend Application
│   ├── src/
│   │   ├── components/     # Landing, Map, Alerts, History, Leaderboard
│   │   ├── api/            # API integration Layer
│   │   └── types/          # TS Interfaces
│   └── package.json
│
├── server/                 # Spring Boot Backend API
│   ├── src/main/java/com/airwatch/
│   │   ├── config/         # Security & Web Configs
│   │   ├── controller/     # REST Endpoints
│   │   ├── model/          # JPA Entities
│   │   ├── repository/     # Data Access Layers
│   │   └── service/        # Core Business Logic
│   └── pom.xml
│
├── ml-service/             # FastAPI Machine Learning Microservice
│   ├── routers/            # API routing
│   ├── kmeans_service.py   # PCA & Clustering execution
│   ├── prophet_service.py  # Facebook Prophet forecasting
│   ├── main.py             # Server entry point
│   └── requirements.txt
│
└── start-all.ps1           # Automation launch script
└── README.md

---

## 🎓 Academic Contribution & Economic Value

This application represents a comprehensive solution for Urban Environmental Management. From an economic perspective:

- **Public Health Optimization**: Provides citizens with predictive alerts, potentially reducing healthcare expenditures associated with respiratory illnesses.
- **Civic Crowdsourcing**: The community engagement model decreases municipal inspection costs by using crowd-sourced alerts to locate local environmental hazards.
- **Data-Driven Smart-City Decisions**: The ML insights can aid city planners in implementing traffic regulations, green corridor creation, and infrastructure development in Bucharest.

---

## 📄 License

This project was developed for academic purposes as a bachelor thesis at ASE Bucharest. All rights reserved © 2026 Leonard Georgescu.

---

*Faculty of Cybernetics, Statistics and Economic Informatics (CSIE) · Bucharest University of Economic Studies (ASE) · 2025–2026*
