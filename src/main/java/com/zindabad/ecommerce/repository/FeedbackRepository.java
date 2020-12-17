package com.zindabad.ecommerce.repository;

import com.zindabad.ecommerce.model.Feedback;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
   List<Feedback> findFeedbacksByCustomerId(long id);
}
