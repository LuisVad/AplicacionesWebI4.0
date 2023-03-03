package mx.edu.utez.firstapp.controllers.subcategory;

import mx.edu.utez.firstapp.dto.SubcategoryDto;
import mx.edu.utez.firstapp.models.subcategory.SubCategory;
import mx.edu.utez.firstapp.services.subcategory.SubcategoryService;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api-firstapp/subcategory")
@CrossOrigin(origins = {"*"})
public class SubcategoryController {
    @Autowired
    private SubcategoryService service;
    //obtener todos los registros de subcategories
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<SubCategory>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //obtener solo un registro se subcategories
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<SubCategory>> getOne(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }
    //Insertar in registro en subcategories
    @PostMapping("/")
    public ResponseEntity<CustomResponse<SubCategory>> insert(@RequestBody SubcategoryDto subcategoryDto, @Valid BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.insert(subcategoryDto.getSubategory()),
                HttpStatus.CREATED
        );
    }
    //Actualiar un registro de subcategories
    @PutMapping("/")
    public ResponseEntity<CustomResponse<SubCategory>> update(@RequestBody SubcategoryDto subcategoryDto, @Valid BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.update(subcategoryDto.getSubategory()),
                HttpStatus.CREATED
        );
    }
    //Borrar (actualizar status) de subcategories
    @PatchMapping("/")
    public ResponseEntity<CustomResponse<Boolean>> changeStatus(@RequestBody SubcategoryDto subcategoryDto, @Valid BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.changeStatus(subcategoryDto.getSubategory()),
                HttpStatus.OK
        );
    }
}
