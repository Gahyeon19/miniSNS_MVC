package com.example.mini_sns.postdomain.controller;

import com.example.mini_sns.postdomain.domain.Post;
import com.example.mini_sns.postdomain.domain.PostCreateRequestDto;
import com.example.mini_sns.postdomain.domain.PostDetailResponseDto;
import com.example.mini_sns.postdomain.domain.PostUpdateRequestDto;
import com.example.mini_sns.postdomain.service.PostService;
import com.example.mini_sns.userdomain.session.SessionConst;
import com.example.mini_sns.userdomain.session.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public String viewAllPosts(Model model) {          // 모든 게시글 조회
        model.addAttribute("allPosts", postService.getAllPosts());
        return "post/postAll";
    }

    @GetMapping("/{postId}")             // 해당 postId의 게시글 조회
    public String viewPostDetail(@PathVariable(name = "postId") int postId, Model model) {
        model.addAttribute("post", postService.getPostById(postId).get());
        return "post/postDetail";
    }

//    @GetMapping("/{useId}")           // 해당 유저의 게시글 조회
//    public String viewUserPost(@PathVariable(name = "useId") String useId, Model model) {
//        model.addAttribute("post", postService.viewAllPostWithUser(useId));
//        return "post/postAll";  // 고쳐
//    }

    @GetMapping("/add")
    public String createNewPostWithUser(Model model) {
        model.addAttribute("post", new Post());
        return "post/postAdd";
    }

    @PostMapping("/add")
    public String createNewPostWithUser(PostCreateRequestDto postDto) {    // 유저의 게시글 저장
        //세션 정보를 얻어온다. ==> 로그인된 상태인지 확인
        postService.createPostWithUser("ccc", postDto);
        return "redirect:/posts";
    }

    @GetMapping("/update/{postId}")                            // 유저의 게시글 수정
    public String updatePostWithUser(@PathVariable("postId") int postId, Model model) {
        model.addAttribute("post", postService.getPostDetail(postId));
        //session의 id와 post의 id가 일치하면 수정 가능

        return "post/postUpdate";
    }

    @PostMapping("/update/{postId}")                            // 유저의 게시글 수정
    public String updatePostWithUser(@PathVariable("postId") int postId,
                                     HttpServletRequest req,
                                     PostUpdateRequestDto postDto) {
        //세션 정보를 얻어온다. ==> 로그인된 상태인지 확인 ==> 'userId'와 'post의 userId'가 일치하면 수정 가능
        HttpSession session = req.getSession(false);
        String useId = "";
        if (session != null) {
            UserSession userSession = (UserSession) session.getAttribute(SessionConst.SESSION_NAME);
            useId = userSession.getUseId();
        }
        postService.updateBodyWithUser(postId, useId, postDto);

        return "redirect:/posts/{postId}";
    }

//    @RequestMapping("/delete/{postId}")        // getmapping + postmapping    // 유저의 게시글 삭제
//    public String deletePostWithUser(@PathVariable(name = "postId") int postId) {
//        postService.removePost(postId, "ccc");
//        return "redirect:/posts";
//    }

    @PostMapping("/delete/{postId}")        // getmapping + postmapping    // 유저의 게시글 삭제
    public String deletePostWithUser(@PathVariable(name = "postId") int postId) {
        //세션 정보를 얻어온다. ==> 로그인된 상태인지 확인 ==> 'userId'와 'post의 userId'가 일치하면 삭제 가능

        postService.removePost(postId, "ccc");      // userId가 'ccc'인 사람만 삭제할 수 있음
        return "redirect:/posts";
    }


    @PutMapping("/{postId}")
    public Post updatePostBody(@PathVariable(name = "postId") int postId,
                               @RequestBody Post post) {
        return postService.updatePost(post);
    }


}
