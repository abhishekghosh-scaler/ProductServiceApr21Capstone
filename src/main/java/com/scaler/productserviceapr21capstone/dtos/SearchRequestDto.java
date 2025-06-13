package com.scaler.productserviceapr21capstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto
{
    private String query;
    private int pageNumber;
    private int pageSize;
    private String sortParam;
}
