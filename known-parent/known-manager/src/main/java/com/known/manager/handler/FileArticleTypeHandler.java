package com.known.manager.handler;

import com.known.common.enums.FileArticleTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({FileArticleTypeEnum.class})
public class FileArticleTypeHandler extends BaseTypeHandler<FileArticleTypeEnum> {

	

	@Override
	public FileArticleTypeEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String type = rs.getString(columnName);
		if(type != null){
			return FileArticleTypeEnum.getFileArticleTypeByType(type);
		}
		return null;
	}

	@Override
	public FileArticleTypeEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String type = rs.getString(columnIndex);
		if(type != null){
			return FileArticleTypeEnum.getFileArticleTypeByType(type);
		}
		return null;
	}

	@Override
	public FileArticleTypeEnum getNullableResult(CallableStatement cs,
                                               int columnIndex) throws SQLException {
		String type = cs.getString(columnIndex);
		if(type != null){
			return FileArticleTypeEnum.getFileArticleTypeByType(type);
		}
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
                                    FileArticleTypeEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getType().toString());
	}



}
