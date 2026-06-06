# Weather App - African Cities Weather

A full-stack web application that provides real-time weather information and AI-powered recommendations for African capitals and major cities. Built with **Spring Boot** (backend) and **Angular** (frontend), consuming the Weather-AI API.

## 🌍 Features

- **Real-time weather** for 70+ African cities (all capitals + major cities)
- **AI-powered summaries** with personalized recommendations
- **Multi-day forecast** (1-7 days)
- **Multi-language support** (English, Portuguese, French, Swahili, Arabic)
- **Unit options** (Metric °C / Imperial °F)
- **Current location weather** (GPS-based)
- **Search and filter** cities
- **Responsive design** with dynamic weather icons (sunny, rainy, cloudy, cold)

## 🎯 Use Cases

- Travel planning to African destinations
- Agricultural and farming decisions
- Event planning across African cities
- Educational purposes (geography, climate studies)
- Business intelligence for logistics and supply chain

## 🛠️ Tech Stack

| Component      | Technology                                |
| -------------- | ----------------------------------------- |
| **Backend**    | Java 17, Spring Boot 3, Maven             |
| **Frontend**   | Angular 18, TypeScript, SCSS              |
| **API**        | Weather-AI API (real-time + AI summaries) |
| **Container**  | Docker, Docker Compose                    |
| **Deployment** | Backend: SnapDeploy / Frontend: Netlify   |

## 📋 Prerequisites

- **Docker** and **Docker Compose** (for local containerized setup)
- **Node.js** 20+ (for frontend development)
- **Java 17** (for backend development)
- **Weather-AI API Key** (get one at [weather-ai.co](https://weather-ai.co))

## 🚀 Quick Start with Docker (Recommended)

### 1. Clone the repository

````bash
git clone https://github.com/ialuj/weather-app.git
cd weather-app

2. Set up environment variables
Create a .env file in the root directory:

# Weather-AI API Key (required)
WEATHER_API_KEY=your_api_key_here

3. Run with Docker Compose

# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

4. Access the application

Service	URL
Frontend	http://localhost:4200
Backend API	http://localhost:8080/api

-----------------------------------------------------------------------
🏃 Local Development (without Docker)

Backend (Spring Boot)

cd weather-info-api

# Set environment variable
export WEATHER_API_KEY=your_api_key_here

# Run with Maven
./mvnw spring-boot:run

# Or build and run JAR
./mvnw clean package
java -jar target/*.jar

Backend will run at: http://localhost:8080/api

Frontend (Angular)

cd weather-info-frontend

# Install dependencies
npm install

# Run development server
npm start

# Build for production
npm run build -- --configuration production

Frontend will run at: http://localhost:4200

🔗 API Endpoints

Endpoint	Method	Description	Example
/api/weather/health	GET	Health check	curl http://localhost:8080/api/weather/health
/api/weather?city=Maputo	GET	Weather by city name	curl "http://localhost:8080/api/weather?city=Nairobi"
/api/weather/by-coords?lat=-1.29&lon=36.82	GET	Weather by coordinates	curl "http://localhost:8080/api/weather/by-coords?lat=-1.29&lon=36.82"
/api/weather/cities	GET	List all supported cities	curl http://localhost:8080/api/weather/cities
/api/weather/analytics	GET	Query analytics (hits per city)	curl http://localhost:8080/api/weather/analytics

Query Parameters for Weather Endpoints

Parameter	Type	Default	Description
days	integer	3	Number of forecast days (1-7)
ai	boolean	true	Include AI summary
units	string	metric	metric (°C) or imperial (°F)
lang	string	en	Language: en, pt, fr, sw, ar

Example Response

{
  "city": "Maputo",
  "current": {
    "temperature": 28.5,
    "condition": "Sunny",
    "humidity": 65,
    "windKph": 12.5,
    "feelsLike": 29.0
  },
  "aiSummary": {
    "text": "Maputo is quite warm today at 29°C. Stay hydrated and avoid direct sun exposure.",
    "recommendation": "🧊 Wear light clothes and drink water"
  },
  "forecast": [
    {
      "date": "2026-06-07",
      "maxTemp": 29.0,
      "minTemp": 22.0,
      "condition": "Sunny"
    }
  ],
  "timestamp": 1700000000000
}

🗺️ Supported Cities
The application includes 70+ African cities, including:

Southern Africa: Maputo, Pretoria, Cape Town, Johannesburg, Windhoek, Gaborone, Lusaka, Harare

East Africa: Nairobi, Mombasa, Kampala, Dar es Salaam, Addis Ababa, Kigali

North Africa: Cairo, Alexandria, Khartoum, Tripoli, Tunis, Algiers, Casablanca

West Africa: Dakar, Accra, Lagos, Abuja, Abidjan, Bamako

Central Africa: Luanda, Kinshasa, Yaoundé, Libreville

🐳 Docker Commands Reference

# Build images
docker-compose build

# Start services
docker-compose up -d

# Stop services
docker-compose down

# View logs for specific service
docker-compose logs -f backend
docker-compose logs -f frontend

# Rebuild and restart
docker-compose up -d --build

# Remove volumes (clean database/data)
docker-compose down -v

📁 Project Structure

weather-app/
├── weather-info-api/          # Spring Boot backend
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── weather-info-frontend/     # Angular frontend
│   ├── src/
│   ├── package.json
│   ├── Dockerfile
│   └── nginx.conf
├── docker-compose.yml
└── README.md

🚢 Deployment
Backend (SnapDeploy)
Container runs Spring Boot on port 80

Environment variable: WEATHER_API_KEY

Frontend (Netlify)
Static site deployment

Build command: npm run build -- --configuration production

Publish directory: dist/weather-info-frontend/browser

🤝 Contributing
Fork the repository

Create a feature branch (git checkout -b feature/amazing-feature)

Commit changes (git commit -m 'Add amazing feature')

Push to branch (git push origin feature/amazing-feature)

Open a Pull Request

📝 License
This project is for demonstration purposes.

👨‍💻 Author
José Julai Ritsure

🙏 Acknowledgments
Weather-AI API for weather data and AI summaries

Spring Boot for backend framework

Angular for frontend framework

Live Demo: Frontend | Backend Health


---

## 📌 Instruções para adicionar o README ao repositório

```bash
cd C:\workspaces\interviews\weather-app

# Criar o arquivo README.md com o conteúdo acima
# (copie o conteúdo e cole)

git add README.md
git commit -m "Add comprehensive README with setup instructions"
git push origin develop

Live app url: https://weather-app-eta-two-9gv21m3g2m.vercel.app/

See more about the amazing Weather AI API at: https://weather-ai.co/

````
