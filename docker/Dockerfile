FROM ubuntu
MAINTAINER Andriu

# Install Java.
RUN \
  apt-get update && \
  apt-get install -y openjdk-8-jre && \
  rm -rf /var/lib/apt/lists/*


#kafka - start 
RUN apt-get update && \
    apt-get install -y unzip wget curl docker jq coreutils && \
    rm -rf /var/lib/apt/lists/*
    
ENV KAFKA_VERSION="0.10.0.0" SCALA_VERSION="2.11"

RUN wget -q http://www-eu.apache.org/dist/kafka/0.10.0.0/kafka_2.11-"$KAFKA_VERSION".tgz -O /tmp/kafka-"$KAFKA_VERSION".tgz && \
    tar -xzf /tmp/kafka-"$KAFKA_VERSION".tgz -C /opt && \
    rm /tmp/kafka-"$KAFKA_VERSION".tgz

ENV KAFKA_HOME /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION}

ADD start.sh /start.sh
#kafka - end

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
EXPOSE 8080 2181 9092

# Define default command.
#/app/pivotal-tc-server-developer-3.1.3.SR1/my_server/logs/catalina.out

# run kafka, pivotal
CMD ["/bin/bash", "/start.sh"]
