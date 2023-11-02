package dev.example.api.service.member;

import dev.example.db.domain.member.Member;
import dev.example.db.domain.team.Team;
import dev.example.db.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("Test")
    @Test
    void test() {
        // given
//        Team teanA = new Team("teanA");
//        Team teanB = new Team("teanB");

        Member member1 = new Member("member1", 10, null);
        Member member2 = new Member("member2", 20, null);

//        Member member3 = new Member("member3", 30, teanB);
//        Member member4 = new Member("member4", 40, teanB);


        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();
//        memberRepository.save(member3);
//        memberRepository.save(member4);
        Member memberqdl = memberRepository.findMemberqdl(member1.getUsername());
        System.out.println("memberqdl = " + memberqdl.getUsername());
        System.out.println("memberqdl = " + memberqdl.getAge());
        // when


        // then


    }



}