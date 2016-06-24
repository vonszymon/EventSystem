By móc odpalić dockera należy mieć w katalogach System i Services spakowaną instancje Pivotal'a - do pobrania z( https://drive.google.com/open?id=0B5c935hNCEjLSmU4emNZWTZaWEU )
oraz spakowane aplikacje - umieszczone w projekcie; można też zbudować własnoręcznie:

jar cvf EventSystem.war . (odpalić wewnątrz wtpwebapps/EventSystem)
jar cvf eventservices.war . (odpalić wewnątrz wtpwebapps/EventSystemServices)   

******************** Odpalenie kontenerów na localhoście ***************************

sudo ./start_env_localhost.sh

To tyle. Startuje to automatycznie wszystkie dockery, deploy aplikacji i kafke.

EventSystem : http://localhost:40000/EventSystem
EventSystemServices : http://localhost:40402/eventservices

Konfiguracja Kafka Manager:

- Na host'cie uruchamiany w przegladarce localhost:9000
- wybieramy z menu Cluster->Add Cluster
- Cluster Name: myCluster, Cluster Zookeeper Hosts: localhost.
- Pozostale zostawiamy bez zmian i dajemy Save. Cluster jest skonfigurowany.

******************** Odpalenie kontenerów na klastrze Docker Swarm ***************************

Wymagana instalacja docker-machine oraz virtualboxa ( nie daje głowy, bo już miałem virtualboxa ale prawdopodobnie trzeba ręcznie zainstalować ).

sudo ./setup_swarm_env.sh  ( odpalamy tylko raz na swoim kompie, tworzy wirtualki na których będą odpalane kontenery )

Zwracamy uwage przy wywołaniu tego skryptu czy nie pluje się, że mu się nasz virtualbox nie podoba. U mnie się pluł, musiałem zrobić jego update.

sudo ./start_env_swarm.sh  ( właściwy skrypt do odpalania kontenerów - przy pierwszym odpaleniu może trochę to trwać, ze względu na zasysanie ubuntu na wirtualki )

W skrypcie na początku usuwane są aktualne kontenery na podstawie ich nazwy. Może ich nie być wtedy rzuca errorami że ich nie ma. Nie przejmujemy się.

Uwaga! Na samym początku skryptu restartowane są wirtualki. Restart stara się uruchomić je z takimi samymi IP jak wcześniej.
U mnie nie było żadnych problemów, ale jeśli wstałyby z innymi IP apka może nie działać ( przy tworzeniu wirtualek, jako argument leci IP wirtualki keystora,
dlatego jeśli się zmieni to lipa; pewnie można to ustawić ponownie przy restarcie ale nie ogarniałem tego ).

EventSystem : http://IP_WIRTUALKI_mhs-demo0:9000:40000/EventSystem (docker-machine ls)
EventSystemServices : http://IP_WIRTUALKI_mhs-demo0:9000:40402/eventservices

Konfiguracja Kafka Manager:

- Na host'cie uruchamiany w przegladarce IP_WIRTUALKI_mhs-demo0:9000
- wybieramy z menu Cluster->Add Cluster
- Cluster Name: myCluster, Cluster Zookeeper Hosts: localhost.
- Pozostale zostawiamy bez zmian i dajemy Save. Cluster jest skonfigurowany.

Odpalenie klienta Kafki:

docker exec -i -t ID /bin/bash ( odpalamy drugiego basha na kontenerze serwisów, w miejsce ID id kontenera z serwisami )
cd /opt/kafka_2.11-0.10.0.0
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning ( odpalenie konsumenta, będą przez niego widoczne logowane eventy )


