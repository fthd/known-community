package cn.inc.manager.mapper;


import cn.inc.manager.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper extends SqlMapper {



	@Select("select id, username from t_user where username=#{username}")
	User findByUsername(@Param("username") String username);

	@Select("select username from t_user where username=#{username} and password=#{password}")
	User findByUAP(@Param("username") String username, @Param("password") String password);

	@Insert("insert into t_user(username,password,phone,email) values(#{username},#{password},#{phone},#{email})")
	int insert(User user);


}
