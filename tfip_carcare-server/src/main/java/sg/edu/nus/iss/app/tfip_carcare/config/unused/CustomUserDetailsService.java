package sg.edu.nus.iss.app.tfip_carcare.config.unused;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.tfip_carcare.models.Customer;
import sg.edu.nus.iss.app.tfip_carcare.repositories.CustomerRepository;

/* @Service */
//THIS CLASS IS NOT USED
//if we use this class, it will use default DAOAuthentication provider instead of custom one.
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository custRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* String email;
        String password;

        List<GrantedAuthority> authorities;
        List<Customer> customers = custRepo.findByEmail(username);
        System.out.println("customer is .."+customers.get(0).toString());

        if (customers.size() == 0) {
            throw new UsernameNotFoundException("User details not found for the user " + username);
        } else {
            email = customers.get(0).getEmail();
            password = customers.get(0).getPwd();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
        }

        return new User(email, password, authorities); */
        return null;
    }

}
