package com.CABA.CabaPro.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CABA.CabaPro.dto.api.ApiResponse;
import com.CABA.CabaPro.dto.api.PagedResponse;
import com.CABA.CabaPro.dto.api.torneo.TorneoResponse;
import com.CABA.CabaPro.model.Torneo;
import com.CABA.CabaPro.service.TorneoService;
import com.CABA.util.mapper.TorneoMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/torneos")
@Tag(name = "Torneos")
public class TorneoApiController {

    @Autowired
    private TorneoService torneoService;

    @GetMapping
    @Operation(summary = "Lista de torneos paginada")
    public ResponseEntity<ApiResponse> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<TorneoResponse> all = torneoService.getAllTorneos().stream()
                .map(TorneoMapper::toResponse)
                .collect(Collectors.toList());
        PageRequest pr = PageRequest.of(Math.max(0, page), Math.max(1, size));
        int start = (int) pr.getOffset();
        int end = Math.min(start + pr.getPageSize(), all.size());
        List<TorneoResponse> slice = start > end ? List.of() : all.subList(start, end);
        Page<TorneoResponse> pageObj = new PageImpl<>(slice, pr, all.size());
        PagedResponse<TorneoResponse> body = PagedResponse.of(pageObj);
        ApiResponse resp = new ApiResponse(true, "OK", body, 200);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalle de un torneo")
    public ResponseEntity<ApiResponse> get(@PathVariable Long id) {
        return torneoService.getTorneoById(id)
                .map(t -> ResponseEntity.ok(new ApiResponse(true, "OK", TorneoMapper.toResponse(t), 200)))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(new ApiResponse(false, "Torneo no encontrado", null, 404)));
    }
}
