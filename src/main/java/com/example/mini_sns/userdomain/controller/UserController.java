package com.example.mini_sns.userdomain.controller;

import com.example.mini_sns.userdomain.domain.User;
import com.example.mini_sns.userdomain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        //세션 정보를 얻어온다. ==> 로그인된 상태인지 확인 ==> 사용자가 admin일 때만
        return userService.getAllusers();
    }

    ////////
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/userRegister";
    }

    @PostMapping("/register")
    public String createUser(@RequestBody User user) {
        userService.createNewUser(user);
        return "redirect:/login";
    }

    @GetMapping("/{useId}")
    public User getUser(@PathVariable("useId") String useId) {


        return userService.getOneUser(useId);
    }

    @DeleteMapping("/{useId}")
    public void deleteUser(@PathVariable("useId") String useId) {
        //세션 정보를 얻어온다. ==> 로그인된 상태인지 확인 ==> 본인이 맞는지 확인해서 본인인 경우에만 탈퇴 가능

        userService.removeUser(useId);
    }

    @PutMapping("/{useId}")
    public User updateUser(@PathVariable("useId") String useId, @RequestBody User user) {
        //세션 정보를 얻어온다. ==> 로그인된 상태인지 확인 ==> 본인이 맞는지 확인해서 본인인 경우에만 수정 가능


        user.setUseId(useId);
        return userService.updateUserPassword(user);
    }
}
