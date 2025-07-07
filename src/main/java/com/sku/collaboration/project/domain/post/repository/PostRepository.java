package com.sku.collaboration.project.domain.post.repository;

import com.sku.collaboration.project.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllByBoardId(Long boardId);

}
