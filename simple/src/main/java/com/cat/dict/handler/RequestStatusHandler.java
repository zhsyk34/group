package com.cat.dict.handler;

import com.cat.dict.RequestStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestStatusHandler extends BaseTypeHandler<RequestStatusEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RequestStatusEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public RequestStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? null : RequestStatusEnum.from(rs.getInt(columnName));
    }

    @Override
    public RequestStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : RequestStatusEnum.from(rs.getInt(columnIndex));
    }

    @Override
    public RequestStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : RequestStatusEnum.from(cs.getInt(columnIndex));
    }
}
