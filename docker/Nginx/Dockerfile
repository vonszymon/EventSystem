FROM ubuntu

MAINTAINER Andriu

RUN apt-get update

RUN apt-get install -y nano wget dialog net-tools

RUN apt-get install -y nginx  

RUN rm -v /etc/nginx/nginx.conf

ADD nginx.conf /etc/nginx/

CMD service nginx start


