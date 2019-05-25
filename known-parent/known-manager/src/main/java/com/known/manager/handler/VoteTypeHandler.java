package com.known.manager.handler;

import com.known.common.enums.VoteTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({VoteTypeEnum.class})
public class VoteTypeHandler extends BaseTypeHandler<VoteTypeEnum> {

	

	@Override
	public VoteTypeEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Integer type = rs.getInt(columnName);
		if(type != null){
			return VoteTypeEnum.getVoteTypeByValue(type);
		}
		return null;
	}

	@Override
	public VoteTypeEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Integer type = rs.getInt(columnIndex);
		if(type != null){
			return VoteTypeEnum.getVoteTypeByValue(type);
		}
		return null;
	}

	@Override
	public VoteTypeEnum getNullableResult(CallableStatement cs,
                                          int columnIndex) throws SQLException {
		Integer type = cs.getInt(columnIndex);
		if(type != null){
			return VoteTypeEnum.getVoteTypeByValue(type);
		}
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
                                    VoteTypeEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getType());
	}



}
