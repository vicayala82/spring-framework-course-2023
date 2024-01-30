package com.vicayala.demotravel.api.controllers;


import com.vicayala.demotravel.infraestructure.abstract_services.IReportService;
import com.vicayala.demotravel.util.ServiceConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "report")
@AllArgsConstructor
@Tag(name = "Report")
public class ReportController {

    private final IReportService reportService;


    @GetMapping
    public ResponseEntity<Resource> get(){
        var headers = new HttpHeaders();
        headers.setContentType(ServiceConstants.FORCE_DOWNLOAD);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, ServiceConstants.FORCE_DOWNLOAD_HEADER_VALUE);
        var file = this.reportService.readFile();
        ByteArrayResource response = new ByteArrayResource(file);
        return ResponseEntity.ok().headers(headers)
                .contentLength(file.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response);

    }
}
