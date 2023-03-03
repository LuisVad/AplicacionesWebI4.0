package mx.edu.utez.firstapp.models.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Boolean findAllById(long id);
    Optional<Person> findByCurp(String curp);
    List<Person> findAllByGender(String gender);

    //revisar notaciones
    @Modifying
    @Query(value = "UPDATE people SET status = :status WHERE id = :id", nativeQuery = true)
    boolean updateStatusById(
            @Param("status") Boolean status,
            @Param("id")Long id
    );

//    @Query(name = "SELECT * FROM people;", nativeQuery = true)
//    List<Person> buscarTodos();

}
