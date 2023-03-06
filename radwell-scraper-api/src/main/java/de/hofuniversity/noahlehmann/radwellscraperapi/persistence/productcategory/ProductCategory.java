package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.productcategory;


import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.producttimestamp.ProductTimestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ProductCategory {

    public static String SEQUENCE_KEY = "product_category_seq";

    @Id
    private long id;

    private String categoryName;
    private String categoryUrl;

    @DBRef
    private Set<ProductTimestamp> productTimestamps;

}
