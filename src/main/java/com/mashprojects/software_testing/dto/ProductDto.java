package com.mashprojects.software_testing.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String price;
    private String expiryDate;
}
