package com.known.manager.handler;

import com.known.common.enums.ExamChooseType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChooseTypeHandler extends BaseTypeHandler<ExamChooseType> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			ExamChooseType parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		ps.setInt(i, parameter.getType());
	}

	@Override
	public ExamChooseType getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Integer type = rs.getInt(columnName);
		if(type != null){
			return ExamChooseType.getExamChooseTypeByValue(type);
		}
		return null;
	}

	@Override
	public ExamChooseType getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Integer type = rs.getInt(columnIndex);
		if(type != null){
			return ExamChooseType.getExamChooseTypeByValue(type);
		}
		return null;
	}

	@Override
	public ExamChooseType getNullableResult(CallableStatement cs,
			int columnIndex) throws SQLException {
		Integer type = cs.getInt(columnIndex);
		if(type != null){
			return ExamChooseType.getExamChooseTypeByValue(type);
		}
		return null;
	}



}
