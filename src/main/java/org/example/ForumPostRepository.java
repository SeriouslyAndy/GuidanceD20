package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    // Fetches entries sorted by date descending to prioritize active topics
    List<ForumPost> findAllByOrderByCreatedAtDesc();
}