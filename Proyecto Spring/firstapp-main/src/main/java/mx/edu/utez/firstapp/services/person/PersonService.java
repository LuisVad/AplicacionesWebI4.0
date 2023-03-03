package mx.edu.utez.firstapp.services.person;

import mx.edu.utez.firstapp.models.person.Person;
import mx.edu.utez.firstapp.models.person.PersonRepository;
import mx.edu.utez.firstapp.models.user.User;
import mx.edu.utez.firstapp.models.user.UserRepository;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;


    //metodo para obtener todos los registros
    @Transactional(readOnly = true)
    public CustomResponse<List<Person>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    // public CustomResponse<Person> savePerson(Person person){
    //     return new CustomResponse<>(
    //             this.repository.save(person),
    //             false,
    //             200,
    //             "Ok"
    //     );
    // }
    // public CustomResponse<String> deletePerson(Long id){
    //     this.repository.deleteById(id);
    //     return new CustomResponse<>(
    //             "Eliminado",
    //             false,
    //             200,
    //             "Ok"
    //     );
    // }

    //metodo para obetener a una persona
    @Transactional(readOnly = true)
    public CustomResponse<Person>getOne(Long id){
        return new CustomResponse<>(
                this.repository.findById(id).get(),
                false,
                200,
                "ok"
        );
    }

    //metodo para insertar a una persona
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Person> insert(Person person) {
        Optional<Person> exists = this.repository.findByCurp(person.getCurp());
        if (exists.isPresent()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La persona ya se encuentra registrada"
            );
        }
        User userExists = this.userRepository.findByUsername(person.getUser().getUsername());
        if (userExists != null) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario ya se encuentra registrada"
            );
        }
        person.getUser().setPassword(
                encoder.encode(person.getUser().getPassword())
        );
        return new CustomResponse<>(
                this.repository.saveAndFlush(person),
                false,
                200,
                "Persona registrada correctamente"
        );
    }

    //metodo para actualizar una categoria
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Person> update(Person person){
        if (!this.repository.existsById(person.getId())){
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La persona no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(person),
                false,
                200,
                "Persona actualizada correctamente"
        );
    }

    //metodo para eliminar (cambiar status) de una persona
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Person person){
        if (!this.repository.existsById(person.getId())){
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La persona no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.updateStatusById(
                        person.getStatus(), person.getId()
                ),
                false,
                200,
                "Persona actualizada correctamente"
        );
    }
}
