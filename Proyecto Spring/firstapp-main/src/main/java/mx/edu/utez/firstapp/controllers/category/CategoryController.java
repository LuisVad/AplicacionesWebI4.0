package mx.edu.utez.firstapp.controllers.category;

import mx.edu.utez.firstapp.dto.CategoryDto;
import mx.edu.utez.firstapp.models.category.Category;
import mx.edu.utez.firstapp.services.category.CategoryService;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api-firstapp/category")
@CrossOrigin(origins = {"*"})
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Category>>> getAll(){
        return new ResponseEntity<>(this.service.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Category>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }
    //metodo para obtener uno solo getOne investigacion

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Category>> insert(@RequestBody CategoryDto categoryDto, @Valid BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.service.insert(categoryDto.getCategory()), HttpStatus.CREATED);
    }

    @PutMapping ("/")
    public ResponseEntity<CustomResponse<Category>> update(@RequestBody CategoryDto categoryDto, @Valid BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.service.update(categoryDto.getCategory()), HttpStatus.CREATED);
    }

    @PatchMapping  ("/")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(@RequestBody CategoryDto categoryDto, @Valid BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.service.changeStatus(categoryDto.getCategory()), HttpStatus.OK);
    }






    //enableOrDisable va por patch que es para cambiar el estatus
}
