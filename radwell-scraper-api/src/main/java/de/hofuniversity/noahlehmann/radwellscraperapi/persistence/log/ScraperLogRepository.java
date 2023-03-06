package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.log;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScraperLogRepository extends MongoRepository<ScraperLog, Long> {

    Optional<ScraperLog> findFirstByMessageContainsIgnoreCaseOrderByTimestampDesc(String messagePart);

}
