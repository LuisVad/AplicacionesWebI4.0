package mx.edu.utez.firstapp.services.subcategory;

import mx.edu.utez.firstapp.models.subcategory.SubCategory;
import mx.edu.utez.firstapp.models.subcategory.SubCategoryRepository;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class SubcategoryService {
    @Autowired
    private SubCategoryRepository repository;

    @Transactional(readOnly = true)
    public CustomResponse<List<SubCategory>> getAll(){
        return new CustomResponse<>(this.repository.findAll(),false, 200, "ok");

    }

    @Transactional(readOnly = true)
    public CustomResponse<SubCategory> getOne(Long id){
        return new CustomResponse<>(this.repository.findById(id).get(), false, 200, "Ok");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<SubCategory> insert(SubCategory subcategory){
        if(this.repository.existsByName(subcategory.getName()))
            return new CustomResponse<>(null, true, 400,"La subcategoria ya se ha registrado" );
        return new CustomResponse<>(this.repository.saveAndFlush(subcategory), false, 200, "Subcategoria registrada correctamente");
    }

    @Transactional()
    public CustomResponse<SubCategory> update(SubCategory subcategory){
        if(!this.repository.existsById(subcategory.getId()))
            return  new CustomResponse<>(null, true, 400, "La subcategoria no existe");
        return new CustomResponse<>(this.repository.saveAndFlush(subcategory), false, 200, "Subcategoria registrada correctamente");

    }

    @Transactional
    public CustomResponse<Boolean> changeStatus(SubCategory subcategory){
        if(!this.repository.existsById(subcategory.getId()))
            return new CustomResponse<>(null, true, 400, "La subcategoria no existe");
        return new CustomResponse<>(this.repository.updateStatusById(subcategory.getStatus(), subcategory.getId()), false, 200, "Subcategoria registrada correctamente");
    }
}
