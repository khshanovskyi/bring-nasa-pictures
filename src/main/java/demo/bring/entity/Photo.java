package demo.bring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "photo")
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {
    @Id
    @Column(unique = true, nullable = false)
    @JsonProperty
    private Long sol;

    @JsonProperty("img_src")
    @Column(nullable = false)
    private String url;

}
