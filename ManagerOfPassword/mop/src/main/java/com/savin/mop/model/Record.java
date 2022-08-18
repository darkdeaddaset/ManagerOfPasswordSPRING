package com.savin.mop.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "records", schema = "public")
@Data
public class Record {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "url")
    private String url;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "record_user_id_fkey"))
    private User user;
}
