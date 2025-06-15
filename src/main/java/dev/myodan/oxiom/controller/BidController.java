package dev.myodan.oxiom.controller;

import dev.myodan.oxiom.dto.BidResponse;
import dev.myodan.oxiom.dto.BidSearch;
import dev.myodan.oxiom.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bids")
public class BidController {

    private final BidService bidService;

    @GetMapping
    public ResponseEntity<Page<BidResponse>> getBids(@RequestParam(required = false) BidSearch bidSearch, Pageable pageable) {
        return ResponseEntity.ok(bidService.getBids(bidSearch, pageable));
    }

}
