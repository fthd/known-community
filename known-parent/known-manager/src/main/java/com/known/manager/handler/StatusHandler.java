package com.known.manager.handler;

import com.known.common.enums.StatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({StatusEnum.class})
public class StatusHandler extends BaseTypeHandler<StatusEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			StatusEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getType());
		
	}

	@Override
	public StatusEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Integer type = rs.getInt(columnName);
		if(type != null){
			return StatusEnum.getStatusByValue(type);
		}
		return null;
	}

	@Override
	public StatusEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Integer type = rs.getInt(columnIndex);
		if(type != null){
			return StatusEnum.getStatusByValue(type);
		}
		return null;
	}

	@Override
	public StatusEnum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Integer type = cs.getInt(columnIndex);
		if(type != null){
			return StatusEnum.getStatusByValue(type);
		}
		return null;
	}

}
