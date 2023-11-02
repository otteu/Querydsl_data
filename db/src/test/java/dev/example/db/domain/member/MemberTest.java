package dev.example.db.domain.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.example.db.domain.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    public void before() {
        Team teanA = new Team("teanA");
        Team teanB = new Team("teanB");
        em.persist(teanA);
        em.persist(teanB);

        Member member1 = new Member("member1", 10, teanA);
        Member member2 = new Member("member2", 20, teanA);

        Member member3 = new Member("member3", 30, teanB);
        Member member4 = new Member("member4", 40, teanB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @DisplayName("test")
    @Test
    void test() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember member = new QMember("m");

        Member member1 = queryFactory.selectFrom(member)
                .fetchOne();

        System.out.println(member1);


    }
}