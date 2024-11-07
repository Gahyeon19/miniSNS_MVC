package com.example.mini_sns.userdomain.service;
import com.example.mini_sns.userdomain.domain.User;
import com.example.mini_sns.userdomain.dto.UserLoginDto;
import com.example.mini_sns.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    //DB의 사용자 정보랑 비교하고 싶어 ...
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("minisns");
//    EntityManager em = emf.createEntityManager();
//    EntityTransaction tx = em.getTransaction();

    private final UserRepository userRepository;
    public boolean isLoginSuccess(User user) {
        if (user != null && StringUtils.hasText(user.getUseId()) && StringUtils.hasText(user.getPassword())) {
            if (user.getUseId().equals("ccc") && user.getPassword().equals("333")) {
                return true;
            }
            return false;
        }
        return false;
    }

    public User login(UserLoginDto userDto) {
        User user = userRepository.findByUseId(userDto.getUseId());
        if (user != null && user.getPassword().equals(userDto.getPassword())) {
            return user;
        }
        return null;
    }
}
