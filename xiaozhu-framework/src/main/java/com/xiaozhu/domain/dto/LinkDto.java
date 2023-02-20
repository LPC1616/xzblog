package com.xiaozhu.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDto {
    private Long id;

    private String name;

    private String status;

    private String logo;

    private String description;

    private String address;
}
