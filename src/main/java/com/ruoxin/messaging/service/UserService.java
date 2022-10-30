package com.ruoxin.messaging.service;

import java.util.Date;
import java.util.List;

import com.ruoxin.messaging.dao.UserDAO;
import com.ruoxin.messaging.dao.UserValidationCodeDAO;
import com.ruoxin.messaging.enums.Gender;
import com.ruoxin.messaging.enums.Status;
import com.ruoxin.messaging.exception.MessagingSystemException;
import com.ruoxin.messaging.model.User;
import com.ruoxin.messaging.model.UserValidationCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserValidationCodeDAO userValidationCodeDAO;

    public void register(String username,
                         String password,
                         String repeatPassword,
                         String email,
                         String address,
                         String nickname,
                         Gender gender) throws Exception { // signature
        // validation
        if (!password.equals(repeatPassword)) {
            throw new MessagingSystemException(Status.PASSWORD_NOT_MATCHED);
        }
        if (password.length() < 16) {
            throw new MessagingSystemException(Status.PASSWORD_TOO_SHORT);
        }

         List<User> users = this.userDAO.selectByUsername(username);
         if (users != null && !users.isEmpty()) {
             throw new MessagingSystemException(Status.USERNAME_ALREADY_EXISTS);
         }

         users = this.userDAO.selectByEmail(email);
         if (users != null && !users.isEmpty()) {
             throw new MessagingSystemException(Status.EMAIL_ALREADY_EXISTS);
         }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setGender(gender);
        user.setAddress(address);
        user.setValid(false);
        user.setRegisterTime(new Date());
        this.userDAO.insert(user);

        String validationCode = RandomStringUtils.randomNumeric(6);
        UserValidationCode userValidationCode = new UserValidationCode();
        userValidationCode.setUserId(user.getId());
        userValidationCode.setValidationCode(validationCode);
        this.userValidationCodeDAO.insert(userValidationCode);

        // send an email with validationCode to `email`
        // refer homework
    }

    public void activate(String username, String validationCode) throws Exception {
        List<User> users = this.userDAO.selectByUsername(username);
        if (users == null || users.isEmpty()) {
            throw new MessagingSystemException(Status.USER_NOT_EXISTS);
        }
        User user = users.get(0);

        UserValidationCode userValidationCode = this.userValidationCodeDAO.selectByUserId(user.getId());
        if (userValidationCode == null) {
            throw new MessagingSystemException(Status.VALIDATION_CODE_NOT_EXISTS);
        }
        if (!userValidationCode.getValidationCode().equals(validationCode)) {
            throw new MessagingSystemException(Status.VALIDATION_CODE_IS_WRONG);
        } else {
            this.userDAO.updateToValid(user.getId());
            this.userValidationCodeDAO.deleteById(userValidationCode.getId());
        }
    }
}
