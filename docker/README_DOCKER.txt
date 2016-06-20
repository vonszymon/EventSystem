By móc odpalić dockera należy mieć w katalogach System i Services spakowaną instancje Pivotal'a - do pobrania z( https://drive.google.com/open?id=0B5c935hNCEjLSmU4emNZWTZaWEU ) 

********************AUTOMATYCZNY BUILD (na Linuksie)***************************

./start_env.sh

To tyle. Startuje to automatycznie wszystkie dockery, deploy aplikacji i kafke.

EventSystem : http://localhost:40000/EventSystem ( localhost dla linuksa, na windowsie ip wirtualki )
EventSystemServices : http://localhost:40402/eventservices

Konfiguracja Kafka Manager:

- Na host'cie uruchamiany w przegladarce localhost:9000
- wybieramy z menu Cluster->Add Cluster
- Cluster Name: myCluster, Cluster Zookeeper Hosts: localhost.
- Pozostale zostawiamy bez zmian i dajemy Save. Cluster jest skonfigurowany.

********************RĘCZNY BUILD***************************

=== Konfiguracja ===
Kontener dla EventSystem budujemy z katalogu System, dla EventSystemServices z katalogu Services.
By móc odpalić dockera należy mieć w tych katalogach spakowaną instancje Pivotal'a - do pobrania z( https://drive.google.com/open?id=0B5c935hNCEjLSmU4emNZWTZaWEU ) 

W katalogu System powinien ponadto znaleźć się skompilowany EventSystem.war, a w katalogu Services eventservices.war (aktualnie wrzucone są tam aktualne wersje apek, więc można z nich skorzystać)
np. odpalić/skompilować projekty przez STSa i w katalogu wtpwebapps (jest on w katalogu serwera pivotal z którego korzysta STS) odpalić w konsoli -> 
jar cvf EventSystem.war . (odpalić wewnątrz wtpwebapps/EventSystem)
jar cvf eventservices.war . (odpalić wewnątrz wtpwebapps/EventSystemServices)  

﻿=== Odpalenie kontenerów ===

Odpalenie baz:

./deploy.sh (z folderu Databases)

Przykładowe odpalenie kontenera dla EventSytem (z folderu System):

Przykładowe odpalenie kontenera dla EventSytemServices (z folderu Services):

docker build -t eventservices .
docker run -t -i --name eventservices --link eventdb:eventdb -p 40402:8080 -p 9000:9000 eventservices:latest

docker build -t eventsystem .
docker run -t -i --name eventsystem --link userdb:userdb --link eventservices:eventservices -p 40000:8080 eventsystem:latest

Po odpaleniu EventSystem możemy odnaleźć pod adresem : http://IP:40000/EventSystem
a serwisy : http://IP:40402/eventservices



