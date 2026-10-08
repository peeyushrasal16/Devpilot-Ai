# DevPilot AI

DevPilot AI is a full-stack AI-powered project management platform designed for software development teams.

It helps teams manage projects, tasks, bugs, documentation, project members, notifications, and activity logs while providing an AI assistant for project-related questions, application guidance, and programming support.

## 🚀 Features

* User registration and login
* JWT-based authentication
* Role-based access control
* Admin, Project Manager, and Developer roles
* Project management
* Task management
* Task assignments
* Bug tracking
* Project documentation
* Project member management
* Notifications
* Activity logs
* Pagination
* REST APIs
* AI-powered developer assistant
* Database-connected AI responses
* Light and Dark theme
* Responsive React interface

## 🤖 DevPilot AI Assistant

The integrated AI assistant is built using Spring AI and Ollama.

It can:

* Answer project-related questions
* Provide real-time task statistics
* Provide project statistics
* Provide bug statistics
* Answer user and developer-related questions
* Show current user's assigned work
* Explain how to use DevPilot features
* Explain programming errors
* Suggest debugging solutions
* Provide project-management guidance

The AI assistant receives relevant application knowledge and database information from the backend before generating responses.

## 🛠️ Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* REST APIs
* MySQL
* Lombok
* ModelMapper
* Spring AI
* Ollama

### Frontend

* React
* Vite
* Tailwind CSS
* Axios
* React Router
* Lucide React

### AI

* Spring AI
* Ollama
* Qwen3

## 🔐 Security

DevPilot AI uses JWT authentication and role-based authorization.

### Roles

* ADMIN
* PROJECT_MANAGER
* DEVELOPER

Each role has different permissions for accessing and managing application features.

## 📂 Main Modules

```text
Dashboard
Projects
Project Members
Tasks
Task Assignments
Bugs
Documentation
Notifications
Activity Logs
Users
DevPilot AI
Profile
```

## 🏗️ Architecture

```text
React Frontend
       ↓
     Axios
       ↓
Spring Boot REST API
       ↓
Spring Security + JWT
       ↓
Service Layer
       ↓
JPA / Hibernate
       ↓
     MySQL

AI Request
       ↓
React AI Chat
       ↓
POST /ai/ask
       ↓
AiService
       ↓
Database + DevPilot Knowledge
       ↓
Spring AI
       ↓
Ollama / Qwen3
       ↓
AI Response
```

## ⚙️ Backend Setup

### Requirements

* Java 21+
* Maven
* MySQL
* Ollama
* Qwen3 model

### Database

Create a MySQL database:

```sql
CREATE DATABASE devpilot_ai;
```

Configure your database credentials in `application.properties`.

Do not commit your real database password or secret keys to GitHub.

### Ollama

Install Ollama and pull the required model:

```bash
ollama pull qwen3:1.7b
```

Make sure Ollama is running before starting the backend.

### Run Backend

```bash
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

## 💻 Frontend Setup

Go to the frontend directory:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

## 🔑 Authentication

After registration, users can log in and receive a JWT token.

The token is used to authenticate protected API requests.

Example:

```http
Authorization: Bearer <JWT_TOKEN>
```

## 🧠 AI API

The AI assistant uses:

```http
POST /ai/ask
```

Example request:

```json
{
  "question": "How many tasks are there?"
}
```

The AI uses live database information to answer questions about the application.

## 🎨 UI

DevPilot AI supports:

* Dark theme
* Light theme
* Responsive layout
* Sidebar navigation
* AI assistant interface
* Notifications
* User profile menu

## 📌 Future Improvements

* AI-powered task generation
* AI project progress summaries
* AI bug analysis
* AI-assisted documentation generation
* More personalized AI recommendations
* AI-assisted project actions
* Advanced dashboards and analytics

## 👨‍💻 Author

Peeyush Rasal

## 📄 License

This project is developed for learning, portfolio, and demonstration purposes.
