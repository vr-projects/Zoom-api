package com.userOnboard.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.tree.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.userOnboard.model.User;

import lombok.SneakyThrows;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcCall simplejdbcCall;

	@Override
	public List<User> getAllUsers(int limit, int offset){

	//return	jdbcTemplate.query("SELECT * FROM t_user1 LIMIT " +limit+ ","+ offset+";", new BeanPropertyRowMapper<User>(User.class));
	
		 String JSON_QUERY = "select json_object(\r\n" +
				  "	'user_id', tu.user_id, 'email_id', tu.email_id, 'first_name', tu.first_name, \r\n"
				  + "	'middle_name', tu.middle_name, 'last_name',tu.last_name ,\r\n" +
				  "	'contacts', (SELECT JSON_ARRAYAGG(JSON_OBJECT('contact_id', contact_id, 'address', address)) \r\n"
				  +
				  "				   from t_contact_info tci where tci.user_id = tu.user_id \r\n"
				  + "				  )\r\n" + "	) col1\r\n" +
				  "from t_user1 tu";
		
		List<User> users = new ArrayList<User>();
		  jdbcTemplate.query(JSON_QUERY, new RowCallbackHandler() {
				public void processRow(ResultSet resultSet) throws SQLException {
					
					do {
						String user_json_str = resultSet.getString(1);
						users.add(unmarshall(user_json_str, User.class));
						
						System.out.println(user_json_str);
					}while (resultSet.next());
				}
			});
	return users;
	}

	@Override
	public int saveUser(User user) {
		//return jdbcTemplate.update("INSERT INTO t_user1 (email_id,fireBase_id,first_name,middle_name,last_name) VALUES (?,?,?,?,?)",new Object[] {user.getEmail_id(),user.getFireBase_id(),user.getFirst_name(),user.getMiddle_name(),user.getLast_name()});
		return jdbcTemplate.update("call saveUsersData (?,?,?,?,?)",user.getEmail_id(),user.getFireBase_id(),user.getFirst_name(),user.getMiddle_name(),user.getLast_name());
	}

	@Override
	@SneakyThrows
	public User getById(int id) throws Exception {
		
//working		//  return jdbcTemplate.queryForObject("SELECT * from t_user1 WHERE user_id=?",  new BeanPropertyRowMapper<User>(User.class), id); 
//working		//return jdbcTemplate.update("call getUser (?)", id);
//working		
		/*this.simplejdbcCall = new SimpleJdbcCall(jdbcTemplate)
		 * .withProcedureName("getUser"); User user = new User(); try {
		 * SqlParameterSource in = new
		 * MapSqlParameterSource().addValue("userId",Integer.valueOf(id)); Map<String,
		 * Object> out = simplejdbcCall.execute(in);
		 * 
		 * user.setUser_id(id); user.setFirst_name((String)out.get("name")); } catch
		 * (Exception e) { throw new Exception(); }
		 * 
		 * 
		 * return user;
		 */
	//working	
		
		  String JSON_QUERY = "select json_object(\r\n" +
		  "	'user_id', tu.user_id, 'email_id', tu.email_id, 'first_name', tu.first_name, \r\n"
		  + "	'middle_name', tu.middle_name, 'last_name',tu.last_name ,\r\n" +
		  "	'contacts', (SELECT JSON_ARRAYAGG(JSON_OBJECT('contact_id', contact_id, 'address', address)) \r\n"
		  +
		  "				   from t_contact_info tci where tci.user_id = tu.user_id \r\n"
		  + "				  )\r\n" + "	) col1\r\n" +
		  "from t_user1 tu where user_id=?";
		  
			
			  return jdbcTemplate.query(JSON_QUERY, (ResultSet rs) -> { rs.next(); return
			  unmarshall(rs.getString(1), User.class); }, id);
			 
		 
			/*
			 * List<User> users = new ArrayList<User>(); jdbcTemplate.query(JSON_QUERY, new
			 * RowCallbackHandler() { public void processRow(ResultSet resultSet) throws
			 * SQLException { while (resultSet.next()) { String user_json_str =
			 * resultSet.getString(1); users.add(unmarshall(user_json_str, User.class));
			 * 
			 * System.out.println(user_json_str); } } });
			 * 
			 * return new User();
			 */
	}
	
	@SneakyThrows
	private  <T> T unmarshall(String json_body, Class<T> clazz){
		T t = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			 t= objectMapper.readValue(json_body, clazz);
		} catch (Exception e) {
			// TODO: handle exception
		}
	   return t;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<User> getUsersByfilter(Map<String, Object> filters) {
		
		String sql = "SELECT * FROM t_user1 WHERE ";
		boolean conditionAdded = false;
		List<Object> args = new ArrayList<Object>();
		/*
		 * if (filters.containsKey("first_name")) { sql += " first_name = ? ";
		 * args.add(filters.get("first_name")); conditionAdded = true; }
		 * if(conditionAdded) { sql += " AND"; conditionAdded = false; } if
		 * (filters.containsKey("user_id")) { sql += " user_id = ?";
		 * args.add(filters.get("user_id")); conditionAdded = true; }
		 */
		for (String filterKey : filters.keySet()) {
			if(conditionAdded) { 
				sql += " AND"; 
			} 
			sql += " "+filterKey+" = ? ";
			 args.add(filters.get(filterKey)); 
			 conditionAdded = true; 
		}

System.out.println("SQL------>"+sql);
System.out.println("args----->"+args);
		return jdbcTemplate.query(sql,
				args.toArray(), 
				new BeanPropertyRowMapper<User>(User.class));
	}
}
