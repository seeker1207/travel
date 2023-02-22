package com.example.travel.domain.user;

import com.example.travel.domain.BaseEntity;
import com.example.travel.domain.travel.Travel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class MyUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @OneToMany(mappedBy = "traveler", orphanRemoval = true)
    private List<Travel> travels;

    @Builder
    public MyUser(String email, String password, String nickname, List<Travel> travels) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        if (travels == null) {
            this.travels = new ArrayList<>();
        } else {
            this.travels = travels;
        }
    }

    public void makeTravelPlan(Travel travel) {
        travels.add(travel);
        travel.setTraveler(this);
    }
}
