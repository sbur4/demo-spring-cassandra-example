get http://localhost:8080/user/id
get http://localhost:8080/users
post http://localhost:8080/user/name
put http://localhost:8080/user/id&name
delete http://localhost:8080/user/id

docker=> casandra:latest
port:9042

cassandra_docker

[//]: # (CASSANDRA_USER=root)

[//]: # (CASSANDRA_PASSWORD=root)

[//]: # (delermando/docker-cassandra-web)

[//]: # (port:3000)

[//]: # ()
[//]: # (cassandra_ui_docker)

[//]: # ()
[//]: # (CASSANDRA_HOST_IPS: 127.0.0.1)

[//]: # (CASSANDRA_PORT: 9042)

[//]: # (CASSANDRA_USER: cassandra)

[//]: # (CASSANDRA_PASSWORD: cassandra)

-------------------
#spring.data.cassandra.contact-points=127.0.0.1
#spring.data.cassandra.contact-points=localhost
spring.data.cassandra.contact-points=localhost:9042
spring.data.cassandra.username=root
spring.data.cassandra.password=root
#spring.data.cassandra.keyspace-name=your_keyspace_name
spring.data.cassandra.keyspace=demo_schema
spring.cassandra.local-datacenter=datacenter1
spring.data.cassandra.port=9042
spring.data.cassandra.schema-action=CREATE_IF_NOT_EXISTS

spring.data.cassandra.connect-timeout-millis=10000ms
spring.data.cassandra.read-timeout-millis=10000ms
spring.data.cassandra.connect-timeout=10000ms
spring.data.cassandra.read-timeout=10000ms
spring.data.cassandra.pool.pool-timeout=10000ms

