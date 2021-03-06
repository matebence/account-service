package com.blesk.accountservice.Controller;

import com.blesk.accountservice.Exception.AccountServiceException;
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

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PostMapping("/preferences")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Preferences> createPreferences(@Valid @RequestBody Preferences preferences, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Preferences preference = this.preferencesService.createPreference(preferences);
        if (preference == null) throw new AccountServiceException(Messages.CREATE_PREFERENCE, HttpStatus.BAD_REQUEST);

        EntityModel<Preferences> entityModel = new EntityModel<Preferences>(preference);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePreferences(preference.getPreferenceId(), httpServletRequest, httpServletResponse)).withRel("preference"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @DeleteMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deletePreferences(@PathVariable long preferenceId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Preferences preference = this.preferencesService.getPreference(preferenceId);
        if (preference == null) throw new AccountServiceException(Messages.GET_PREFERENCE, HttpStatus.NOT_FOUND);
        if(!this.preferencesService.deletePreference(preference)) throw new AccountServiceException(Messages.GET_PREFERENCE, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PutMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePreferences(@Valid @RequestBody Preferences preferences, @PathVariable long preferenceId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Preferences preference = this.preferencesService.getPreference(preferenceId);
        if (preference == null) throw new AccountServiceException(Messages.GET_PREFERENCE, HttpStatus.BAD_REQUEST);

        if (!this.preferencesService.updatePreference(preference, preferences)) throw new AccountServiceException(Messages.UPDATE_PREFERENCE, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Preferences> retrievePreferences(@PathVariable long preferenceId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Preferences preferences = this.preferencesService.getPreference(preferenceId);
        if (preferences == null) throw new AccountServiceException(Messages.GET_PREFERENCE, HttpStatus.BAD_REQUEST);

        EntityModel<Preferences> entityModel = new EntityModel<Preferences>(preferences);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePreferences(preferenceId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(PreferencesResource.DEFAULT_NUMBER, PreferencesResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-preferences"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/preferences/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Preferences>> retrieveAllPreferences(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Preferences> preferences = this.preferencesService.getAllPreferences(pageNumber, pageSize);
        if (preferences == null || preferences.isEmpty()) throw new AccountServiceException(Messages.GET_ALL_PREFERENCES, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Preferences>> collectionModel = new CollectionModel(preferences);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/preferences/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Preferences>> searchForPreferences(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (search.get(Keys.PAGINATION) == null) throw new AccountServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);
        Map<String, Object> preferences = this.preferencesService.searchForPreference(search);
        if (preferences == null || preferences.isEmpty()) throw new AccountServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Preferences>> collectionModel = new CollectionModel((List<Preferences>) preferences.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForPreferences(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) preferences.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForPreferences(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) preferences.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForPreferences(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping("/preferences/join/{columName}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Preferences>> joinPreferences(@PathVariable String columName, @RequestBody List<Long> ids, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Preferences> preferences = this.preferencesService.getPreferencesForJoin(ids, columName);
        if (preferences == null || preferences.isEmpty()) throw new AccountServiceException(Messages.GET_ALL_PREFERENCES, HttpStatus.BAD_REQUEST);
        return new CollectionModel(preferences);
    }
}