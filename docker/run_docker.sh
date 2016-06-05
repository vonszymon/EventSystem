# event_server -> image .cont container
sudo docker run -d -it --name event_servers.cont event_server bash
sudo docker start event_servers.cont
sudo docker run -it event_server bash
