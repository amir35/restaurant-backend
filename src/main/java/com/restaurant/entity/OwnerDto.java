package com.restaurant.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link Student} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OwnerDto implements Serializable {
    private int ownerId;
    private String ownerName;
    private int age;
    private String username;
    private String email;
    private Long mobile;
    private String location;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDto entity = (OwnerDto) o;
        return Objects.equals(this.ownerId, entity.ownerId) &&
                Objects.equals(this.ownerName, entity.ownerName) &&
                Objects.equals(this.age, entity.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, ownerName, age);
    }

}