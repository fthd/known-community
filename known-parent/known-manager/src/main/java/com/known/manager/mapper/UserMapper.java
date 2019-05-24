package com.known.manager.mapper;

import com.known.common.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserMapper<T, Q>  extends BaseMapper<T, Q>{

	Integer changeUserMark(@Param(value = "changeMark") Integer changeMark, @Param(value = "userId") Integer userId);
	
	void updateStatus(@Param(value = "userName") String userName, @Param(value = "activationCode") String activationCode);

	void delete(@Param("ids") Integer[] ids);
	
	List<UserVo> selectUserVoList();

}