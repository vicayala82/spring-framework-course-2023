package com.vicayala.demotravel.api.controllers;

import com.vicayala.demotravel.api.models.request.TicketRequest;
import com.vicayala.demotravel.api.models.response.ErrorsResponse;
import com.vicayala.demotravel.api.models.response.TicketResponse;
import com.vicayala.demotravel.infraestructure.abstract_services.ITicketService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "ticket")
@AllArgsConstructor
@Tag(name = "Ticket")
public class TicketController {

    private final ITicketService ticketService;

    @ApiResponse(responseCode = "400",
            description = "When Request have invalid o missing fields",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorsResponse.class))}
    )
    @PostMapping
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.create(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.read(id));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<TicketResponse> put(@Valid @PathVariable UUID id, @RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam Long flyId){
        return ResponseEntity.ok(Collections.singletonMap("fly_price",
                ticketService.findPrice(flyId)));
    }
}
