package de.hofuniversity.noahlehmann.radwellscraperapi.persistence.sequence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Document(collection = "sequence")
public class SequenceId {

    public SequenceId(String key){
        this.id = key;
        this.seq = 0L;
    }

    @Id
    private String id;

    private long seq;
}
