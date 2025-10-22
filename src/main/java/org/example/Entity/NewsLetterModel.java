package org.example.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "NEWS_LETTER_SUBSCRIPTION")
public class NewsLetterModel {
    private String email;
    private Instant created_at=Instant.now();
    private boolean isSubscribed;
    private long no_of_newsletters_sent;
}
