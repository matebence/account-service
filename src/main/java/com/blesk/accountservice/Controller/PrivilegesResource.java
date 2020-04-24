package com.blesk.accountservice.Controller;

import com.blesk.accountservice.DTO.JwtMapper;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
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
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("CREATE_PRIVILEGES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Privileges privilege = this.privilegesService.createPrivilege(privileges);
        if (privilege == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.CREATE_PRIVILEGE);
        }

        EntityModel<Privileges> entityModel = new EntityModel<Privileges>(privilege);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePrivileges(privilege.getPrivilegeId(), httpServletRequest, httpServletResponse)).withRel("privilege"));

        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @DeleteMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deletePrivileges(@PathVariable long privilegeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("DELETE_PRIVILEGES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Boolean result;
        try {
            result = this.privilegesService.softDeletePrivilege(privilegeId);
        } catch (AccountServiceException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw ex;
        }

        if (!result) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.DELETE_PRIVILEGE);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PutMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePrivileges(@Valid @RequestBody Privileges privileges, @PathVariable long privilegeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("UPDATE_PRIVILEGES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Privileges privilege = this.privilegesService.getPrivilege(privilegeId);
        if (privilege == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.GET_PRIVILEGE);
        }

        privilege.setName(privileges.getName());
        if (!this.privilegesService.updatePrivilege(privilege)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.UPDATE_PRIVILEGE);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @GetMapping("/privileges/{privilegeId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Privileges> retrievePrivileges(@PathVariable long privilegeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_PRIVILEGES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Privileges privileges = this.privilegesService.getPrivilege(privilegeId);
        if (privileges == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.GET_PRIVILEGE);
        }

        EntityModel<Privileges> entityModel = new EntityModel<Privileges>(privileges);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePrivileges(privilegeId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(PrivilegesResource.DEFAULT_NUMBER, PrivilegesResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-privileges"));

        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @GetMapping("/privileges/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Privileges>> retrieveAllPrivileges(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_PRIVILEGES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        List<Privileges> privileges = this.privilegesService.getAllPrivileges(pageNumber, pageSize);
        if (privileges.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.GET_ALL_PRIVILEGES);
        }

        CollectionModel<List<Privileges>> collectionModel = new CollectionModel(privileges);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPrivileges(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));

        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PostMapping("/privileges/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Privileges>> searchForPrivileges(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_PRIVILEGES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        if (search.get(Keys.PAGINATION) == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.PAGINATION_ERROR);
        }

        Map<String, Object> privileges = this.privilegesService.searchForPrivileges(search);
        if (privileges == null || privileges.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.SEARCH_ERROR);
        }

        CollectionModel<List<Privileges>> collectionModel = new CollectionModel((List<Privileges>) privileges.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForPrivileges(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) privileges.get("hasPrev")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForPrivileges(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        }
        if ((boolean) privileges.get("hasNext")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForPrivileges(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        }

        return collectionModel;
    }
}