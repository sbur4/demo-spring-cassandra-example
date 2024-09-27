//-- To check if the keyspace exists:
//SELECT * FROM system_schema.keyspaces WHERE keyspace_name = 'demo_schema';

//-- To create the keyspace if it does not exist:
//CREATE KEYSPACE IF NOT EXISTS demo_schema WITH replication = {'class': 'NetworkTopologyStrategy', 'datacenter1' : 1};

CREATE KEYSPACE demo_schema WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};