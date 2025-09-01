package br.edu.ifpb.dac.groupd.model.repository;

import br.edu.ifpb.dac.groupd.model.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
