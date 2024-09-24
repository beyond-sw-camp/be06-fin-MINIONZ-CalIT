FROM node:20.17.0
WORKDIR /minionz/frontend
COPY package.json yarn.lock ./
RUN yarn install
COPY . .
RUN yarn build
FROM nginx:alpine
COPY --from=0 /minionz/frontend/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]