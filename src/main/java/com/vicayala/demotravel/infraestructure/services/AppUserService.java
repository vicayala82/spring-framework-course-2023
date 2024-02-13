package com.vicayala.demotravel.infraestructure.services;

import com.vicayala.demotravel.domain.repositories.mongo.AppUserRepository;
import com.vicayala.demotravel.infraestructure.abstract_services.ModifyUserService;
import com.vicayala.demotravel.util.exceptions.UsernameNotFoundExceptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AppUserService implements ModifyUserService {

    private final AppUserRepository appUserRepository;


    @Override
    public Map<String, Boolean> enabled(String username) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundExceptions(username)
        );
        user.setEnabled(!user.isEnabled());
        var userSaved = this.appUserRepository.save(user);
        return Collections.singletonMap(userSaved.getUsername(), userSaved.isEnabled());
    }

    @Override
    public Map<String, Set<String>> addRole(String username, String role) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundExceptions(username)
        );
        user.getRole().getGrantedAuthorities().add(role);
        var userSaved = this.appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        log.info("User {} add role {}", userSaved.getUsername(),
                userSaved.getRole().getGrantedAuthorities().toString());
        return Collections.singletonMap(userSaved.getUsername(), authorities);
    }

    @Override
    public Map<String, Set<String>> removeRole(String username, String role) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundExceptions(username)
        );
        user.getRole().getGrantedAuthorities().remove(role);
        var userSaved = this.appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        log.info("Role {}, removed", role);
        return Collections.singletonMap(userSaved.getUsername(), authorities);
    }

    @Transactional(readOnly = true)
    private void loadUserByUsername(String username){
        var user = this.appUserRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundExceptions(username)
        );
    }
}
