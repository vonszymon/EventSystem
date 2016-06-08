FROM ubuntu
MAINTAINER Andriu

# Install Java.
RUN \
  apt-get update && \
  apt-get install -y openjdk-8-jre && \
  rm -rf /var/lib/apt/lists/*




# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV CATALINA_HOME /app/pivotal-tc-server-developer-3.1.3.SR1/tomcat-7.0.67.B.RELEASE/bin/
ENV CATALINA_BASE /app/pivotal-tc-server-developer-3.1.3.SR1/my_server

ADD pivotal-tc-server-developer-3.1.3.SR1.tar.gz /app

# Define working directory.
WORKDIR /app/pivotal-tc-server-developer-3.1.3.SR1
RUN \
  ./tcruntime-instance.sh create my_server && \
  cd my_server && \
  mkdir wtpwebapps
ADD /wtpwebapps /app/pivotal-tc-server-developer-3.1.3.SR1/my_server/wtpwebapps
ADD /wtpwebapps /app/pivotal-tc-server-developer-3.1.3.SR1/my_server/webapps
ADD /config /app/pivotal-tc-server-developer-3.1.3.SR1/my_server
EXPOSE 8080
# Define default command.
#/app/pivotal-tc-server-developer-3.1.3.SR1/my_server/logs/catalina.out
#CMD ["ls"]
RUN ["/app/pivotal-tc-server-developer-3.1.3.SR1/tcruntime-ctl.sh",  "my_server", "start"]
CMD ["tail", "-f", "/app/pivotal-tc-server-developer-3.1.3.SR1/my_server/logs/catalina.out"] 

