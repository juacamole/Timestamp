package ch.kuan.timestamp.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "tb_stamp")
public class Stamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 20)
    @NotEmpty
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
