package com.sku.collaboration.project.domain.post.service;

import com.sku.collaboration.project.domain.board.entity.Board;
import com.sku.collaboration.project.domain.board.repository.BoardRepository;
import com.sku.collaboration.project.domain.post.dto.request.CreatePostRequest;
import com.sku.collaboration.project.domain.post.dto.request.UpdatePostRequest;
import com.sku.collaboration.project.domain.post.dto.response.PostResponse;
import com.sku.collaboration.project.domain.post.entity.Post;
import com.sku.collaboration.project.domain.post.exception.PostErrorCode;
import com.sku.collaboration.project.domain.post.repository.PostRepository;
import com.sku.collaboration.project.domain.user.entity.User;
import com.sku.collaboration.project.domain.user.exception.UserErrorCode;
import com.sku.collaboration.project.domain.user.repository.UserRepository;
import com.sku.collaboration.project.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final BoardRepository boardRepository;

  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) { //DTO를 인자로 받아,
    log.info("[서비스] 게시글 생성 시도: title={}, content={}, isAnonymous={}, userId={}",
        createPostRequest.getTitle(),
        createPostRequest.getContent(),
        createPostRequest.isAnonymous(),
        createPostRequest.getUserId());

    if (createPostRequest.getTitle() == null || createPostRequest.getTitle().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_TITLE);
    }

    if (createPostRequest.getContent() == null || createPostRequest.getContent().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_CONTENT);
    }

    if (createPostRequest.getTitle().length() > 30) {
      throw new CustomException(PostErrorCode.TITLE_TOO_LONG);
    }

    User user = userRepository.findById(createPostRequest.getUserId())
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    Board board = boardRepository.findById(createPostRequest.getBoardId())
        .orElseThrow(() -> new CustomException(PostErrorCode.BOARD_NOT_FOUND));

    Post post = Post.builder()  //DTO -> Entity 변환 후!
        .title(createPostRequest.getTitle()) //프론트에서 보낸 "title" 값을 Post 객체의 필드로 넣는 과정
        .content(createPostRequest.getContent())
        .isAnonymous(createPostRequest.isAnonymous())  //익명 여부 저장
        .user(user)
        .board(board)
        .build();
    postRepository.save(post);  //Post 객체를 DB에 저장!!

    log.info("[서비스]게시글 생성 완료: id= {}, title= {}, content={}", post.getId(), post.getTitle(),
        post.getContent());
    return PostResponse.of(post); //그리고, 저장된 결과(Entity)를 DTO로 변환해서 반환!!
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByBoard(Long boardId) {
    List<Post> posts = postRepository.findAllByBoardId(boardId);
    return posts.stream()
        .map(PostResponse::of)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public PostResponse getPost(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

    return PostResponse.of(post);
  }

  @Transactional
  public PostResponse updatePost(Long postId, UpdatePostRequest request) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

    post.update(request.getTitle(), request.getContent());
    post.setIsAnonymous(request.isAnonymous());

    log.info("[서비스] 게시글 수정 완료: id={}, title={}, isAnonymous={}",
        post.getId(), post.getTitle(), post.getIsAnonymous());

    return PostResponse.of(post);
  }

  @Transactional
  public void deletePost(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

    postRepository.delete(post);

    log.info("[서비스] 게시글 삭제 완료: id={}", postId);
  }

}
