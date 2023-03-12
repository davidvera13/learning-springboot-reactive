// https://stackoverflow.com/questions/60522471/docker-compose-mongodb-docker-entrypoint-initdb-d-is-not-working
print('Start db creation #################################################################');
db.createUser({
    user: 'root',
    pwd: 'root',
    roles: [
        {
            role: 'readWrite',
            db: 'innonetwork',
        },
    ],
})

db = new Mongo().getDB("innonetwork");
db.createCollection('innonetwork', { capped: false });
db.createCollection('dummy', { capped: false });

print('END db creation #################################################################');
