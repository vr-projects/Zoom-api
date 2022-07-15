package com.userOnboard.mapper;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.userOnboard.model.User;

@Mapper
public interface UserMapper {
@Select("select * from t_user1")
	List<User> getAllUsers();

@Select( value = "{CALL getUser(#{user_id,mode=IN},#{user_id, mode=OUT, jdbcType=INTEGER},#{first_name, mode=OUT, jdbcType=VARCHAR})}")
@Options(statementType = StatementType.CALLABLE)
@ResultType(User.class)
void getUserByStoredProcedure(User user);



@Select("select json_object('user_id', tu.user_id, 'email_id', tu.email_id, 'first_name', tu.first_name, 'middle_name', tu.middle_name, 'last_name',tu.last_name ,'contacts', (SELECT JSON_ARRAYAGG(JSON_OBJECT('contact_id', contact_id, 'address', address)) from t_contact_info tci where tci.user_id = tu.user_id  )) col1 from t_user1 tu ")

List<User> usersByjson();

}
