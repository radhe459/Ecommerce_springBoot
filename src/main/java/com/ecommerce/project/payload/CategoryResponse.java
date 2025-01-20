package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    public List<CategoryDTO> content;
    public Integer pageNumber;
    public Integer pageSize;
    public Long totalElements;
    public Integer totalPages;
    public boolean lastPage;
}
