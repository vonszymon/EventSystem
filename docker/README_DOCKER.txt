Kontener dla EventSystem budujemy z katalogu System, dla EventSystemServices z katalogu Services.
By móc odpalić dockera należy mieć w tych katalogach spakowaną instancje Pivotal'a - do pobrania z( https://drive.google.com/open?id=0B5c935hNCEjLSmU4emNZWTZaWEU ) 

W katalogu System powinien ponadto znaleźć się skompilowany EventSystem.war, a w katalogu Services eventservices.war.
np. odpalić/skompilować projekty przez STSa i w katalogu wtpwebapps (jest on w katalogu serwera pivotal z którego korzysta STS) odpalić w konsoli -> 
jar cvf EventSystem.war . (odpalić wewnątrz wtpwebapps/EventSystem)
jar cvf eventservices.war . (odpalić wewnątrz wtpwebapps/EventSystemServices)  

Przed zbudowaniem tych warów należy przestawić adresy ip w projektach:
- w pliku application properties są adresy serwisów, należy podmienić na adres ip swojej wirtualki/hosta (* patrz na sam dół o jaki adres ip chodzi ) jeśli jest on różny od tego:
tai.services.event.uri=http://192.168.99.100:40402/eventservices/event
tai.services.comment.uri=http://192.168.99.100:40402/eventservices/commentary
- w plikach spring-servlet zarówno w EventSystem i EventSystemServices należy podmienić także ip w data source
(oczywiście do działania apki wymagane jest odpalanie dockerów z bazami, ale to już jest proste wystarczy odpalić z katalogu baz skrypt deploy.sh)

Przykładowe odpalenie kontenera dla EventSytem (z folderu System):

docker build -t eventsystem .
docker run -t -i -p 40000:8080 eventsystem:latest bash 
./tcruntime-ctl.sh my_server start ( nie zamykamy konsoli basha, dopóki działa kontener jest aktywny )

Przykładowe odpalenie kontenera dla EventSytemServices (z folderu Services):

docker build -t eventservices .
docker run -t -i -p 40402:8080 eventservices:latest bash 
./tcruntime-ctl.sh my_server start ( nie zamykamy konsoli basha, dopóki działa kontener jest aktywny )
/start.sh (odpalenie apache kafka)

Odpalenie Apache Kafka:

docker exec -i -t ID /bin/bash ( odpalamy drugiego basha na kontenrze serwisów, w miejsce ID id kontenera z serwisami )
cd /opt/kafka_2.11-0.10.0.0
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning ( odpalenie konsumenta, będą przez niego widoczne logowane eventy )

Po odpaleniu EventSystem możemy odnaleźć pod adresem : http://IP:40000/EventSystem
a serwisy : http://IP:40402/eventservices

*******************************************************************************************
Wymagany adres IP - na windowsie kontenery odpalane są na wirtualce z linuchem i używamy ip tej wirtualki (docker-machine ls),
na linuchu bodajże kontenery wstają bezpośrednio na hoście więc podajemy ip swojego peceta - podmieniamy tylko adresy IP, porty zostawiamy w spokoju.
Tak nawiasem, ja i Anrzej odpalamy na windowsie i z tego co widze mamy taki sam adres wirtualki.



