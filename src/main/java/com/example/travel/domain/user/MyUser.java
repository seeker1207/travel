package com.example.travel.domain.user;

import com.example.travel.domain.BaseEntity;
import com.example.travel.domain.travel.Travel;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MyUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @OneToMany(mappedBy = "myUser", orphanRemoval = true)
    private List<Travel> travels;
}
