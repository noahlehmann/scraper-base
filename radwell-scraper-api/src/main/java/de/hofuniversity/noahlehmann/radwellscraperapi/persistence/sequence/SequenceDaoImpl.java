package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.sequence;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceDaoImpl implements SequenceDao {

    private final MongoOperations mongoOperation;

    public SequenceDaoImpl(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    @Override
    public long getNextSequenceId(String key) throws SequenceException {

        Query query = new Query(Criteria.where("_id").is(key));

        if (mongoOperation.find(query, SequenceId.class).size() == 0) {
            mongoOperation.insert(new SequenceId(key));
        }

        //increase sequence id by 1
        Update update = new Update();
        update.inc("seq", 1);

        //return new increased id
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        SequenceId seqId = mongoOperation.findAndModify(query, update, options, SequenceId.class);

        if (seqId == null) {
            throw new SequenceException("Unable to get sequence id for key : " + key);
        }

        return seqId.getSeq();

    }

}
