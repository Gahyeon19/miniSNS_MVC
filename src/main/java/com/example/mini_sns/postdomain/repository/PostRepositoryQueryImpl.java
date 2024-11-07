//package com.example.mini_sns.postdomain.repository;
//
//import com.example.mini_sns.postdomain.domain.*;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityManager;
//import org.springframework.stereotype.Repository;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
//import static com.example.mini_sns.postdomain.domain.QPost.post;
//
//@Repository
//public class PostRepositoryQueryImpl implements PostRepositoryQuery{
//    private final EntityManager em;
//    private final JPAQueryFactory queryFactory;
//
//    public PostRepositoryQueryImpl(EntityManager em) {
//        this.em = em;
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//    @Override
//    public List<Post> getAllPostWithLikesAndWriter(DynamicSearchCond searchCond) {
//        List<Post> fetch = queryFactory
//                .select(post)
//                .from(post)
//                .where(
//                        likesGoe(searchCond.getLikes()),
//                        writerEq(searchCond.getUseId())
//                )
//               .fetch();
//
//        return fetch;
//    }
//
//    private BooleanExpression likesGoe(Integer likes) {
//        return (likes != null) ? post.likes.goe(likes) : null;
//    }
//
//    private BooleanExpression writerEq(String useId) {
//        return StringUtils.hasText(useId) ? post.writer.userName.eq(useId) : null;
//    }
//
//}
