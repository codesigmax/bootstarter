package com.qfleaf.bootstarter.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("permissions")
public class Permission {
    @TableId
    private Long id;
    private String name;
    private String code;
    private String description;
}
