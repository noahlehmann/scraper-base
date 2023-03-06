package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config;

public interface ConfigDao {

    ScraperConfiguration getCurrentConfig() throws NoConfigSetException;

    ScraperConfiguration setCurrentConfig(ScraperConfiguration configuration);

}
