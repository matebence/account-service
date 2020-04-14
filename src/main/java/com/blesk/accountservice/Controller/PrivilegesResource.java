package com.blesk.accountservice.Controller;

import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Service.Privileges.PrivilegesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class PrivilegesResource {

    private PrivilegesServiceImpl privilegesService;

    @Autowired
    public PrivilegesResource(PrivilegesServiceImpl privilegesService) {
        this.privilegesService = privilegesService;
    }

    @PreAuthorize("hasRole('VIEW_ALL_PRIVILEGES')")
    @GetMapping("/privileges/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Privileges>> retrieveAllPrivileges(@PathVariable int pageNumber, @PathVariable int pageSize) {
        List<Privileges> privileges = this.privilegesService.getAllPrivileges(pageNumber, pageSize);
        CollectionModel<List<Privileges>> collectionModel = new CollectionModel(privileges);

        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(pageNumber, pageSize)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(++pageNumber, pageSize)).withRel("next-range"));

        return collectionModel;
    }

    @PreAuthorize("hasRole('VIEW_PRIVILEGES')")
    @GetMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Privileges> retrievePrivileges(@PathVariable long privilegeId) {
        Privileges privileges = this.privilegesService.getPrivilege(privilegeId);

        EntityModel<Privileges> entityModel = new EntityModel<Privileges>(privileges);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePrivileges(privilegeId)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(0, 10)).withRel("all-privileges"));

        return entityModel;
    }

    @PreAuthorize("hasRole('DELETE_PRIVILEGES')")
    @DeleteMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePrivileges(@PathVariable long privilegeId) {
        this.privilegesService.deletePrivilege(privilegeId);
    }

    @PreAuthorize("hasRole('CREATE_PRIVILEGES')")
    @PostMapping("/privileges")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Privileges> createPrivileges(@Valid @RequestBody Privileges privileges) {
        Privileges privilege = this.privilegesService.createPrivilege(privileges);
        EntityModel<Privileges> entityModel = new EntityModel<Privileges>(privilege);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePrivileges(privilege.getPrivilegeId())).withRel("privilege"));

        return entityModel;
    }

    @PreAuthorize("hasRole('UPDATE_PRIVILEGES')")
    @PutMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePrivileges(@Valid @RequestBody Privileges privileges, @PathVariable long privilegeId) {
        if (this.privilegesService.getPrivilege(privilegeId) != null) {
            privileges.setPrivilegeId(privilegeId);
            this.privilegesService.updatePrivilege(privileges);
        }

        return ResponseEntity.noContent().build();
    }
}