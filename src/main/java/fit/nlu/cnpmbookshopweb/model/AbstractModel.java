package fit.nlu.cnpmbookshopweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public abstract class AbstractModel {
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
