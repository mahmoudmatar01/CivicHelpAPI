package org.civichelpapi.civichelpapi.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.category.dto.request.CategoryRequest;
import org.civichelpapi.civichelpapi.category.dto.response.CategoryResponse;
import org.civichelpapi.civichelpapi.category.entity.Category;
import org.civichelpapi.civichelpapi.category.repository.CategoryRepository;
import org.civichelpapi.civichelpapi.category.service.CategoryService;
import org.civichelpapi.civichelpapi.exception.BusinessException;
import org.civichelpapi.civichelpapi.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse create(CategoryRequest request) {
        categoryRepository.findByNameIgnoreCase(request.name())
                .ifPresent(c -> {
                    throw new BusinessException("Category already exists");
                });

        Category category = new Category();
        category.setName(request.name());
        category.setSlaHours(request.slaHours());
        category.setDefaultPriority(request.defaultPriority());

        return map(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Integer id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        category.setName(request.name());
        category.setSlaHours(request.slaHours());
        category.setDefaultPriority(request.defaultPriority());

        return map(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> findEnabled() {
        return categoryRepository.findByEnabledTrue()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public void enable(Integer id) {
        toggle(id, true);
    }

    @Override
    public void disable(Integer id) {
        toggle(id, false);
    }

    private void toggle(Integer id, boolean enabled) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        if(category.isEnabled() && enabled) {
            throw new BusinessException("Category is already enabled");
        }
        else if(category.isEnabled() == false && enabled == false) {
            throw new BusinessException("Category is already disabled");
        }
        else{
            category.setEnabled(enabled);
            categoryRepository.save(category);
        }
    }

    private CategoryResponse map(Category c) {
        return new CategoryResponse(
                c.getId(),
                c.getName(),
                c.getSlaHours(),
                c.getDefaultPriority(),
                c.isEnabled()
        );
    }

}
