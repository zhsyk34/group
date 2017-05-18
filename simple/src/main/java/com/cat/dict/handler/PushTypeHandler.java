package com.cat.dict.handler;

import com.cat.dict.PushTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PushTypeHandler extends BaseTypeHandler<PushTypeEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PushTypeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getDescription());
    }

    @Override
    public PushTypeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? null : PushTypeEnum.from(rs.getString(columnName));
    }

    @Override
    public PushTypeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : PushTypeEnum.from(rs.getString(columnIndex));
    }

    @Override
    public PushTypeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : PushTypeEnum.from(cs.getString(columnIndex));
    }
}
