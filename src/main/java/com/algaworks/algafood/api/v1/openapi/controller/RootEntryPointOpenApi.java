package com.algaworks.algafood.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Root Entry Point")
public interface RootEntryPointOpenApi {

    @Operation(summary = "Lista os principais endpoints")
    ResponseEntity<?> root();
}
