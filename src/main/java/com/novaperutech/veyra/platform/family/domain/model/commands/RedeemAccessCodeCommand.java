package com.novaperutech.veyra.platform.family.domain.model.commands;

public record RedeemAccessCodeCommand( Long userId,
                                       String code) {
}
