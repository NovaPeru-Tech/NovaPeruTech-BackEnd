package com.novaperutech.veyra.platform.health.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/api/v1",produces = APPLICATION_JSON_VALUE)
@RestController
@Tag(name = "")
public class VitalSignsController {
}
