package com.blesk.accountservice.Controller;

import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Service.Privileges.PrivilegesServiceImpl;
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
public class PrivilegesResource {

    private PrivilegesServiceImpl privilegesService;

    @Autowired
    public PrivilegesResource(PrivilegesServiceImpl privilegesService) {
        this.privilegesService = privilegesService;
    }

    @GetMapping("/privileges/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Privileges>> retrieveAllPrivileges(@PathVariable int pageNumber, @PathVariable int pageSize) {
        List<Privileges> privileges = this.privilegesService.getAllPrivileges(pageNumber, pageSize);
        CollectionModel<List<Privileges>> collectionModel = new CollectionModel(privileges);

        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(pageNumber, pageSize)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(++pageNumber, pageSize)).withRel("next-range"));

        return collectionModel;
    }

    @GetMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Privileges> retrievePrivileges(@PathVariable long privilegeId) {
        Privileges privileges = this.privilegesService.getPrivilege(privilegeId);

        EntityModel<Privileges> EntityModel = new EntityModel<Privileges>(privileges);
        EntityModel.add(linkTo(methodOn(this.getClass()).retrievePrivileges(privilegeId)).withSelfRel());
        EntityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(0 ,10)).withRel("all-privileges"));

        return EntityModel;
    }

    @DeleteMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePrivileges(@PathVariable long privilegeId) {
        this.privilegesService.deletePrivilege(privilegeId);
    }

    @PostMapping("/privileges")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createPrivileges(@Valid @RequestBody Privileges privileges) {
        Privileges privilege = this.privilegesService.createPrivilege(privileges);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{privilegeId}")
                .buildAndExpand(privilege.getPrivilegeId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePrivileges(@Valid @RequestBody Privileges privileges, @PathVariable long privilegeId) {
        if (this.privilegesService.getPrivilege(privilegeId) != null) {
            privileges.setPrivilegeId(privilegeId);
        }
        this.privilegesService.updatePrivilege(privileges);
        return ResponseEntity.noContent().build();
    }
}