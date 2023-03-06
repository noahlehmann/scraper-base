package de.hofuniversity.noahlehmann.radwellscraperapi;

import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.productcategory.ProductCategory;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.productcategory.ProductCategoryRepository;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.producttimestamp.ProductTimestamp;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.producttimestamp.ProductTimestampRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

@SpringBootApplication
@EnableMongoRepositories
public class RadwellScraperApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadwellScraperApiApplication.class, args);
    }

}
