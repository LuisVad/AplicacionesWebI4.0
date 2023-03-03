package mx.edu.utez.firstapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.firstapp.models.category.Category;
import mx.edu.utez.firstapp.models.subcategory.SubCategory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubcategoryDto {
    private Long id;
   @NotEmpty(message = "Campo obligatorio")
   @Size(min = 3, max = 50)
    private String name;
    private Boolean status;
    private Category category;
    public SubCategory getSubategory(){
       return new SubCategory(
               getId(),
               getName(),
               getStatus(),
               getCategory()

       );
    }

}
