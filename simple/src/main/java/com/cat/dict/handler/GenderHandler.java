package com.cat.dict.handler;

import com.cat.dict.GenderEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderHandler extends BaseTypeHandler<GenderEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, GenderEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getDescription());
    }

    @Override
    public GenderEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? null : GenderEnum.from(rs.getString(columnName));
    }

    @Override
    public GenderEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : GenderEnum.from(rs.getString(columnIndex));
    }

    @Override
    public GenderEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : GenderEnum.from(cs.getString(columnIndex));
    }
}
