package com.example.mini_sns.userdomain.controller;

import com.example.mini_sns.userdomain.dto.UserLoginDto;
import com.example.mini_sns.userdomain.service.LoginService;
import com.example.mini_sns.userdomain.domain.User;
import com.example.mini_sns.userdomain.session.SessionConst;
import com.example.mini_sns.userdomain.session.UserSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto,
                        @RequestParam(name = "redirectUri", defaultValue = "/posts") String redirectUri,
                        HttpServletRequest req, HttpServletResponse resp) {
        User loginUser = loginService.login(userLoginDto);
        log.info("loginUser : {}", loginUser);
        if (loginUser == null) {    //로그인 실패
            return "redirect:/login";
        }

        //로그인 성공 : 세션 생성
        UserSession userSession = new UserSession();
        userSession.setUseId(loginUser.getUseId());
        userSession.setUserName(loginUser.getUserName());

        HttpSession session = req.getSession(true);  // 없으면 생성
        session.setAttribute(SessionConst.SESSION_NAME, userSession);
//        session.setAttribute("uuid", uuid);

        //로그인 성공한 세션 정보를 쿠키에 보내기
        Cookie cookie = new Cookie(SessionConst.COOKIE_NAME, SessionConst.SESSION_NAME);
        cookie.setPath("/");
        cookie.setMaxAge(600);  // 세션이 사라지면 쿠키를 얼마동안 유지할 것인지 (600초 = 10분)
        resp.addCookie(cookie);

        return "redirect:" + redirectUri;      // login ==> /posts , parameter로 redirectUri를 가지고 온 경우에는

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie cookie = new Cookie(SessionConst.COOKIE_NAME, null);
        cookie.setMaxAge(0);    // 즉시 무효화
        resp.addCookie(cookie);

        return "redirect:/login";
    }

}
