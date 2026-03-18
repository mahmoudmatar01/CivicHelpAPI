package org.civichelpapi.civichelpapi.report;

import org.civichelpapi.civichelpapi.auth.dto.request.RegisterRequest;
import org.civichelpapi.civichelpapi.auth.service.AuthService;
import org.civichelpapi.civichelpapi.category.dto.request.CategoryRequest;
import org.civichelpapi.civichelpapi.category.dto.response.CategoryResponse;
import org.civichelpapi.civichelpapi.category.enums.Priority;
import org.civichelpapi.civichelpapi.category.service.CategoryService;
import org.civichelpapi.civichelpapi.address.entity.City;
import org.civichelpapi.civichelpapi.address.entity.District;
import org.civichelpapi.civichelpapi.address.entity.Governorate;
import org.civichelpapi.civichelpapi.address.reposirory.GovernorateRepository;
import org.civichelpapi.civichelpapi.report.dto.request.ReportRequest;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.service.AuthorityReportService;
import org.civichelpapi.civichelpapi.report.service.CitizenReportService;
import org.civichelpapi.civichelpapi.report.service.ReportService;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.enums.Role;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReportWorkflowIntegrationTest {

    @Autowired private ReportService reportService;
    @Autowired private CitizenReportService citizenReportService;
    @Autowired private AuthorityReportService authorityReportService;
    @Autowired private CategoryService categoryService;
    @Autowired private AuthService authService;
    @Autowired private UserRepository userRepository;
    @Autowired private GovernorateRepository governorateRepository;

    private Long citizenId;
    private Long authorityId;
    private Integer categoryId;
    private Integer districtId;

    @BeforeEach
    void setUp() {
        // Setup locations
        Governorate g = new Governorate();
        g.setName("Test Gov");
        g.setCities(new ArrayList<>());
        
        City c = new City();
        c.setName("Test City");
        c.setGovernorate(g);
        c.setDistricts(new ArrayList<>());
        g.getCities().add(c);
        
        District d = new District();
        d.setName("Test District");
        d.setCity(c);
        c.getDistricts().add(d);
        
        governorateRepository.save(g);
        districtId = d.getId();

        // Setup Category
        CategoryResponse cat = categoryService.create(new CategoryRequest("Infrastructure", 48, Priority.HIGH));
        categoryId = cat.id();

        // Setup Users
        authService.register(new RegisterRequest("Citizen", "citizen@test.com", "password","password"));
        User citizen = userRepository.findByEmail("citizen@test.com").get();
        citizenId = citizen.getId();

        // Setup Authority manually because public registration doesn't allow it
        User authority = new User();
        authority.setFullName("Authority");
        authority.setEmail("auth@test.com");
        authority.setPassword("password");
        authority.setRole(Role.ROLE_AUTHORITY);
        authority.setCity(c);
        authority.setEnabled(true);
        userRepository.save(authority);
        authorityId = authority.getId();
    }

    @Test
    void fullReportWorkflow_Success() {
        // 1. Create Report
        ReportRequest reportReq = new ReportRequest(categoryId, districtId, "Pothole in the main street");
        ReportResponse report = reportService.createReport(citizenId, reportReq, new ArrayList<>());

        assertNotNull(report);
        assertEquals(Status.OPEN.name(), report.status());
        assertEquals(Priority.HIGH.name(), report.priority());

        // 2. Assign Report
        report = authorityReportService.assignReport(report.id(), authorityId);
        assertEquals(Status.ASSIGNED.name(), report.status());

        // 3. Start Progress
        report = authorityReportService.startProgress(report.id(), authorityId);
        assertEquals(Status.IN_PROGRESS.name(), report.status());

        // 4. Resolve Report
        report = authorityReportService.resolveReport(report.id(), authorityId, "Fixed the pothole");
        assertEquals(Status.RESOLVED.name(), report.status());

        // 5. Close Report (by citizen)
        report = citizenReportService.closeReport(report.id(), citizenId);
        assertEquals(Status.CLOSED.name(), report.status());
    }
}
