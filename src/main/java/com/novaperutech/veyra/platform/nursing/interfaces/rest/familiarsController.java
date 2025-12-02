package com.novaperutech.veyra.platform.nursing.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/familiars",produces = APPLICATION_JSON_VALUE)
 @Tag(name ="Familiars",description = "Available endpoints for familiars")
public class familiarsController {

}
