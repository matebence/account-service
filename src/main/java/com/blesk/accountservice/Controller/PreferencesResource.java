package com.blesk.accountservice.Controller;

import com.blesk.accountservice.Model.Preferences.AccountPreferenceItems;
import com.blesk.accountservice.Service.Preferences.PreferencesServiceImpl;
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
public class PreferencesResource {

    private PreferencesServiceImpl preferencesService;

    @Autowired
    public PreferencesResource(PreferencesServiceImpl preferencesService) {
        this.preferencesService = preferencesService;
    }

    @PreAuthorize("hasRole('VIEW_ALL_PREFERENCES')")
    @GetMapping("/preferences/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<AccountPreferenceItems>> retrieveAllPreferences(@PathVariable int pageNumber, @PathVariable int pageSize) {
        List<AccountPreferenceItems> accountPreferenceItems = this.preferencesService.getAllPreferences(pageNumber, pageSize);
        CollectionModel<List<AccountPreferenceItems>> collectionModel = new CollectionModel(accountPreferenceItems);

        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(pageNumber, pageSize)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(++pageNumber, pageSize)).withRel("next-range"));

        return collectionModel;
    }

    @PreAuthorize("hasRole('VIEW_PREFERENCES')")
    @GetMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<AccountPreferenceItems> retrievePreferences(@PathVariable long preferenceId) {
        AccountPreferenceItems accountPreferenceItems = this.preferencesService.getPreference(preferenceId);

        EntityModel<AccountPreferenceItems> entityModel = new EntityModel<AccountPreferenceItems>(accountPreferenceItems);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePreferences(preferenceId)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(0, 10)).withRel("all-preferences"));

        return entityModel;
    }

    @PreAuthorize("hasRole('DELETE_PREFERENCES')")
    @DeleteMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePreferences(@PathVariable long preferenceId) {
        this.preferencesService.deletePreference(preferenceId);
    }

    @PreAuthorize("hasRole('CREATE_PREFERENCES')")
    @PostMapping("/preferences")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<AccountPreferenceItems> createPreferences(@Valid @RequestBody AccountPreferenceItems accountPreferenceItems) {
        AccountPreferenceItems preference = this.preferencesService.createPreference(accountPreferenceItems);
        EntityModel<AccountPreferenceItems> entityModel = new EntityModel<AccountPreferenceItems>(preference);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePreferences(preference.getPreferences().getPreferenceId())).withRel("preference"));

        return entityModel;
    }

    @PreAuthorize("hasRole('UPDATE_PREFERENCES')")
    @PutMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePreferences(@Valid @RequestBody AccountPreferenceItems accountPreferenceItems, @PathVariable long preferenceId) {
        if (this.preferencesService.getPreference(preferenceId) != null) {
            accountPreferenceItems.getPreferences().setPreferenceId(preferenceId);
            this.preferencesService.updatePreference(accountPreferenceItems);
        }

        return ResponseEntity.noContent().build();
    }
}