package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.sequence;

public interface SequenceDao {

    long getNextSequenceId(String key) throws SequenceException;

}