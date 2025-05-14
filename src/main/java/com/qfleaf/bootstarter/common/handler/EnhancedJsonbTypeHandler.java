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
import java.util.Map;

/**
 * 强化的PostgreSQL JSON/JSONB类型处理器(专为Map<String, Object>设计)
 * @author 搴芳
 */
@MappedTypes({Map.class})
@MappedJdbcTypes({JdbcType.OTHER})
public class EnhancedJsonbTypeHandler extends BaseTypeHandler<Map<String, Object>> {
    private static final Logger log = LoggerFactory.getLogger(EnhancedJsonbTypeHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeReference<Map<String, Object>> TYPE_REF = new TypeReference<>() {
    };

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    Map<String, Object> parameter,
                                    JdbcType jdbcType) throws SQLException {
        PGobject pgObject = new PGobject();
        pgObject.setType("jsonb");
        try {
            pgObject.setValue(objectMapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting Map to JSON", e);
        }
        ps.setObject(i, pgObject);
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseValue(rs.getObject(columnName));
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseValue(rs.getObject(columnIndex));
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseValue(cs.getObject(columnIndex));
    }

    private Map<String, Object> parseValue(Object value) {
        if (value == null) {
            return Collections.emptyMap();
        }

        try {
            // 处理PostgreSQL的PGobject
            if (value instanceof PGobject pgObject) {
                if (pgObject.getValue() == null) {
                    return Collections.emptyMap();
                }
                return objectMapper.readValue(pgObject.getValue(), TYPE_REF);
            }
            // 处理直接字符串
            else if (value instanceof String str) {
                return objectMapper.readValue(str, TYPE_REF);
            }
            // 其他情况尝试直接转换
            else {
                log.warn("Unexpected JSON type: {}", value.getClass().getName());
                return objectMapper.convertValue(value, TYPE_REF);
            }
        } catch (Exception e) {
            log.error("JSON parsing error. Raw value: {} (Type: {})",
                    value, value.getClass().getSimpleName(), e);
            return Collections.emptyMap();
        }
    }
}
