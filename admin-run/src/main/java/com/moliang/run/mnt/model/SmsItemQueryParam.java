package com.moliang.run.mnt.model;

import lombok.Data;

import java.util.Date;

/**
 * @Use 查询项目参数
 * @Author Chui moliang
 * @Date 2021/2/20 1:38
 * @Version 1.0
 */
@Data
public class SmsItemQueryParam {

    private Long startId;

    private Long toId;

    private String name;

    private Integer startPort;
    private Integer toPort;

    private String uploadPath;

    private String deployPath;

    private String backupPath;

    private String createBy;

    private String updateBy;

    private Date startUpdateTime;
    private Date toUpdateTime;

    private Date startCreateTime;
    private Date toCreateTime;
}
