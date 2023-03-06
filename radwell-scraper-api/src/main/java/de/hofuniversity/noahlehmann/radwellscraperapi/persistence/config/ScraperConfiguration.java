package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@Document("scraper_configuration")
public class ScraperConfiguration {



}
