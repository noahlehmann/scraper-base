package de.hofuniversity.noahlehmann.radwellscraperapi.controller;

import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config.ScraperConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("config")
public class ConfigController {

    @GetMapping
    public ResponseEntity<ScraperConfiguration> getConfig(){
        return null;
    }

    @PutMapping
    public ResponseEntity<ScraperConfiguration> changeConfig(){
        return null;
    }

}
