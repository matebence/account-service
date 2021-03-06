package com.blesk.accountservice.Controller;

import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Service.Privileges.PrivilegesServiceImpl;
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
public class PrivilegesResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private PrivilegesServiceImpl privilegesService;

    @Autowired
    public PrivilegesResource(PrivilegesServiceImpl privilegesService) {
        this.privilegesService = privilegesService;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PostMapping("/privileges")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Privileges> createPrivileges(@Valid @RequestBody Privileges privileges, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Privileges privilege = this.privilegesService.createPrivilege(privileges);
        if (privilege == null) throw new AccountServiceException(Messages.CREATE_PRIVILEGE, HttpStatus.BAD_REQUEST);

        EntityModel<Privileges> entityModel = new EntityModel<Privileges>(privilege);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePrivileges(privilege.getPrivilegeId(), httpServletRequest, httpServletResponse)).withRel("privilege"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @DeleteMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deletePrivileges(@PathVariable long privilegeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Privileges privilege = this.privilegesService.getPrivilege(privilegeId);
        if (privilege == null) throw new AccountServiceException(Messages.GET_PRIVILEGE, HttpStatus.NOT_FOUND);
        if (!this.privilegesService.deletePrivilege(privilege)) throw new AccountServiceException(Messages.DELETE_PRIVILEGE, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PutMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePrivileges(@Valid @RequestBody Privileges privileges, @PathVariable long privilegeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Privileges privilege = this.privilegesService.getPrivilege(privilegeId);
        if (privilege == null) throw new AccountServiceException(Messages.GET_PRIVILEGE, HttpStatus.BAD_REQUEST);

        if (!this.privilegesService.updatePrivilege(privilege, privileges)) throw new AccountServiceException(Messages.UPDATE_PRIVILEGE, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @GetMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Privileges> retrievePrivileges(@PathVariable long privilegeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Privileges privileges = this.privilegesService.getPrivilege(privilegeId);
        if (privileges == null) throw new AccountServiceException(Messages.GET_PRIVILEGE, HttpStatus.BAD_REQUEST);

        EntityModel<Privileges> entityModel = new EntityModel<Privileges>(privileges);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePrivileges(privilegeId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(PrivilegesResource.DEFAULT_NUMBER, PrivilegesResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-privileges"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @GetMapping("/privileges/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Privileges>> retrieveAllPrivileges(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Privileges> privileges = this.privilegesService.getAllPrivileges(pageNumber, pageSize);
        if (privileges == null || privileges.isEmpty()) throw new AccountServiceException(Messages.GET_ALL_PRIVILEGES, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Privileges>> collectionModel = new CollectionModel(privileges);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PostMapping("/privileges/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Privileges>> searchForPrivileges(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (search.get(Keys.PAGINATION) == null) throw new AccountServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);
        Map<String, Object> privileges = this.privilegesService.searchForPrivilege(search);
        if (privileges == null || privileges.isEmpty()) throw new AccountServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Privileges>> collectionModel = new CollectionModel((List<Privileges>) privileges.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForPrivileges(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) privileges.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForPrivileges(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) privileges.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForPrivileges(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping("/privileges/join/{columName}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Privileges>> joinPrivileges(@PathVariable String columName, @RequestBody List<Long> ids, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Privileges> privileges = this.privilegesService.getPrivilegesForJoin(ids, columName);
        if (privileges == null || privileges.isEmpty()) throw new AccountServiceException(Messages.GET_ALL_PRIVILEGES, HttpStatus.BAD_REQUEST);
        return new CollectionModel(privileges);
    }
}