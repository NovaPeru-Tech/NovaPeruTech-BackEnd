package com.novaperutech.veyra.platform.family.interfaces.rest.resources;
import java.time.LocalDateTime;
public record AccessCodeResource(String code,String familyEmail,Long residentId,LocalDateTime expiresAt) {
}
