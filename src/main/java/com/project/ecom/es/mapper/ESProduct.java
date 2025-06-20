package com.project.ecom.es.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Builder
@Document(indexName = "product_index")
public class ESProduct {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    // @Field(type = FieldType.Text, analyzer = "standard")  // Adds flexibility in search; NOTE: @Field annotation must not be used on a @MultiField property. Either @Field or @MultiField can be used
    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "standard"),
            otherFields = {
            @InnerField(suffix = "raw", type = FieldType.Keyword)  // Better for filtering, sorting, aggregation, exact category match
            }
    )
    private String category;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;

    @Field(type = FieldType.Scaled_Float, scalingFactor = 100)  // stores price as an int internally with 2 decimal places
    private BigDecimal price;

    @Field(type = FieldType.Keyword)
    private String primaryImageUrl;

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ESProduct)) return false;
        ESProduct other = (ESProduct) obj;
        return Objects.equals(this.id, other.id);
    }
}
