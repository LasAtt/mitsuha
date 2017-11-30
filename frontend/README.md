### Frontend

The frontend is again fully written in Kotlin, utilizing JetBrain's React wrappers for Kotlin. State management is 
handled by my own implementation of Redux, rewritten in Kotlin. The Redux state is connected to the React components with 
my own version of react-redux's connect function, which unlike the original, is fully typesafe.

```bash
//starting the frontend
cd frontend/
yarn start
```
