package mx.edu.utez.firstapp.security;

import mx.edu.utez.firstapp.security.jwt.JwtEntryPoint;
import mx.edu.utez.firstapp.security.jwt.JwtTokenFilter;
import mx.edu.utez.firstapp.security.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthService service;
    @Autowired
    private JwtEntryPoint entryPoint;
    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return  new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
        //super viene de la clase padre en este caso AuthenticationManager
    }
    @Override
    //este metodo proteje nuestrar rutas
    protected void configure(HttpSecurity http) throws Exception{//solo se puede acceder a este medodo desde security
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api-firstapp/auth/**", "/api-firstapp/contact/**").permitAll()
//                .antMatchers("/api-firstapp/category/*").permitAll() descomentar para dar acceso publico
                .antMatchers(HttpMethod.POST,"/api-firstapp/user/").permitAll()
                .antMatchers(HttpMethod.GET, "/api-firstapp/product/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api-firstapp/person/").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
                //investigar cors y csrf
        //antMatchers() toda peticion tiene un path, y con esto las hacemos publicas
    }

}
