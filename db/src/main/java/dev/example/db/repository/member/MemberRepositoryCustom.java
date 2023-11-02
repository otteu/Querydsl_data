package dev.example.db.repository.member;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import dev.example.db.domain.member.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchCondition condition);
    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);





    Member findMemberqdl(String username);
    List<Member> findMembersqdl();

    List<Member> fetch();

    Member fetchOne();

    QueryResults<Member> total();

    long totalCount();

    // 정렬
    List<Member> sort();

    // 페이징
    List<Member> paging1();

    // 페이징 토탈 카운트
    QueryResults<Member> paging2();

    List<Tuple> group();

    List<Member> join();

    List<Tuple> join_on();

    List<Tuple> join_on_no_relation();

    void fetch_join();

    void subQuery();

    void constant();

    void findDto();

    void dynamicQuery_BooleanBuilder(String usernameCond, Integer ageCond);
}
