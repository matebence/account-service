package com.blesk.authorizationserver.Controller.ResourceControllers;

import com.blesk.authorizationserver.Model.Preferences.Preferences;
import com.blesk.authorizationserver.Service.Preferences.PreferencesServiceImpl;
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
public class PreferencesResource {

    private PreferencesServiceImpl preferencesService;

    @Autowired
    public PreferencesResource(PreferencesServiceImpl preferencesService) {
        this.preferencesService = preferencesService;
    }

    @GetMapping("/preferences/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Preferences>> retrieveAllPreferences(@PathVariable int pageNumber, @PathVariable int pageSize) {
        List<Preferences> preferences = this.preferencesService.getAllPreferences(pageNumber, pageSize);
        CollectionModel<List<Preferences>> collectionModel = new CollectionModel(preferences);

        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(pageNumber, pageSize)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(++pageNumber, pageSize)).withRel("next-range"));

        return collectionModel;
    }

    @GetMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Preferences> retrievePreferences(@PathVariable long preferenceId) {
        Preferences preferences = this.preferencesService.getPreference(preferenceId);

        EntityModel<Preferences> EntityModel = new EntityModel<Preferences>(preferences);
        EntityModel.add(linkTo(methodOn(this.getClass()).retrievePreferences(preferenceId)).withSelfRel());
        EntityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPreferences(0 ,10)).withRel("all-preferences"));

        return EntityModel;
    }

    @DeleteMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePreferences(@PathVariable long preferenceId) {
        this.preferencesService.deletePreference(preferenceId);
    }

    @PostMapping("/preferences")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createPreferences(@Valid @RequestBody Preferences preferences) {
        Preferences preference = this.preferencesService.createPreference(preferences);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{preferenceId}")
                .buildAndExpand(preference.getPreferenceId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/preferences/{preferenceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePreferences(@Valid @RequestBody Preferences preferences, @PathVariable long preferenceId) {
        if (this.preferencesService.getPreference(preferenceId) != null) {
            preferences.setPreferenceId(preferenceId);
        }
        this.preferencesService.updatePreference(preferences);
        return ResponseEntity.noContent().build();
    }
}