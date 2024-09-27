package org.example.config;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class CasandraConfig extends AbstractCassandraConfiguration {
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;
    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;
    @Value("${spring.data.cassandra.port}")
    private int port;
    @Value("${spring.data.cassandra.local-datacenter}")
    private String localDatacenter;
//    @Value("${spring.data.cassandra.username}")
//    private String username;
//    @Value("${spring.data.cassandra.password}")
//    private String password;

    @Override
    protected String getKeyspaceName() {
//        return "keyspace";
        return keyspace;
    }

    @Override
    public String getContactPoints() {
//        return "127.0.0.1";
        return contactPoints;
    }

    @Override
    public int getPort() {
//        return 9042;
        return port;
    }

    @Override
    public String getLocalDataCenter() {
        return localDatacenter;  // Adjust this to match your Cassandra cluster setup
    }

    //--------------
    @Bean
    @Primary
    public CqlSessionFactoryBean session() {
        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(getContactPoints());
        session.setKeyspaceName(getKeyspaceName());
        session.setLocalDatacenter(localDatacenter);

//        session.setUsername(username);  // If authentication is necessary
//        session.setPassword(password);

        session.setSchemaAction(getSchemaAction());
        session.setKeyspaceCreations(getKeyspaceCreations());
        session.setConverter(cassandraConverter());

        return session;
    }

    @Bean
    @Override
    public CassandraMappingContext cassandraMapping() {
        return new BasicCassandraMappingContext();

//        BasicCassandraMappingContext mappingContext = new BasicCassandraMappingContext();
//        mappingContext.setUserTypeResolver(
//                new SimpleUserTypeResolver(cluster().getObject(), getKeyspaceName()));
//        return mappingContext;
    }

    @Bean
    @Override
    public CassandraConverter cassandraConverter() {
        return new MappingCassandraConverter(cassandraMapping());
    }

    @Bean
    public CassandraAdminTemplate cassandraTemplate() {
//        return new CassandraAdminTemplate(session().getObject(), converter());

        CassandraAdminTemplate template = new CassandraAdminTemplate(session().getObject());
        template.createTable(true, CqlIdentifier.fromCql("users"), User.class, new HashMap<>());

        return template;
//        return new CassandraAdminTemplate(session().getObject());
    }

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer() {
        return builder -> builder.withKeyspace(getKeyspaceName());
    }

    //-----------
    @Override
    public SchemaAction getSchemaAction() {
//        return SchemaAction.RECREATE_DROP_UNUSED;
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(
                CreateKeyspaceSpecification.createKeyspace(keyspace).ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication());
    }

    @Override
    protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
        return Stream.of(User.class).collect(Collectors.toSet());
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"org.example.model"};
    }
}