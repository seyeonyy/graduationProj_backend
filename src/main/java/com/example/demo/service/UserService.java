package com.example.demo.service;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(User user) {

        validateDuplicateMember(user); //중복 회원 검증
        userRepository.save(user);
        return user.getId();
    }
    @Transactional
    public Long update(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Transactional  // Add this annotation to allow fetching and updating in one transaction.
    public User modifyNickname(String userId, String newNickname) {
        User user = findUserByUserId(userId);

        if (user != null) {
            user.setNickName(newNickname);
            update(user);  // Save changes.
        }

        return user;  // Return updated user. Might be null if there was no such user.
    }

    public User findUserByUserId(String userId) {
        List<User> users = userRepository.findByUserId(userId);
        if (!users.isEmpty()) {
            return users.get(0);  // 첫 번째 매칭되는 사용자 반환
        } else {
            return null;  // 매칭되는 사용자가 없으면 null 반환
        }
    }

    public User findUserById(String Id) {
        List<User> users = userRepository.findById(Id);
        System.out.println("service ");
        if (!users.isEmpty()) {
            return users.get(0);  // 첫 번째 매칭되는 사용자 반환
        } else {
            return null;  // 매칭되는 사용자가 없으면 null 반환
        }
    }

    private void validateDuplicateMember(User user) {
        List<User> findUsers = userRepository.findByUserId(user.getUserId());
        if(!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

}


