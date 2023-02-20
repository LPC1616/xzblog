package com.xiaozhu.domain.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTwoVo {
    private String description;
    private Long id;
    private String name;
    private String status;
}
