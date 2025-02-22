package com.fitcoach.fitcoach.dao.entity;

import com.fitcoach.fitcoach.enums.Equipment;
import com.fitcoach.fitcoach.enums.Goal;
import com.fitcoach.fitcoach.enums.Level;
import com.fitcoach.fitcoach.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Entity
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)

@Table(name = "client")
public class Client extends Person {

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Enumerated(EnumType.STRING)
    private Level level;
    private Integer days_per_week;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<Chat> chats;


    @ManyToOne
    private Coach coach;

    @ManyToOne
    private Programme programme;

    @OneToMany(mappedBy = "client")
    Collection<Paiement> paiementsHistory;

    @OneToMany(mappedBy = "client")
    Collection<Review> reviews;

    public Client(Long id, String firstName, String lastName, String email,String profile, String avatar, Date date, Date date1, String password, Role role) {
        super(id,firstName,lastName,email,profile,avatar,date,date1,password,role);
        this.coach=null;
        this.programme=null;
        this.paiementsHistory=new ArrayList<>();
        this.reviews=new ArrayList<>();
        this.setAvatar("http://localhost:9090/content/logo.png");
    }


    @Override
    public String toString() {

        return super.toString()+"Client{" +
                "coach=" + coach +
                ", programme=" + programme +
                ", paiementsHistory=" + paiementsHistory +
                ", reviews=" + reviews +
                '}';
    }

    public Client(){
        super();
        this.setRole(Role.USER);

    }

}
