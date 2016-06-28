docker-machine restart mh-keystore
eval "$(docker-machine env mh-keystore)"
docker run -d \
    -p "8500:8500" \
    -h "consul" \
    progrium/consul -server -bootstrap
docker-machine restart mhs-demo0
docker-machine restart mhs-demo1

eval $(docker-machine env --swarm mhs-demo0)

docker network create --driver overlay --subnet=10.0.9.0/24 my-net

docker stop userdb
docker stop eventdb
docker stop eventservices
docker stop eventservices2
docker stop eventsystem
docker stop dd-agent
docker stop dd-agent2
docker stop services-proxy
docker rm userdb
docker rm eventdb
docker rm eventservices
docker rm eventservices2
docker rm eventsystem
docker rm dd-agent
docker rm dd-agent2
docker rm services-proxy

eval $(docker-machine env mhs-demo1)

cd Databases
./deploy.sh
./deploy.sh
./deploy.sh
./deploy.sh
cd -

eval $(docker-machine env --swarm mhs-demo0)

docker run -d --name dd-agent --env="constraint:node==mhs-demo0" -h `hostname` -v /var/run/docker.sock:/var/run/docker.sock -v /proc/:/host/proc/:ro -v /sys/fs/cgroup/:/host/sys/fs/cgroup:ro \
-e API_KEY=802efe71870dec9e8c5df14426f3bb81 datadog/docker-dd-agent:latest

docker run -d --name dd-agent2 --env="constraint:node==mhs-demo1" -h `hostname` -v /var/run/docker.sock:/var/run/docker.sock -v /proc/:/host/proc/:ro -v /sys/fs/cgroup/:/host/sys/fs/cgroup:ro \
-e API_KEY=802efe71870dec9e8c5df14426f3bb81 datadog/docker-dd-agent:latest

cd Services
docker build --build-arg constraint:node==mhs-demo0 -t eventservices . 
docker run -d -i --name eventservices --net=my-net --env="constraint:node==mhs-demo0" -p 40402:8080 -p 9000:9000 eventservices:latest
cd -

cd Services
docker build --build-arg constraint:node==mhs-demo1 -t eventservices2 . 
docker run -d -i --name eventservices2 --net=my-net --env="constraint:node==mhs-demo1" eventservices2:latest
cd -

cd Nginx
docker build --build-arg constraint:node==mhs-demo0 -t services-proxy . 
docker run -d -i --name services-proxy --net=my-net --env="constraint:node==mhs-demo0" services-proxy:latest
cd -

cd System
docker build --build-arg constraint:node==mhs-demo0 -t eventsystem  .
docker run -t -i --name eventsystem --net=my-net --env="constraint:node==mhs-demo0" -p 40000:8080 eventsystem:latest

