FROM ubuntu
MAINTAINER Andriu

# Install Java.
RUN \
  apt-get update && \
  apt-get install -y openjdk-8-jre && \
  rm -rf /var/lib/apt/lists/*

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

ADD run_scripts.sh /run_scripts.sh

ADD pivotal-tc-server-developer-3.1.3.SR1.tar.gz /app

# Define working directory.
WORKDIR /app/pivotal-tc-server-developer-3.1.3.SR1
RUN \
  ./tcruntime-instance.sh create my_server 

ADD EventSystem.war /app/pivotal-tc-server-developer-3.1.3.SR1/my_server/webapps
EXPOSE 8080 2181 9092

CMD bash -C '/run_scripts.sh';'bash'


