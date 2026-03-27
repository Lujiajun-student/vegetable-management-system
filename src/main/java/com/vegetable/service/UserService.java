package com.vegetable.service;

import com.vegetable.entity.User;
import com.vegetable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.math.BigDecimal;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final String CURRENT_USER_KEY = "currentUser";

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("邮箱已被注册");
        }
        
        user.setPassword(encryptPassword(user.getPassword()));
        user.setBalance(BigDecimal.ZERO); // 新用户初始余额为0
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        if (!user.getPassword().equals(encryptPassword(password))) {
            throw new RuntimeException("密码错误");
        }
        
        // 保存用户到会话
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute(CURRENT_USER_KEY, user);
        
        return user;
    }

    public void logout() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute(CURRENT_USER_KEY);
    }

    public BigDecimal getCurrentUserBalance() {
        User currentUser = getCurrentUser();
        return currentUser.getBalance();
    }

    public User getCurrentUser() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        User sessionUser = (User) session.getAttribute(CURRENT_USER_KEY);
        if (sessionUser == null) {
            throw new RuntimeException("用户未登录");
        }
        // 每次都从数据库查最新的用户信息
        return userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    @Transactional
    public void deductBalance(User user, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("扣除金额必须大于0");
        }
        
        User currentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        if (currentUser.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("余额不足");
        }
        
        currentUser.setBalance(currentUser.getBalance().subtract(amount));
        userRepository.save(currentUser);
    }

    @Transactional
    public void addBalance(User user, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("充值金额必须大于0");
        }
        
        User currentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        currentUser.setBalance(currentUser.getBalance().add(amount));
        userRepository.save(currentUser);
    }
} 