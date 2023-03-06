package de.hofuniversity.noahlehmann.radwellscraperapi.controller;

import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.log.ScraperLog;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.log.ScraperLogRepository;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.sequence.SequenceDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("log")
public class ScraperLogController {

    private final ScraperLogRepository scraperLogRepository;
    private final SequenceDao sequenceDao;

    public ScraperLogController(ScraperLogRepository scraperLogRepository, SequenceDao sequenceDao) {
        this.scraperLogRepository = scraperLogRepository;
        this.sequenceDao = sequenceDao;
    }

    @GetMapping
    public ResponseEntity<List<ScraperLog>> getAllLogs() {
        return ResponseEntity.ok(scraperLogRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<List<ScraperLog>> createLogs(@RequestBody List<ScraperLog> scraperLogs) {
        return ResponseEntity.created(URI.create("")).body(scraperLogRepository.saveAll(
                scraperLogs.stream()
                        .peek(log -> log.setId(sequenceDao.getNextSequenceId(ScraperLog.SEQUENCE_KEY)))
                        .collect(Collectors.toList())
        ));
    }

}
