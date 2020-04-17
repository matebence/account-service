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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class PrivilegesResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private PrivilegesServiceImpl privilegesService;

    @Autowired
    public PrivilegesResource(PrivilegesServiceImpl privilegesService) {
        this.privilegesService = privilegesService;
    }

    @PreAuthorize("hasRole('VIEW_ALL_PRIVILEGES')")
    @GetMapping("/privileges/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Privileges>> retrieveAllPrivileges(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Privileges> privileges = this.privilegesService.getAllPrivileges(pageNumber, pageSize);
        CollectionModel<List<Privileges>> collectionModel = new CollectionModel(privileges);

        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));

        return collectionModel;
    }

    @PreAuthorize("hasRole('VIEW_PRIVILEGES')")
    @GetMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Privileges> retrievePrivileges(@PathVariable long privilegeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Privileges privileges = this.privilegesService.getPrivilege(privilegeId);

        EntityModel<Privileges> entityModel = new EntityModel<Privileges>(privileges);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePrivileges(privilegeId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(PrivilegesResource.DEFAULT_NUMBER, PrivilegesResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-privileges"));

        return entityModel;
    }

    @PreAuthorize("hasRole('DELETE_PRIVILEGES')")
    @DeleteMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deletePrivileges(@PathVariable long privilegeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.privilegesService.deletePrivilege(privilegeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CREATE_PRIVILEGES')")
    @PostMapping("/privileges")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Privileges> createPrivileges(@Valid @RequestBody Privileges privileges, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Privileges privilege = this.privilegesService.createPrivilege(privileges);
        EntityModel<Privileges> entityModel = new EntityModel<Privileges>(privilege);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePrivileges(privilege.getPrivilegeId(), httpServletRequest, httpServletResponse)).withRel("privilege"));

        return entityModel;
    }

    @PreAuthorize("hasRole('UPDATE_PRIVILEGES')")
    @PutMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePrivileges(@Valid @RequestBody Privileges privileges, @PathVariable long privilegeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Privileges privilege = this.privilegesService.getPrivilege(privilegeId);
        if (privilege != null) {
            privilege.setName(privileges.getName());
            privilege.setUpdatedBy(privileges.getUpdatedBy());
            privilege.setDeletedBy(privileges.getDeletedBy());
            this.privilegesService.updatePrivilege(privilege);
        }

        return ResponseEntity.noContent().build();
    }
}