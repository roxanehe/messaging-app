package com.ruoxin.messaging.dao;

import com.ruoxin.messaging.model.UserValidationCode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserValidationCodeDAO {

    @Insert("insert into user_validation_code (user_id, validation_code) values (#{userId}, #{validationCode})")
    void insert(UserValidationCode userValidationCode);

    @Select("select * from user_validation_code where user_id = #{userId}")
    UserValidationCode selectByUserId(@Param("userId") int userId);

    @Delete("DELETE FROM user_validation_code WHERE id = #{id}")
    void deleteById(@Param("id") int id);

    @Delete("DELETE FROM user_validation_code")
    void deleteAll();
}
