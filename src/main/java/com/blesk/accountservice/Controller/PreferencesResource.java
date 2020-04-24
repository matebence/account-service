package com.blesk.accountservice.Controller;

import com.blesk.accountservice.DTO.JwtMapper;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.AccountPreferences;
import com.blesk.accountservice.Model.Preferences;
import com.blesk.accountservice.Service.Preferences.PreferencesServiceImpl;
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
public class PreferencesResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private PreferencesServiceImpl preferencesService;

    @Autowired
    public PreferencesResource(PreferencesServiceImpl preferencesService) {
        this.preferencesService = preferencesService;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PostMapping("/preferences")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Preferences> createPreferences(@Valid @RequestBody Preferences preferences, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("CREATE_PREFERENCES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Preferences preference = this.preferencesService.createPreference(preferences);
        if (preference == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.CREATE_PREFERENCE);
        }

        EntityModel<Preferences> entityModel = new EntityModel<Preferences>(preference);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePreferences(preference.getPreferenceId(), httpServletRequest, httpServletResponse)).withRel("preference"));

        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @DeleteMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deletePreferences(@PathVariable long preferenceId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("DELETE_PREFERENCES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Boolean result;
        try {
            result = this.preferencesService.softDeletePreference(preferenceId);
        } catch (AccountServiceException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw ex;
        }

        if (!result) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.DELETE_PREFERENCE);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PutMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePreferences(@Valid @RequestBody Preferences preferences, @PathVariable long preferenceId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("UPDATE_PREFERENCES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Preferences preference = this.preferencesService.getPreference(preferenceId, false);
        if (preference == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.GET_PREFERENCE);
        }

        preference.setName(preferences.getName());

        for (AccountPreferences accountPreference : preference.getAccountPreferences()) {
            for (AccountPreferences accountPreferences : preferences.getAccountPreferences()) {
                if (accountPreferences.getDeleted() == null) {
                    preference.addAccount(accountPreferences);
                } else if (accountPreferences.getDeleted()) {
                    preference.removeAccount(accountPreference);
                } else {
                    accountPreference.setAccounts(accountPreferences.getAccounts());
                }
            }
        }

        if (!this.preferencesService.updatePreference(preference)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.UPDATE_PREFERENCE);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @GetMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Preferences> retrievePreferences(@PathVariable long preferenceId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_PREFERENCES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Preferences preferences = this.preferencesService.getPreference(preferenceId, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        if (preferences == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.GET_PREFERENCE);
        }

        EntityModel<Preferences> entityModel = new EntityModel<Preferences>(preferences);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePreferences(preferenceId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(PreferencesResource.DEFAULT_NUMBER, PreferencesResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-preferences"));

        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @GetMapping("/preferences/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Preferences>> retrieveAllPreferences(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_PREFERENCES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        List<Preferences> preferences = this.preferencesService.getAllPreferences(pageNumber, pageSize, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        if (preferences.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.GET_ALL_PREFERENCES);
        }

        CollectionModel<List<Preferences>> collectionModel = new CollectionModel(preferences);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));

        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PostMapping("/preferences/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Preferences>> searchForPreferences(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_PREFERENCES")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        if (search.get(Keys.PAGINATION) == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.PAGINATION_ERROR);
        }

        Map<String, Object> preferences = this.preferencesService.searchForPreferences(search, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        if (preferences == null || preferences.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.SEARCH_ERROR);
        }

        CollectionModel<List<Preferences>> collectionModel = new CollectionModel((List<Preferences>) preferences.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForPreferences(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) preferences.get("hasPrev")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForPreferences(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        }
        if ((boolean) preferences.get("hasNext")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForPreferences(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        }

        return collectionModel;
    }
}