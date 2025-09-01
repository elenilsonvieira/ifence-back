package br.edu.ifpb.dac.groupd.presentation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.dac.groupd.business.service.FeedbackService;
import br.edu.ifpb.dac.groupd.presentation.dto.FeedbackRequest;
import br.edu.ifpb.dac.groupd.presentation.dto.FeedbackResponse;

@RestController
@RequestMapping("/feedbacks")
@Validated
public class FeedbackResource {

    @Autowired
    private FeedbackService service;

    @PostMapping
    public ResponseEntity<FeedbackResponse> create(@Valid @RequestBody FeedbackRequest req) {
        var saved = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<Page<FeedbackResponse>> list(Pageable pageable) {
        var page = service.list(pageable);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
