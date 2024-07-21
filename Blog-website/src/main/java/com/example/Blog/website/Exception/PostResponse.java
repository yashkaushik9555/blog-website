package com.example.Blog.website.Exception;

import java.util.List;

import com.example.Blog.website.Dto.PostDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
  private List<PostDto>content;
  private boolean lastPage;
  private Long totalPage;
  private int pageSize;
}
