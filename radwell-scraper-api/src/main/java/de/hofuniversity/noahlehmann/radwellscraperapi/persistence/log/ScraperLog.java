package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class ScraperLog {

    public static String SEQUENCE_KEY = "scraper_log_seq";

    private long id;
    private String message;
    private Date timestamp;

}
