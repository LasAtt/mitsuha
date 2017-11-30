# Mitsuha

Kotlin based image gallery software.

Currently supported functionality

* Uploading images
* Browsing images

### Backend

The backend is written fully with Kotlin, utilizing Spring Boot. The database connections are handled by the excellent Exposed library from JetBrains.

```bash
//starting the backend
cd backend/
./gradleW bootRun
```

### Frontend

The frontend is again fully written in Kotlin, using JetBrain's React wrappers for Kotlin. State management is handled by my own implementation of Redux, rewritten in Kotlin. The Redux state is connected to React components with my own version of react-redux's connect function, which unlike the JavaScript version, is fully typesafe.

```bash
//starting the frontend
cd frontend/
yarn start
```
