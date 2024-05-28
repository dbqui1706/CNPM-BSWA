package fit.nlu.cnpmbookshopweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String ip;
    private String country;
    private String region;
    private String city;
    private double latitude;
    private double longitude;
}
