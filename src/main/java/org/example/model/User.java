package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table("users")
public class User {
    @Id
    @PrimaryKey
    private UUID id;
    ;
    private String name;

    public User(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }
}
