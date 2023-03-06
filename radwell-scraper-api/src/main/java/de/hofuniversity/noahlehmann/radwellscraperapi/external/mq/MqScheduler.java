package de.hofuniversity.noahlehmann.radwellscraperapi.external.mq;

import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config.ConfigDao;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config.NoConfigSetException;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config.ScraperConfiguration;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.log.ScraperLog;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.log.ScraperLogRepository;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.sequence.SequenceDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MqScheduler {

    private final RabbitMQProducer producer;
    private final ConfigDao configDao;
    private final ScraperLogRepository logRepository;
    private final SequenceDao sequenceDao;

    public MqScheduler(RabbitMQProducer producer, ConfigDao configDao, ScraperLogRepository logRepository, SequenceDao sequenceDao) {
        this.producer = producer;
        this.configDao = configDao;
        this.logRepository = logRepository;
        this.sequenceDao = sequenceDao;
    }

    @Scheduled(initialDelay = 1000 * 10, fixedRate = 1000 * 60 * 5) // 5min, first after 10s
    public void sendMessage() {
        /*
         * todo:
         * - build scraping advice
         * - build message, pref json
         * */

        ScraperConfiguration config;
        try { // check configuration
            config = configDao.getCurrentConfig();
        } catch (NoConfigSetException noConfigException) {
            logRepository.save(new ScraperLog(
                    sequenceDao.getNextSequenceId(ScraperLog.SEQUENCE_KEY),
                    "No Scraper Configuration set. " + noConfigException.getMessage(),
                    new Date()
            )); // no default set, quit
            return;
        }

        Optional<ScraperLog> optionalLog =
                logRepository.findFirstByMessageContainsIgnoreCaseOrderByTimestampDesc("Started Scraping");

        long interval = config.getScrapingInterval();
        if (optionalLog.isEmpty() || isInPast(optionalLog.get().getTimestamp(), interval)) {
            // either no log with scraping start was set, or last is overdue
            producer.sendMessage("Start Scraping");
        }
    }

    private boolean isInPast(Date date, long added) {
        Date datePlusInterval = new Date(date.getTime() + added);
        return new Date().after(datePlusInterval);
    }

}
