package com.known.manager.mapper;

import com.known.common.model.ThirdUser;
import com.known.common.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserMapper<T, Q>  extends BaseMapper<T, Q>{

	Integer changeUserMark(@Param(value = "changeMark") Integer changeMark, @Param(value = "userId") Integer userId);
	
	void delete(@Param("ids") Integer[] ids);
	
	List<UserVo> selectUserVoList();

	/** 查询第三方帐号用户Id */
	Long queryUserIdByThirdParty(ThirdUser param);

	//新增
	void insertThird(ThirdUser thirdUser);
}