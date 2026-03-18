package org.civichelpapi.civichelpapi.category.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.category.dto.request.CategoryRequest;
import org.civichelpapi.civichelpapi.category.service.CategoryService;
import org.civichelpapi.civichelpapi.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(categoryService.create(request))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(categoryService.update(id, request))
        );
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<?> enable(@PathVariable Integer id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        categoryService.enable(id)
                )
        );
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<?> disable(@PathVariable Integer id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        categoryService.disable(id)
                )
        );
    }

}
