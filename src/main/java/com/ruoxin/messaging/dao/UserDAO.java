package com.ruoxin.messaging.dao;

import java.util.List;

import com.ruoxin.messaging.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

// Data Access Object
// service -> dao
@Repository
@Mapper // MyBatis
public interface UserDAO {
    // SQL // user.getUsername() @ -> annotation
    @Insert("insert into user (username, nickname, email, address, gender, password, register_time, is_valid) " +
            "values (#{username}, #{nickname}, #{email}, #{address}, #{gender}, #{password}, #{registerTime}, " +
            "#{isValid});")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(User user);

    @Select("select * from user where username=#{username}")
    List<User> selectByUsername(@Param("username") String username);

    @Select("select * from user where email=#{email}")
    List<User> selectByEmail(@Param("email") String email);

    @Update("UPDATE user SET is_valid=1 WHERE id=#{userId}")
    void updateToValid(@Param("userId") int userId);

    @Delete("DELETE from user")
    void deleteAll();
}
