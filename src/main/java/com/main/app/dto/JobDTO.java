package com.main.app.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JobDTO {
    private Long id;
    private String company;
    private String source;
    private String status;
    private String roleName;
    private Date appliedAt;
    private String url;
    private String notes;
}
