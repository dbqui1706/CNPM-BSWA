package fit.nlu.cnpmbookshopweb.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractModel {
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
