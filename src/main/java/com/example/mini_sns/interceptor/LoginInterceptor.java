package com.example.mini_sns.interceptor;

import com.example.mini_sns.userdomain.session.SessionConst;
import com.example.mini_sns.userdomain.session.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request uri : {}", request.getRequestURI());
        String redirectUri = request.getRequestURI();
        //로그인 여부 확인
        HttpSession session = request.getSession(false);//로그인 되어 있으면 session 정보 get, 로그인 안되어 있으면 null
        if (session != null) {
            //세션 정보를 가져왔으면
            Object objSession = session.getAttribute(SessionConst.SESSION_NAME);
            if (objSession != null && objSession instanceof UserSession) {  // 세션을 가져왔는데 비어있으면
                UserSession userSession = (UserSession) objSession;
                log.info("===== UserSession : {}", userSession);
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/" + "/login?redirectURI=" + redirectUri);
        return false;
    }
}
