package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@NoArgsConstructor
@Document("scraper_configuration")
public class ScraperConfiguration {

    public static final String SEQUENCE_KEY = "scraper_config_seq";
    private static final long DEFAULT_SCRAPING_INTERVAL = 1000 * 60 * 60 * 4; // 4 hours

    @Id
    @Getter
    private long id;

    @Getter
    private Date validFrom;

    private Long scrapingInterval;

    public long getScrapingInterval() {
        return scrapingInterval == null ? DEFAULT_SCRAPING_INTERVAL : scrapingInterval;
    }


}
