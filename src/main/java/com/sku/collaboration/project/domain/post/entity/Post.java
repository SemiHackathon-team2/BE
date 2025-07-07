package com.sku.collaboration.project.domain.post.entity;


import com.sku.collaboration.project.domain.board.entity.Board;
import com.sku.collaboration.project.domain.user.entity.User;
import com.sku.collaboration.project.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity  //이 클래스가 엔티티 클래스임을 밝히는 어노테이션!! Spring Data JPA에선 반드시 추가해야한다.
@Getter
@Builder //빌더 패턴을 적용할 수 있게 해줌!!
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")  //이 엔티티가 메핑될 실제 데이터베이스 테이블을 지정!!
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //자동으로 ID를 생성해주는 어노테이션!! IDENTITY는, AUTO_INCREMENT처럼 자동으로 번호를 붙인다!!
  private Long id;

  //title이라는 멤버변수를, DB테이블의 하나의 열(Column)으로 매핑하라는 뜻!!
  @Column(nullable = false) //nullable = false이면, 이 컬럼엔 절대 null이 들어가면 안된다는 것을 뜻함!!
  private String title;  //제목

  @Column(nullable = false)
  private String content;  //내용

  @Column(name = "is_anonymous", nullable = false)
  @Builder.Default
  private Boolean isAnonymous = false; //기본값 false

  @Column(name = "like_count", nullable = false)
  @Builder.Default
  private Integer likeCount = 0;

  @Column(name = "comment_count", nullable = false)
  @Builder.Default
  private Integer commentCount = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  //게시글 수정시 사용하는 메소드
  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public void setIsAnonymous(boolean isAnonymous) {
    this.isAnonymous = isAnonymous;
  }

}
