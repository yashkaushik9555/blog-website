package com.example.Blog.website.Entity;



import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@NoArgsConstructor
@Data
public class Users {
  @Id
  @GeneratedValue(strategy  = GenerationType.IDENTITY)
  
  @Column(name = "user_id",nullable = false)
  private long id;
  
  @Column(name="userName")
  private String name;
  
  @Column(name="userEmail")
  
  private String emailAddress;
  
  @Column(name="password")
  private String password;
  
  @Column(name="mobile",length = 10)
  private String mobileNumber;
  
  @Column(name="about",length = 200)
  private String about;
  
  @Temporal(TemporalType.DATE)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
  private Date date;
  
  @Column(name="is_active")
  private String isActive;
  
  @CreatedBy
  private String createdby;
  
  @LastModifiedBy
  private String modifiedBy;
  
  @OneToMany(mappedBy="user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  private Set<Posts> posts=new TreeSet<>();
}
