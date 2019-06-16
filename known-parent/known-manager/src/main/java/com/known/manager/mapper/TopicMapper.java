package com.known.manager.mapper;

import com.known.manager.query.UpdateQuery4ArticleCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicMapper<T, Q> extends BaseMapper<T, Q> {
   void updateInfoCount(UpdateQuery4ArticleCount updateQuery4ArticleCount);
   
   List<T> selectActiveUser4Topic();

   List<T> selectList4(Q q);
   
   void delete(@Param("ids") String[] ids);
   

}