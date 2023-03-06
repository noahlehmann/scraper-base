package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.productcategory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends MongoRepository<ProductCategory, Long> {



}
