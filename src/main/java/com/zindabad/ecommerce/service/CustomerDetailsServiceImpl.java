package com.zindabad.ecommerce.service;

import com.zindabad.ecommerce.model.Customer;
import com.zindabad.ecommerce.model.Role;
import com.zindabad.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Customer> optionalUser = customerRepository.findByUsername(userName);
        if (optionalUser.isPresent()) {
            Customer customer = optionalUser.get();

            List<String> roleList = new ArrayList<String>();
            for (Role role : customer.getRoles()) {
                roleList.add(role.getName());
            }

            return User.builder()
                    .username(customer.getUsername())
                    //change here to store encoded password in db
                    .password(customer.getPassword())
                    .disabled(customer.isDisabled())
                    .accountExpired(customer.isAccountExpired())
                    .accountLocked(customer.isAccountLocked())
                    .credentialsExpired(customer.isCredentialsExpired())
                    .roles(roleList.toArray(new String[0]))
                    .build();
        } else {
            throw new UsernameNotFoundException("User Name is not Found");
        }
    }
}