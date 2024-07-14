package com.example.Blog.website.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
public class Posts {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="post_id")
	private Long id;
	
	@Column(name="post_content")
	private String content;
	
	@Column(name="post_title")
	private String title;
	
	@Column(name="post_Description")
	private String description;
	
	@Column(name="is_active")
	private String isActive;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name="create_date")
	private Date createdDate;
	
	
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
	private Date modifiedDate;
	@Column(name="post_imageName")
	private String imageName;
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	@ManyToOne
	@JoinColumn(name="cate_id")
	private Categories category;
}
