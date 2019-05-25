package com.known.manager.handler;

import com.known.common.enums.FileTopicTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({FileTopicTypeEnum.class})
public class FileTopicTypeHandler extends BaseTypeHandler<FileTopicTypeEnum> {

	

	@Override
	public FileTopicTypeEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String type = rs.getString(columnName);
		if(type != null){
			return FileTopicTypeEnum.getFileTopicTypeByType(type);
		}
		return null;
	}

	@Override
	public FileTopicTypeEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String type = rs.getString(columnIndex);
		if(type != null){
			return FileTopicTypeEnum.getFileTopicTypeByType(type);
		}
		return null;
	}

	@Override
	public FileTopicTypeEnum getNullableResult(CallableStatement cs,
                                               int columnIndex) throws SQLException {
		String type = cs.getString(columnIndex);
		if(type != null){
			return FileTopicTypeEnum.getFileTopicTypeByType(type);
		}
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
                                    FileTopicTypeEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getType().toString());
	}



}
