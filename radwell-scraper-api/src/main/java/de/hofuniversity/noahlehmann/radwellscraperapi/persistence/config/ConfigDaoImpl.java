package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.config;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class ConfigDaoImpl implements ConfigDao {

    private final MongoOperations mongoOperation;

    public ConfigDaoImpl(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    @Override
    public ScraperConfiguration getCurrentConfig() throws NoConfigSetException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        final Query query = new Query()
                .limit(1)
                .with(Sort.by("validFrom").descending());

        ScraperConfiguration config = mongoOperation.findOne(query, ScraperConfiguration.class);

        if (config == null) throw new NoConfigSetException("Checked at " + format.format(new Date()));
        else return config;
    }

    @Override
    public ScraperConfiguration setCurrentConfig(ScraperConfiguration configuration) {
        return mongoOperation.insert(configuration);
    }

}
