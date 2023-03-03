package mx.edu.utez.firstapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.firstapp.models.category.Category;
import mx.edu.utez.firstapp.models.subcategory.SubCategory;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
    private Long id;
   @NotEmpty(message = "Campo obligatorio")
   @Size(min = 3, max = 50)
    private String name;
    private Boolean status;
    private SubCategory sucategories;

    public Category getCategory(){
        return new Category(
                getId(),
                getName(),
                getStatus(),
                (List<SubCategory>) getSucategories()
        );
    }

}
