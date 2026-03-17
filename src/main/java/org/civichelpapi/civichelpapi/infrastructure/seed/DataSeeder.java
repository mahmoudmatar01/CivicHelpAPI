package org.civichelpapi.civichelpapi.infrastructure.seed;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.category.entity.Category;
import org.civichelpapi.civichelpapi.category.enums.Priority;
import org.civichelpapi.civichelpapi.category.repository.CategoryRepository;
import org.civichelpapi.civichelpapi.location.entity.City;
import org.civichelpapi.civichelpapi.location.entity.District;
import org.civichelpapi.civichelpapi.location.entity.Governorate;
import org.civichelpapi.civichelpapi.location.reposirory.GovernorateRepository;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.enums.Role;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final GovernorateRepository governorateRepo;
    private final CategoryRepository categoryRepo;
    private final UserRepository userRepo;

    @Override
    public void run(String... args) throws Exception {
        seedLocations();
        seedCategories();
    }

    private void seedLocations() {

        if (governorateRepo.count() > 0) {
            return;
        }

        Governorate cairo = new Governorate();
        cairo.setName("Cairo");

        City nasrCity = new City();
        nasrCity.setName("Nasr City");
        nasrCity.setGovernorate(cairo);
        cairo.getCities().add(nasrCity);

        District d1 = new District();
        d1.setName("District 1");
        d1.setCity(nasrCity);
        nasrCity.getDistricts().add(d1);

        District d2 = new District();
        d2.setName("District 2");
        d2.setCity(nasrCity);
        nasrCity.getDistricts().add(d2);

        Governorate giza = new Governorate();
        giza.setName("Giza");

        City dokki = new City();
        dokki.setName("Dokki");
        dokki.setGovernorate(giza);
        giza.getCities().add(dokki);

        District moh = new District();
        moh.setName("Mohandessin");
        moh.setCity(dokki);
        dokki.getDistricts().add(moh);

        District ag = new District();
        ag.setName("Agouza");
        ag.setCity(dokki);
        dokki.getDistricts().add(ag);

        governorateRepo.save(cairo);
        governorateRepo.save(giza);
    }

    private void seedAuthoritiesAndNGOAndAdmins(){

        // Seed admin account
        User admin = new User();
        admin.setEmail("admin@admin.com");
        admin.setPassword("admin-password");
        admin.setRole(Role.ADMIN);
        admin.setFullName("Admin");

        Governorate cairo = governorateRepo.findByNameIgnoreCase("Cairo").get();
        admin.setGovernorate(cairo);
        admin.setCity(cairo.getCities().get(0));
        admin.setDistrict(cairo.getCities().get(0).getDistricts().get(0));

        admin.setEnabled(true);

        // Seed authority account
        User authority = new User();
        admin.setEmail("authority@authority.com");
        admin.setPassword("authority-password");
        admin.setRole(Role.AUTHORITY);
        admin.setFullName("Authority");

        Governorate giza = governorateRepo.findByNameIgnoreCase("Giza").get();
        authority.setGovernorate(giza);
        authority.setCity(giza.getCities().get(0));
        authority.setDistrict(giza.getCities().get(0).getDistricts().get(0));

        authority.setEnabled(true);

        // save data
        userRepo.saveAll(List.of(admin,authority));

    }

    private void seedCategories() {

        if (categoryRepo.count() > 0) {
            return;
        }

        categoryRepo.save(createCategory(
                "Garbage Collection", 24, Priority.HIGH));

        categoryRepo.save(createCategory(
                "Water Leakage", 6, Priority.EMERGENCY));

        categoryRepo.save(createCategory(
                "Electricity Outage", 4, Priority.EMERGENCY));

        categoryRepo.save(createCategory(
                "Road Damage", 72, Priority.MEDIUM));
    }

    private Category createCategory(
            String name, long sla, Priority priority) {

        Category c = new Category();
        c.setName(name);
        c.setSlaHours(sla);
        c.setDefaultPriority(priority);
        c.setEnabled(true);
        return c;
    }
}
