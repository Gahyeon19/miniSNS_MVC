package com.example.mini_sns.postdomain.repository;

import com.example.mini_sns.postdomain.domain.DynamicSearchCond;
import com.example.mini_sns.postdomain.domain.Post;

import java.util.List;

public interface PostRepositoryQuery {
    List<Post> getAllPostWithLikesAndWriter(DynamicSearchCond searchCond);
}
