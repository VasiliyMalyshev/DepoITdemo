package com.malyshev.demojwt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Data
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "username")
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "current_lesson")
    private Lesson currentLesson;

    @CreatedDate
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created")
    private Date created;

    @LastModifiedDate
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated")
    private Date updated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

}
