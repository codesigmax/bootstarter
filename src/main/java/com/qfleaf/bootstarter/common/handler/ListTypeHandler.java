package com.qfleaf.bootstarter.common.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@MappedTypes({List.class})
@MappedJdbcTypes({JdbcType.ARRAY})
public class ListTypeHandler extends BaseTypeHandler<List<Object>> {
    private static final Logger log = LoggerFactory.getLogger(ListTypeHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeReference<List<Object>> TYPE_REF = new TypeReference<>() {
    };

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Object> parameter, JdbcType jdbcType) throws SQLException {
        PGobject pgObject = new PGobject();
        pgObject.setType("array");
        try {
            pgObject.setValue(objectMapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting List to ARRAY", e);
        }
        ps.setObject(i, pgObject);
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return List.of();
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return List.of();
    }

    @Override
    public List<Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return List.of();
    }

    private List<Object> parseValue(Object value) throws SQLException {
        if (value == null) {
            return Collections.emptyList();
        }

        try {
            // 处理PostgreSQL的PGobject
            if (value instanceof PGobject pgObject) {
                if (pgObject.getValue() == null) {
                    return Collections.emptyList();
                }
                return objectMapper.readValue(pgObject.getValue(), TYPE_REF);
            }
            // 处理直接字符串
            else if (value instanceof String str) {
                return objectMapper.readValue(str, TYPE_REF);
            }
            // 其他情况尝试直接转换
            else {
                log.warn("Unexpected ARRAY type: {}", value.getClass().getName());
                return objectMapper.convertValue(value, TYPE_REF);
            }
        } catch (Exception e) {
            log.error("ARRAY parsing error. Raw value: {} (Type: {})",
                    value, value.getClass().getSimpleName(), e);
            return Collections.emptyList();
        }
    }
}
