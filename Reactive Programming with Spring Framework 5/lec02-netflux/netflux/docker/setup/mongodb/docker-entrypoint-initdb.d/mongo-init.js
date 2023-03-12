// https://stackoverflow.com/questions/60522471/docker-compose-mongodb-docker-entrypoint-initdb-d-is-not-working
print('Start db creation #################################################################');
db.createUser({
    user: 'root',
    pwd: 'root',
    roles: [
        {
            role: 'readWrite',
            db: 'mongodemo',
        },
    ],
})

db = new Mongo().getDB("mongodemo");
// no need to create collections, collection are build by springboot on startup
db.createCollection('dummy', { capped: false });

print('END db creation #################################################################');
