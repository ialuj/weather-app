# Estágio 1: Build
FROM node:20-alpine AS build
WORKDIR /app

# Copiar package.json e instalar dependências
COPY package*.json ./
RUN npm ci --force

# Copiar código fonte e fazer build
COPY . .
RUN npm run build -- --configuration production

# Estágio 2: Servir com Nginx
FROM nginx:alpine

# Copiar os arquivos buildados para o Nginx
COPY --from=build /app/dist/weather-info-frontend/browser /usr/share/nginx/html

# Copiar configuração customizada do Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Expor a porta do frontend
EXPOSE 80

# Comando para rodar o Nginx
CMD ["nginx", "-g", "daemon off;"]
