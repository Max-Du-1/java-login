package com.example.login.service.impl;

import com.example.login.common.ErrorCode;
import com.example.login.common.PageResult;
import com.example.login.entity.User;
import com.example.login.exception.BusinessException;
import com.example.login.repository.UserRepository;
import com.example.login.service.UserService;
import com.example.login.util.PasswordUtil;
import java.util.List;
import com.example.login.util.UuidUtil;
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
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL,"用户名或者密码错误");
        }

        if (!PasswordUtil.matches(password, user.getPassword())) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL,"用户名或者密码错误");
        }

        return user;
    }

    @Override
    public User loginByAccount(String account, String password) {
        // 调用 repository 层的方法，完成业务逻辑
        User user = userRepository.findByAccount(account);
        if (user == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL,"账号或者密码错误");
        }

        if (!PasswordUtil.matches(password, user.getPassword())) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL,"账号或者密码错误");
        }

        return user;
    }

    @Override
    public User register(String username,String password,Integer gender,String account, String phone, String email, Integer isAdmin){

        if (username == null || username.isBlank()) {
            throw new BusinessException(ErrorCode.USERNAME_EMPTY,"用户名不能为空");
        }
        User oldUser = userRepository.findByUsername(username);//username查重
        if(oldUser != null){
            throw new BusinessException(ErrorCode.USERNAME_EXISTS, "用户名已存在");
        }
        if (account == null || account.isBlank()) {
            throw new BusinessException(ErrorCode.ACCOUNT_EMPTY,"账号不能为空");
        }
        User oldAccount = userRepository.findByAccount(account);
        if (oldAccount != null) {
            throw new BusinessException(ErrorCode.ACCOUNT_EXISTS,"账号已存在");
        }
        if (gender == null) {
            gender = 0;
        }
        if (gender < 0 || gender > 2) {
            throw new BusinessException(ErrorCode.GENDER_ILLEGAL,"性别非法传参");
        }
        if (isAdmin == null) {
            isAdmin = 2;
        }
        if (isAdmin != 1 && isAdmin != 2) {
            isAdmin = 2;
        }

        if (phone != null && phone.isBlank()) {
            phone = null;  // "" 和 "   " 都当成没填
        }
        if (phone != null) {
            if (!phone.matches("^1[3-9]\\d{9}$")) {
                throw new BusinessException(ErrorCode.PHONE_INVALID,"非法手机号");
            }
            User oldPhoneUser = userRepository.findByPhone(phone);//phone查重
            if (oldPhoneUser != null) {
                throw new BusinessException(ErrorCode.PHONE_EXISTS,"手机号已存在");
            }
        }
        if (email !=null && email.isBlank() ) {
            email = null;
        }
        if (email !=null) {
            User oldEmail = userRepository.findByEmail(email);//email查重
            if (oldEmail != null) {
                throw new BusinessException(ErrorCode.EMAIL_EXISTS,"邮箱已存在");
            }
        }
        if (password == null || password.isBlank()) {
            throw new BusinessException(ErrorCode.PASSWORD_EMPTY,"密码不能为空");
        }
        // 生成32位随机ID
        String userId = UuidUtil.get32Uuid();
        // 传入 userId 保存到数据库
        // 明文密码 → BCrypt 哈希后再存库
        String encodedPassword = PasswordUtil.encode(password);
        userRepository.insertUser(username, encodedPassword, userId, gender, account, phone, email, isAdmin);
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

    @Override
    public boolean deleteUser(Integer id){
        User user = userRepository.findById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_UNEXISTS,"用户不存在或已删除");
        }
        userRepository.deleteUser(id);
        return true;
    }

    @Override
    public boolean deleteByUserId(String userId) {

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_UNEXISTS,"用户不存在或已删除");
        }

        userRepository.deleteByUserId(userId);
        return true;
    }

}