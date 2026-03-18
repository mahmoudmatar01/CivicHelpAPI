package org.civichelpapi.civichelpapi.report;

import org.civichelpapi.civichelpapi.category.entity.Category;
import org.civichelpapi.civichelpapi.category.enums.Priority;
import org.civichelpapi.civichelpapi.address.entity.City;
import org.civichelpapi.civichelpapi.address.entity.District;
import org.civichelpapi.civichelpapi.address.entity.Governorate;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.helper.ReportHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportHelperMappingTest {

    @Test
    void toReportResponse_ShouldMapFieldsInCorrectOrder() {
        // Arrange
        Governorate gov = new Governorate();
        gov.setName("Cairo");

        City city = new City();
        city.setName("Nasr City");
        city.setGovernorate(gov);

        District dist = new District();
        dist.setName("District 1");
        dist.setCity(city);

        Category cat = new Category();
        cat.setName("Roads");

        Report report = new Report();
        report.setId(101L);
        report.setCategory(cat);
        report.setDistrict(dist);
        report.setDescription("Broken pavement");
        report.setStatus(Status.OPEN);
        report.setPriority(Priority.MEDIUM);
        report.setImageUrls(new ArrayList<>());

        LocalDateTime now = LocalDateTime.now();
        report.setCreatedAt(now);

        ReportResponse response = ReportHelper.toReportResponse(report);

        // Assert: Verify each field specifically to catch ordering bugs
        assertEquals(101L, response.id());
        assertEquals("Roads", response.categoryName());
        assertEquals("Cairo", response.governorateName());
        assertEquals("Nasr City", response.cityName());
        assertEquals("District 1", response.districtName());
        assertEquals("Broken pavement", response.description());
        assertEquals(Status.OPEN.name(), response.status());
        assertEquals(Priority.MEDIUM.name(), response.priority());
        assertEquals(now, response.createdAt());
    }
}
