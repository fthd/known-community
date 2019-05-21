package com.known.manager.handler;

import com.known.common.enums.MessageStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageStatusHandler extends BaseTypeHandler<MessageStatus> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			MessageStatus parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getType());
		
	}

	@Override
	public MessageStatus getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Integer type = rs.getInt(columnName);
		if(type != null){
			return MessageStatus.getMessageStatusByType(type);
		}
		return null;
	}

	@Override
	public MessageStatus getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Integer type = rs.getInt(columnIndex);
		if(type != null){
			return MessageStatus.getMessageStatusByType(type);
		}
		return null;
	}

	@Override
	public MessageStatus getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Integer type = cs.getInt(columnIndex);
		if(type != null){
			return MessageStatus.getMessageStatusByType(type);
		}
		return null;
	}

}
