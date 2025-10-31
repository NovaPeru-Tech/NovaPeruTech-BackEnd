package com.novaperutech.veyra.platform.residents.interfaces.rest.controllers;

import com.novaperutech.veyra.platform.residents.domain.model.services.ResidentCommandService;
import com.novaperutech.veyra.platform.residents.domain.model.services.ResidentQueryService;
import com.novaperutech.veyra.platform.residents.interfaces.rest.resources.*;
import com.novaperutech.veyra.platform.residents.interfaces.rest.transform.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/residents")
public class ResidentController {

    private final ResidentCommandService residentCommandService;
    private final ResidentQueryService residentQueryService;

    public ResidentController(ResidentCommandService residentCommandService,
                              ResidentQueryService residentQueryService) {
        this.residentCommandService = residentCommandService;
        this.residentQueryService = residentQueryService;
    }

    @GetMapping
    public List<ResidentResource> getAll() {
        return residentQueryService.handle().stream()
                .map(ResidentResourceFromEntityAssembler::toResource)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResidentResource> getById(@PathVariable Long id) {
        return residentQueryService.handle(id)
                .map(ResidentResourceFromEntityAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResidentResource> create(@RequestBody CreateResidentResource resource) {
        var command = CreateResidentCommandFromResourceAssembler.toCommand(resource);
        var created = residentCommandService.handle(command);
        var residentResource = ResidentResourceFromEntityAssembler.toResource(created);
        return new ResponseEntity<>(residentResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResidentResource> update(@PathVariable Long id, @RequestBody UpdateResidentResource resource) {
        var command = UpdateResidentCommandFromResourceAssembler.toCommand(id, resource);
        var updated = residentCommandService.handle(command);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ResidentResourceFromEntityAssembler.toResource(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        residentCommandService.handle(new com.novaperutech.veyra.platform.residents.domain.model.commands.DeleteResidentCommand(id));
        return ResponseEntity.noContent().build();
    }
}
