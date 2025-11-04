package com.CABA.CabaPro.dto.api.auth;

import com.CABA.CabaPro.dto.api.arbitro.ArbitroProfileResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de autenticación con token JWT")
public class AuthResponse {
    private String token;
    private ArbitroProfileResponse profile;

    public AuthResponse() {
    }

    public AuthResponse(String token, ArbitroProfileResponse profile) {
        this.token = token;
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArbitroProfileResponse getProfile() {
        return profile;
    }

    public void setProfile(ArbitroProfileResponse profile) {
        this.profile = profile;
    }
}
