package com.example.mini_sns.postdomain.controller;

import com.example.mini_sns.postdomain.domain.Post;
import com.example.mini_sns.postdomain.domain.PostCreateRequestDto;
import com.example.mini_sns.postdomain.domain.PostDetailResponseDto;
import com.example.mini_sns.postdomain.domain.PostUpdateRequestDto;
import com.example.mini_sns.postdomain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public String viewAllPost(Model model) {          // 모든 게시글 조회
        model.addAttribute("allPosts", postService.getAllPosts());
        return "post/postAll";
    }

    @GetMapping("/{postId}")
    public String viewPostDetail(@PathVariable(name = "postId") int postId, Model model) {   // 해당 id의 게시글 조회
        model.addAttribute("post", postService.getPostById(postId).get());
        return "post/postDetail";
    }

    @GetMapping("/add")
    public String addPost(Model model) {
        model.addAttribute("post", new Post());
        return "post/postAdd";
    }

    @PostMapping("/add")
    public String createNewPostWithUser(PostCreateRequestDto postDto) {    // 유저의 게시글 저장
        postService.createPostWithUser("ccc", postDto);
        return "redirect:/posts";
    }

    @GetMapping("/update/{postId}")
    public String updatePost(@PathVariable("postId") int postId, Model model) {
        model.addAttribute("post", postService.getPostDetail(postId));
        return "post/postUpdate";
    }

    @PostMapping("/update/{postId}")                            // 유저의 게시글 수정
    public String updatePostWithUser(@PathVariable("postId") int postId, PostUpdateRequestDto postDto) {
        postService.updateBodyWithUser(postId, "ccc", postDto);
        return "redirect:/posts/{postId}";
    }


    @PutMapping("/{postId}")
    public Post updatePostBody(@PathVariable(name = "postId") int postId,
                               @RequestBody Post post) {
        return postService.updatePost(post);
    }

    @DeleteMapping("/{postId}")
    public void deletePostWithUser(@PathVariable(name = "postId") int postId,
                                   @PathVariable(name = "useId") String useId) {    // 유저의 게시글 삭제
        postService.removePost(postId, useId);
    }

}
