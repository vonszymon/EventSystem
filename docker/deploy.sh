
docker-compose -p siusevents -f ./docker-compose.yml build
docker-compose -p siusevents -f ./docker-compose.yml up -d

echo "Waiting for the database to boot..."
sleep 5
mysql --host=127.0.0.1 --port=3301 --user=sius --password=sius_es events_system < ../DB/create.sql
