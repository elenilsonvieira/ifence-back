package br.edu.ifpb.dac.groupd.business.service;

import br.edu.ifpb.dac.groupd.model.entities.Feedback;
import br.edu.ifpb.dac.groupd.model.repository.FeedbackRepository;
import br.edu.ifpb.dac.groupd.presentation.dto.FeedbackRequest;
import br.edu.ifpb.dac.groupd.presentation.dto.FeedbackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository repository;

    public FeedbackResponse create(FeedbackRequest req) {
        Feedback entity = new Feedback();
        entity.setName(req.getName());
        entity.setContact(req.getContact());
        entity.setMessage(req.getMessage());
    entity.setMessageCategory(req.getMessageCategory());
        entity.setCreatedAt(OffsetDateTime.now());
        Feedback saved = repository.save(entity);

        return toResponse(saved);
    }

    public Page<FeedbackResponse> list(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public void delete(Long id) {
        if (id == null) return;
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    private FeedbackResponse toResponse(Feedback saved) {
        FeedbackResponse res = new FeedbackResponse();
        res.setId(saved.getId());
        res.setName(saved.getName());
        res.setContact(saved.getContact());
        res.setMessage(saved.getMessage());
        res.setCreatedAt(saved.getCreatedAt());
    res.setMessageCategory(saved.getMessageCategory());
        return res;
    }
}
