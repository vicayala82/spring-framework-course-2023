package com.vicayala.demotravel.api.controllers;

import com.vicayala.demotravel.api.models.request.TourRequest;
import com.vicayala.demotravel.api.models.request.TourResponse;
import com.vicayala.demotravel.api.models.response.ErrorsResponse;
import com.vicayala.demotravel.infraestructure.abstract_services.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "tour")
@AllArgsConstructor
@Tag(name = "Tour")
public class TourController {

    private final ITourService tourService;

    @ApiResponse(responseCode = "400",
            description = "When Request have invalid o missing fields",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorsResponse.class))}
    )
    @Operation(summary = "Save tour based on list of hotels and list of fly")
    @PostMapping
    public ResponseEntity<TourResponse> post(@Valid @RequestBody TourRequest request){
        return ResponseEntity.ok(this.tourService.create(request));
    }

    @Operation(summary = "Return tour by id")
    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(this.tourService.read(id));
    }
    @Operation(summary = "Delete tour by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.tourService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Delete ticket of tour by ticket_id")
    @PatchMapping(path = "{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId, @PathVariable UUID ticketId){
        this.tourService.removeTicket(tourId, ticketId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId, @PathVariable Long flyId){
        var response = Collections.singletonMap("ticketId",this.tourService.addTicket(tourId, flyId));
        return ResponseEntity.ok(response);
    }
    @PatchMapping(path = "{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> removeReservation(@PathVariable Long tourId, @PathVariable UUID ticketId){
        this.tourService.removeTicket(tourId, ticketId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tourId}/add_ticket/{hotelId}")
    public ResponseEntity<Map<String, UUID>> addReservation(
            @PathVariable Long tourId,
            @PathVariable Long hotelId,
            @RequestParam Integer totalDays
    ){
        var response = Collections
                .singletonMap("reservationId",this.tourService.addReservation(tourId, hotelId, totalDays));
        return ResponseEntity.ok(response);
    }
}
