docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

docker run -d --name dd-agent -h `hostname` -v /var/run/docker.sock:/var/run/docker.sock -v /proc/:/host/proc/:ro -v /sys/fs/cgroup/:/host/sys/fs/cgroup:ro \
-e API_KEY=802efe71870dec9e8c5df14426f3bb81 datadog/docker-dd-agent:latest

cd Databases
./deploy.sh
./deploy.sh
./deploy.sh
./deploy.sh
cd -

cd Services
docker build -t eventservices . 
docker run -d -i --name eventservices --link eventdb:eventdb -p 40402:8080 -p 9000:9000 eventservices:latest
cd -

cd System
docker build -t eventsystem .
docker run -t -i --name eventsystem --link userdb:userdb --link eventservices:eventservices -p 40000:8080 eventsystem:latest

