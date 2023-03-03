package mx.edu.utez.firstapp.services.category;

import mx.edu.utez.firstapp.models.category.Category;
import mx.edu.utez.firstapp.models.category.CategoryRepository;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository repository;
    //metodo para obtener todas las categorias
    @Transactional(readOnly = true)
    public CustomResponse<List<Category>> getAll() {
        return new CustomResponse<>(this.repository.findAll(), false, 200, "ok");
    }
    //metodo para obtener solo una categoria
    @Transactional(readOnly = true)
    public CustomResponse<Category> getOne(Long id) {
        return new CustomResponse<>(this.repository.findById(id).get(), false, 200, "ok");
    }
    //metodo para insertar una categoria
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Category> insert(Category category) {
        if (this.repository.existsByName(category.getName()))
            return new CustomResponse<>(null, true, 400, "La categoria ya se ha registrado");
        return new CustomResponse<>(this.repository.saveAndFlush(category), false, 200, "Categoria registrada correctamente");
    }
    //metodo para actualizar una categoria
    @Transactional()
    public CustomResponse<Category> update(Category category) {//buscar la forma de validar que no se repita el nombre haciendo otro metodo
        if (!this.repository.existsById(category.getId()))
            return new CustomResponse<>(null, true, 400, "La categoria no existe");
        return new CustomResponse<>(this.repository.saveAndFlush(category), false, 200, "Categoria actualizada correctamente");
    }
    //metodo para eliminar (cambiar es status) de una categoria
    @Transactional()
    public CustomResponse<Boolean> changeStatus(Category category) {
        if (!this.repository.existsById(category.getId()))
            return new CustomResponse<>(null, true, 400, "La categoria no existe");
        return new CustomResponse<>(this.repository.updateStatusById(category.getStatus(), category.getId()), false, 200, "Categoria status actualizado correctamente");
    }


}
