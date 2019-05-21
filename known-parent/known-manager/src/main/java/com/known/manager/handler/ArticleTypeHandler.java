package com.known.manager.handler;

import com.known.common.enums.ArticleType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleTypeHandler extends BaseTypeHandler<ArticleType> {

	

	@Override
	public ArticleType getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String type = rs.getString(columnName);
		if(type != null){
			return ArticleType.getArticleTypeByType(type);
		}
		return null;
	}

	@Override
	public ArticleType getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String type = rs.getString(columnIndex);
		if(type != null){
			return ArticleType.getArticleTypeByType(type);
		}
		return null;
	}

	@Override
	public ArticleType getNullableResult(CallableStatement cs,
			int columnIndex) throws SQLException {
		String type = cs.getString(columnIndex);
		if(type != null){
			return ArticleType.getArticleTypeByType(type);
		}
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			ArticleType parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getType());
	}

}
