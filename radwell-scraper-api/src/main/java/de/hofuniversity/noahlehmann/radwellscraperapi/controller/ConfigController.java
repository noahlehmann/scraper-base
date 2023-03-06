package de.hofuniversity.noahlehmann.radwellscraperapi.controller;

import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config.ConfigDao;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config.ScraperConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("config")
public class ConfigController {

    private final ConfigDao configDao;

    public ConfigController(ConfigDao configDao) {
        this.configDao = configDao;
    }

    @GetMapping
    public ResponseEntity<ScraperConfiguration> getConfig() {
        return ResponseEntity.ok(configDao.getCurrentConfig());
    }

    @PutMapping
    public ResponseEntity<ScraperConfiguration> changeConfig(@RequestBody ScraperConfiguration configuration) {
        return ResponseEntity.ok(configDao.setCurrentConfig(configuration));
    }

}
