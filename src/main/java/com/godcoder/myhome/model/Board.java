package com.godcoder.myhome.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2,max=30,message = "title must be between 2 and 30 characters")
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id") // referenced 생략가능
    @JsonIgnore
    private User user;
    //many쪽에 외래키가 있다. joinColumn에 적어준다


}
