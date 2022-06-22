package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;


    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();
    //many to many


    //cascade 데이터에 반영, remove는 user가 삭제됐을때 게시글들도 같이 삭제
    //orphanRemoval 기본값 false, true 부모가 없는 데이터는 지운다?
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
   //블팔요한 데이터를 가져오는 걸 막기위해 fetchtype lazy해줌. many로 끝나는것들은 lazy로 해주기
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();


}
