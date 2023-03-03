package mx.edu.utez.firstapp.models.subcategory;

import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query(value = "UPDATE subcategories SET status = :status WHERE id = :id", nativeQuery = true)
    boolean updateStatusById(@Param("status") Boolean status, @Param("id")Long id);
    boolean existsByName(String name);
}
