package com.vicayala.demotravel.api.controllers;

import com.vicayala.demotravel.infraestructure.abstract_services.ModifyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "user")
@AllArgsConstructor
@Tag(name = "user")
public class AppUserController {

    private final ModifyUserService modifyUserService;

    @Operation(summary = "enable or disable user")
    @PatchMapping(path = "/enable-or-disable")
    public ResponseEntity<Map<String, Boolean>> enableOrDisable(@RequestParam String username){
        return ResponseEntity.ok(this.modifyUserService.enabled(username));
    }

    @Operation(summary = "add role to user")
    @PatchMapping(path = "/add-role")
    public ResponseEntity<Map<String, Set<String>>> addRole(
            @RequestParam String username, @RequestParam String role){
        return ResponseEntity.ok(this.modifyUserService.addRole(username, role));
    }

    @Operation(summary = "delete role to user")
    @PatchMapping(path = "/remove-role")
    public ResponseEntity<Map<String, Set<String>>> removeRole(
            @RequestParam String username, @RequestParam String role){
        return ResponseEntity.ok(this.modifyUserService.removeRole(username, role));
    }
}
