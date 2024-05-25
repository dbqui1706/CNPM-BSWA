package fit.nlu.cnpmbookshopweb.model;

import fit.nlu.cnpmbookshopweb.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product extends AbstractModel {
    private String name;
    private Double price;
    private Double discount;
    private Integer quantity;
    private Integer totalBuy;
    private String author;
    private Integer pages;
    private String publisher;
    private Integer yearPublishing;
    private String description;
    private String imageName;
    private Integer shop;

    public ProductDto get() {
        return new ProductDto(
            this.getId(),
            this.getName(),
            this.getPrice(),
            this.getDiscount(),
            this.getQuantity(),
            this.getTotalBuy(),
            this.getAuthor(),
            this.getPages(),
            this.getPublisher(),
            this.getYearPublishing(),
            this.getDescription(),
            this.getImageName(),
            this.getShop(),
            this.getCreatedAt(),
            this.getUpdatedAt()
        );
    }
}
