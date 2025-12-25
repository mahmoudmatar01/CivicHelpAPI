package org.civichelpapi.civichelpapi.infrastructure.seed;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.category.entity.Category;
import org.civichelpapi.civichelpapi.category.enums.Priority;
import org.civichelpapi.civichelpapi.category.repository.CategoryRepository;
import org.civichelpapi.civichelpapi.location.domain.City;
import org.civichelpapi.civichelpapi.location.domain.District;
import org.civichelpapi.civichelpapi.location.domain.Governorate;
import org.civichelpapi.civichelpapi.location.reposirory.GovernorateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final GovernorateRepository governorateRepo;
    private final CategoryRepository categoryRepo;

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
