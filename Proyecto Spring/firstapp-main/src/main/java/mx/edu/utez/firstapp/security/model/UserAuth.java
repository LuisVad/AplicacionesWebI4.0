package mx.edu.utez.firstapp.security.model;

import mx.edu.utez.firstapp.models.person.Person;
import mx.edu.utez.firstapp.models.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//clase para iniciar sesion
public class UserAuth implements UserDetails {//conexion entre springsecurity y nuestro auth
    private String username;
    private  String password;
    private Person person;
    // es pare que spring lo reconozca como un rol propio
    //colection es un contenedor y le estamos diciendo que la clase de nosotros sea reconocida como una clase propia de spring
    private Collection<? extends GrantedAuthority> authorities;

    public UserAuth(String username, String password, Person person, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.authorities = authorities;
    }

    public static UserAuth build(User user){

        //obtenemos todos los roles de usuario y los convierte a roles de spring security
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new UserAuth(user.getUsername(), user.getPassword(), user.getPerson(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }//reconoce nuestros roles como propios

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Person getPerson(){
        return person;
    }

    public void setPerson(Person person){
        this.person = person;
    }

}
