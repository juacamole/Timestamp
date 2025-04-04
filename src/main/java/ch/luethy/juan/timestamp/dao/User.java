package ch.luethy.juan.timestamp.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(length = 50, unique = true)
    @Size(min = 2, max = 50)
    private String username;

    @NotEmpty
    @Column(length = 20)
    @Size(min = 1, max = 20)
    private String firstname;

    @NotEmpty
    @Column(length = 20)
    @Size(min = 1, max = 20)
    private String lastname;

    @NotEmpty
    @Column(length = 29)
    private byte workhours;

    @NotEmpty
    @Column(length = 20)
    private byte workminutes;

}
