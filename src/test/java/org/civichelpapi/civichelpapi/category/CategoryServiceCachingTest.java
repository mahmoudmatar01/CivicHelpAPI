package org.civichelpapi.civichelpapi.category;

import org.civichelpapi.civichelpapi.category.dto.request.CategoryRequest;
import org.civichelpapi.civichelpapi.category.dto.response.CategoryResponse;
import org.civichelpapi.civichelpapi.category.enums.Priority;
import org.civichelpapi.civichelpapi.category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CategoryServiceCachingTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void enableDisable_ShouldEvictCache() {
        CategoryResponse category = categoryService.create(
                new CategoryRequest("Environment", 24, Priority.MEDIUM)
        );
        

        categoryService.findEnabled();
        Cache cache = cacheManager.getCache("categories");
        if (cache != null) {
            assertNotNull(cache.get("active"));
        }

        categoryService.disable(category.id());

        assertNull(
                Objects.requireNonNull(cache).get("active"),
                "Cache should be cleared after category state change"
        );
    }

    @Test
    void findEnabled_ShouldOnlyReturnEnabledCategories() {
        categoryService.create(new CategoryRequest("Cat 1", 10, Priority.LOW));
        CategoryResponse cat2 = categoryService.create(new CategoryRequest("Cat 2", 10, Priority.LOW));
        categoryService.disable(cat2.id());

        List<CategoryResponse> enabled = categoryService.findEnabled();

        assertTrue(enabled.stream().allMatch(CategoryResponse::enabled));
        assertFalse(enabled.stream().anyMatch(c -> c.id().equals(cat2.id())));
    }
}
