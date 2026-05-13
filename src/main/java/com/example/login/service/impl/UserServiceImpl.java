package com.example.login.service.impl;

import com.example.login.common.PageResult;
import com.example.login.entity.User;
import com.example.login.repository.UserRepository;
import com.example.login.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // 注入 repository 层
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        // 调用 repository 层的方法，完成业务逻辑
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User register(String username,String password){
        User oldUser = userRepository.findByUsername(username);
        if(oldUser != null){
            return null;
        }
        userRepository.insertUser(username,password);
        return userRepository.findByUsername(username);
    }

    @Override
    public PageResult<User> pageUsers(int pageNum, int pageSize) {
        // 边界约束，避免恶意大 offset / 超大 pageSize
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }
        long total = userRepository.countAll();
        int offset = (pageNum - 1) * pageSize;
        List<User> list = userRepository.findPage(offset, pageSize);
        PageResult<User> page = new PageResult<>();
        page.setList(list);
        page.setTotal(total);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return page;
    }

}