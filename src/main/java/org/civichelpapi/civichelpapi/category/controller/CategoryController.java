package org.civichelpapi.civichelpapi.category.controller;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.category.service.CategoryService;
import org.civichelpapi.civichelpapi.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(
                ApiResponse.success(categoryService.findEnabled())
        );
    }
}