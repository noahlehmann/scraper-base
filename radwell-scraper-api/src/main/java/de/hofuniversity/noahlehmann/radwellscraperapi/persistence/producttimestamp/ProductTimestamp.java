package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.producttimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ProductTimestamp {

    public static String SEQUENCE_KEY = "product_timestamp_seq";

    @Id
    private Long id;

    private String productId;
    private String brandName;
    private String productName;
    private String link;
    private String imageUrl;
    private double price;
    private String availability;
    private String shippingComment;
    private Date timestamp;
}
