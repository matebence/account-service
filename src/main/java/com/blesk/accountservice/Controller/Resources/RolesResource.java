package com.blesk.accountservice.Controller.Resources;

import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Service.Roles.RolesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class RolesResource {

    private RolesServiceImpl rolesService;

    @Autowired
    public RolesResource(RolesServiceImpl rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping("/roles/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Roles>> retrieveAllRoles(@PathVariable int pageNumber, @PathVariable int pageSize) {
        List<Roles> roles = this.rolesService.getAllRoles(pageNumber, pageSize);
        CollectionModel<List<Roles>> collectionModel = new CollectionModel(roles);

        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllRoles(pageNumber, pageSize)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllRoles(++pageNumber, pageSize)).withRel("next-range"));

        return collectionModel;
    }

    @GetMapping("/roles/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Roles> retrieveRoles(@PathVariable long roleId) {
        Roles roles = this.rolesService.getRole(roleId);

        EntityModel<Roles> EntityModel = new EntityModel<Roles>(roles);
        EntityModel.add(linkTo(methodOn(this.getClass()).retrieveRoles(roleId)).withSelfRel());
        EntityModel.add(linkTo(methodOn(this.getClass()).retrieveAllRoles(0, 10)).withRel("all-roles"));

        return EntityModel;
    }

    @DeleteMapping("/roles/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRoles(@PathVariable long roleId) {
        this.rolesService.deleteRole(roleId);
    }

    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createRoles(@Valid @RequestBody Roles roles) {
        Roles role = this.rolesService.createRole(roles);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{roleId}")
                .buildAndExpand(role.getRoleId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/roles/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateRoles(@Valid @RequestBody Roles roles, @PathVariable long roleId) {
        if (this.rolesService.getRole(roleId) != null) {
            roles.setRoleId(roleId);
        }
        this.rolesService.updateRole(roles);
        return ResponseEntity.noContent().build();
    }
}