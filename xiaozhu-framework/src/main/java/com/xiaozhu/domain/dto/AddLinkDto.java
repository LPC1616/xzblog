package com.xiaozhu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLinkDto {
    private String name;

    private String status;

    private String logo;

    private String description;

    private String address;
}
