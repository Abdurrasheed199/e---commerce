package com.zindabad.ecommerce.controller;

import com.zindabad.ecommerce.model.Role;
import com.zindabad.ecommerce.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {


    final
    RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/roles", method = RequestMethod.GET)
    public List roles() {

        return (List) roleRepository.findAll();
    }

    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/role/create", method = RequestMethod.POST)
    public ResponseEntity.BodyBuilder createRole(@RequestParam String name){
        Role role = new Role(name);
        roleRepository.save(role);

        return ResponseEntity.status(HttpStatus.OK);
    }

    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/role/update/{id}", method = RequestMethod.PUT)
    public String updateRole(@PathVariable("id") int id, @RequestParam String name){
        Role role = roleRepository.findById(id).get();
        role.setName(name);

        return "Role updated successfully.";
    }

    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/role/delete/{id}", method = RequestMethod.POST)
    public String deleteRole(@PathVariable("id") int id){
        Role role = roleRepository.findById(id).get();
        roleRepository.delete(role);

        return "Role deleted successfully.";
    }

}
