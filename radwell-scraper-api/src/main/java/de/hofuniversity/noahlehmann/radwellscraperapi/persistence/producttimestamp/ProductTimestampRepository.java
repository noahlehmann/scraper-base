package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.producttimestamp;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductTimestampRepository extends MongoRepository<ProductTimestamp, Long> {

    Set<ProductTimestamp> findAllByProductId(String productId);

    Optional<ProductTimestamp> findById(Long id);

    public long count();

}
