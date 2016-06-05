FROM ubuntu
MAINTAINER Andriu

# Install Java.
RUN \
  apt-get update && \
  apt-get install -y git && \
  apt-get install -y openjdk-8-jre && \
  rm -rf /var/lib/apt/lists/*

# Define working directory.

WORKDIR /app
# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
#RUN \
#  git clone https://github.com/vonszymon/EventSystem.git
ADD pivotal-tc-server-developer-3.1.3.SR1.tar.gz /app
# Define default command.
RUN \
  cd pivotal-tc-server-developer-3.1.3.SR1 && \
  ./tcruntime-instance.sh create my_server && \
  ./tcruntime-ctl.sh  my_server start && \
  cd my_server && \
  mkdir wtpwebapps
ADD /wtpwebapps /app/pivotal-tc-server-developer-3.1.3.SR1/my_server/wtpwebapps
CMD ["bash"]