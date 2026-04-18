FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build
RUN mkdir -p /out && if [ -d /app/dist/cmsaws-frontend-admin/browser ]; then cp -r /app/dist/cmsaws-frontend-admin/browser/* /out/; else cp -r /app/dist/cmsaws-frontend-admin/* /out/; fi

FROM nginx:1.27-alpine
COPY --from=build /out /usr/share/nginx/html

EXPOSE 80
