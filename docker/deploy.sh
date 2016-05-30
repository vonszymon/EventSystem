
docker-compose -p siusevents -f ./docker-compose.yml build
docker-compose -p siusevents -f ./docker-compose.yml up -d

echo "Waiting for the databases to boot..."
sleep 5
# copy file from host to docker; create dababase from file
docker cp ../DB/create_users.sql siusevents_user_database_1:/create.sql
docker exec siusevents_user_database_1 bash -c 'mysql --user=sius --password=sius_es events_users < /create.sql'

docker cp ../DB/create_events.sql siusevents_event_database_1:/create.sql
docker exec siusevents_event_database_1 bash -c 'mysql --user=sius --password=sius_es events_system < /create.sql'

