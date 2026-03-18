package org.civichelpapi.civichelpapi.category.service;

import org.civichelpapi.civichelpapi.category.dto.request.CategoryRequest;
import org.civichelpapi.civichelpapi.category.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    CategoryResponse update(Integer id, CategoryRequest request);
    List<CategoryResponse> findEnabled();
    CategoryResponse enable(Integer id);
    CategoryResponse disable(Integer id);
}
