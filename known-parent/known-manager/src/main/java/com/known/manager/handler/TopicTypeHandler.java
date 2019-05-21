package com.known.manager.handler;

import com.known.common.enums.TopicType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({TopicType.class})
public class TopicTypeHandler extends BaseTypeHandler<TopicType> {

	

	@Override
	public TopicType getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String type = rs.getString(columnName);
		if(type != null){
			return TopicType.getTopicTypeByValue(Integer.parseInt(type));
		}
		return null;
	}

	@Override
	public TopicType getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String type = rs.getString(columnIndex);
		if(type != null){
			return TopicType.getTopicTypeByValue(Integer.parseInt(type));
		}
		return null;
	}

	@Override
	public TopicType getNullableResult(CallableStatement cs,
			int columnIndex) throws SQLException {
		String type = cs.getString(columnIndex);
		if(type != null){
			return TopicType.getTopicTypeByValue(Integer.parseInt(type));
		}
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			TopicType parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getType());
	}



}
