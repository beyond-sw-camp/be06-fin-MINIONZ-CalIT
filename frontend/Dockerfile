FROM node:20.17.0
FROM nginx:alpine
ADD ./dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]