package com.known.manager.handler;

import com.known.common.enums.MessageStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({MessageStatusEnum.class})
public class MessageStatusHandler extends BaseTypeHandler<MessageStatusEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
                                    MessageStatusEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getType());
		
	}

	@Override
	public MessageStatusEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Integer type = rs.getInt(columnName);
		if(type != null){
			return MessageStatusEnum.getMessageStatusByType(type);
		}
		return null;
	}

	@Override
	public MessageStatusEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Integer type = rs.getInt(columnIndex);
		if(type != null){
			return MessageStatusEnum.getMessageStatusByType(type);
		}
		return null;
	}

	@Override
	public MessageStatusEnum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Integer type = cs.getInt(columnIndex);
		if(type != null){
			return MessageStatusEnum.getMessageStatusByType(type);
		}
		return null;
	}

}
