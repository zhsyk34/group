package com.cat.dict.handler;

import com.cat.dict.SwitchStateEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SwitchStateHandler extends BaseTypeHandler<SwitchStateEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SwitchStateEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public SwitchStateEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? null : SwitchStateEnum.from(rs.getInt(columnName));
    }

    @Override
    public SwitchStateEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : SwitchStateEnum.from(rs.getInt(columnIndex));
    }

    @Override
    public SwitchStateEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : SwitchStateEnum.from(cs.getInt(columnIndex));
    }
}
