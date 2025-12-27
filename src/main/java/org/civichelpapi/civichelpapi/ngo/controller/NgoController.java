package org.civichelpapi.civichelpapi.ngo.controller;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.ngo.service.NgoService;
import org.civichelpapi.civichelpapi.common.response.ApiResponse;
import org.civichelpapi.civichelpapi.common.service.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ngos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('NGO')")
public class NgoController {

    private final NgoService ngoService;

    @PostMapping("/reports/{reportId}/offer-help")
    public ResponseEntity<?> offerHelp(@PathVariable Long reportId) {

        Long ngoId = JwtUtil.getUserIdFromContext();
        var offer = ngoService.offerHelp(ngoId,reportId);
        return ResponseEntity.ok(
                ApiResponse.success(offer)
        );
    }


    @GetMapping("/reports/unresolved")
    public ResponseEntity<?> unresolvedReports() {
        return ResponseEntity.ok(
                ApiResponse.success(ngoService.getUnresolvedReports())
        );
    }
}
