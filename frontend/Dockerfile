FROM node:24-alpine AS base
WORKDIR /app/frontend
COPY package*.json ./

FROM base AS develop
RUN npm ci
COPY . .
EXPOSE 5173
CMD ["npm", "run", "dev", "--", "--host"]

FROM base AS test
RUN npm ci
COPY . .
CMD ["npm", "test"]

FROM base AS build-production
RUN npm ci
COPY . .
ARG VITE_URL_DOCUMENTS
RUN npm run build

FROM nginx:alpine AS production
COPY --from=build-production /app/frontend/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]