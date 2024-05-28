package fit.nlu.cnpmbookshopweb.model;

import fit.nlu.cnpmbookshopweb.model.Location;
import lombok.Getter;

import java.sql.Timestamp;

public abstract class AbstractModel {
    @Getter
    private Long id;

    private Timestamp createdAt;
    private Timestamp updatedAt;


    public void setId(Long id) {
        this.id = id;
    }
}
