package com.blesk.accountservice.Controller;

import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Service.Roles.RolesServiceImpl;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class RolesResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private RolesServiceImpl rolesService;

    @Autowired
    public RolesResource(RolesServiceImpl rolesService) {
        this.rolesService = rolesService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Roles> createRoles(@Valid @RequestBody Roles roles, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Roles role = this.rolesService.createRole(roles);
        if (role == null) throw new AccountServiceException(Messages.CREATE_ROLE, HttpStatus.BAD_REQUEST);

        EntityModel<Roles> entityModel = new EntityModel<Roles>(role);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveRoles(role.getRoleId(), httpServletRequest, httpServletResponse)).withRel("role"));
        return entityModel;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/roles/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteRoles(@PathVariable long roleId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Roles role = this.rolesService.getRole(roleId);
        if (role == null) throw new AccountServiceException(Messages.GET_ROLE, HttpStatus.NOT_FOUND);
        if(!this.rolesService.deleteRole(role)) throw new AccountServiceException(Messages.DELETE_ROLE, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/roles/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateRoles(@Valid @RequestBody Roles roles, @PathVariable long roleId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Roles role = this.rolesService.getRole(roleId);
        if (role == null) throw new AccountServiceException(Messages.GET_ROLE, HttpStatus.BAD_REQUEST);

        if (!this.rolesService.updateRole(role, roles)) throw new AccountServiceException(Messages.UPDATE_ROLE, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Roles> retrieveRoles(@PathVariable long roleId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Roles roles = this.rolesService.getRole(roleId);
        if (roles == null) throw new AccountServiceException(Messages.GET_ROLE, HttpStatus.BAD_REQUEST);

        EntityModel<Roles> entityModel = new EntityModel<Roles>(roles);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveRoles(roleId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllRoles(RolesResource.DEFAULT_NUMBER, RolesResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-roles"));
        return entityModel;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Roles>> retrieveAllRoles(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Roles> roles = this.rolesService.getAllRoles(pageNumber, pageSize);
        if (roles == null || roles.isEmpty()) throw new AccountServiceException(Messages.GET_ALL_ROLES, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Roles>> collectionModel = new CollectionModel(roles);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllRoles(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllRoles(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Roles>> searchForRoles(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (search.get(Keys.PAGINATION) == null) throw new AccountServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);
        Map<String, Object> roles = this.rolesService.searchForRole(search);
        if (roles == null || roles.isEmpty()) throw new AccountServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Roles>> collectionModel = new CollectionModel((List<Roles>) roles.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForRoles(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) roles.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForRoles(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) roles.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForRoles(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping("/roles/join/{columName}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Roles>> joinRoles(@PathVariable String columName, @RequestBody List<Long> ids, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Roles> roles = this.rolesService.getRolesForJoin(ids, columName);
        if (roles == null || roles.isEmpty()) throw new AccountServiceException(Messages.GET_ALL_ROLES, HttpStatus.BAD_REQUEST);
        return new CollectionModel(roles);
    }
}