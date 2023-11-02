package dev.example.db.repository.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.example.db.domain.member.Member;
import dev.example.db.domain.member.QMember;
import dev.example.db.domain.team.QTeam;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static dev.example.db.domain.member.QMember.member;
import static dev.example.db.domain.team.QTeam.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * 단순한 페이징, fetchResults() 사용
     */
    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition,
                                                Pageable pageable) {
        QueryResults<MemberTeamDto> results = queryFactory
                .select(new QMemberTeamDto(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name))
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();



        return new PageImpl<>(content, pageable, total);
    }


    JPAQuery<Member> countQuery = queryFactory
            .select(member)
            .from(member)
            .leftJoin(member.team, team)
            .where(usernameEq(condition.getUsername()),
                    teamNameEq(condition.getTeamName()),
                    ageGoe(condition.getAgeGoe()),
                    ageLoe(condition.getAgeLoe()));
// return new PageImpl<>(content, pageable, total);
    return PageableExecutionUtils.getPage(content, pageable,
    countQuery::fetchCount);


    /**
     * 복잡한 페이징
     * 데이터 조회 쿼리와, 전체 카운트 쿼리를 분리
     */
    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition,
                                                 Pageable pageable) {
        List<MemberTeamDto> content = queryFactory
                .select(new QMemberTeamDto(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name))
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

//        JPAQuery<Member> = queryFactory
//                .select(member)
//                .from(member)
//                .leftJoin(member.team, team)
//                .where(usernameEq(condition.getUsername()),
//                        teamNameEq(condition.getTeamName()),
//                        ageGoe(condition.getAgeGoe()),
//                        ageLoe(condition.getAgeLoe())
//                );

        JPAQuery<Member> countQuery = queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()));
// return new PageImpl<>(content, pageable, total);
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }





    @Override
    public Member findMemberqdl(String username) {
        return queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetchOne()
                ;
    }

    @Override
    public List<Member> findMembersqdl() {
        return queryFactory
                .selectFrom(member)
                .fetch()
                ;
    }

    // 리스트 조회
    @Override
    public List<Member> fetch() {
        return queryFactory.selectFrom(member)
                .fetch();
    }

    // 단건 조회
    @Override
    public Member fetchOne() {
        return queryFactory.selectFrom(member)
                .fetchOne();
    }

    @Override
    public QueryResults<Member> total() {
        QueryResults<Member> memberQueryResults = queryFactory.selectFrom(member)
                .fetchResults();
        // 토탈 타운트
        // memberQueryResults.getTotal()

        // 조회 리스트
        // List<Member> results = memberQueryResults.getResults();

        return memberQueryResults;

    }

    @Override
    public long totalCount() {
        return queryFactory
                .selectFrom(member)
                .fetchCount();
    }

    @Override
    public List<Member> sort() {
        return queryFactory.selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(
                        member.age.desc(),
                        member.username.asc().nullsLast())
                .fetch()
                ;
    }

    @Override
    public List<Member> paging1() {
        return queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch()
                ;
    }

    @Override
    public QueryResults<Member> paging2() {
        return queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults()
                ;
    }

    @Override
    public List<Tuple> group() {
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();
        Tuple tuple = result.get(0);

        tuple.get(team.name);
        tuple.get(member.age.avg());

        return result
                ;
    }

    @Override
    public List<Member> join() {
        return queryFactory.selectFrom(member)
                // inner join
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch()
                ;
    }

    @Override
    public List<Tuple> join_on() {
        return queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                .fetch()
                ;
    }


    @Override
    public List<Tuple> join_on_no_relation() {
        return queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch()
                ;
    }

    @PersistenceUnit
    EntityManagerFactory emf;
    public void fetch_join() {

        Member member1 = queryFactory
                .selectFrom(member)
                .where(member.username.eq("memberq"))
                .fetchOne();

        // 페치 조인 미적용
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(member1.getTeam());

        queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("memberq"))
                .fetchOne();

    }

    @Override
    public void subQuery() {

        QMember memberSub = new QMember("memberSub");

        queryFactory.selectFrom(member)
                .where(member.age.eq(
                        JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        // 인절 여러개 
        queryFactory.selectFrom(member)
                .where(member.age.in(
                        JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        queryFactory.select(member.age,
                        JPAExpressions
                            .select(memberSub.age.max())
                            .from(memberSub)
                .from(member)
                .fetch();

    }

    public void constant() {

        List<Tuple> result = queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        List<String> fetch = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .fetch();


    }

    public void findDto() {

        QMember memberSub = new QMember("memberSub");

        queryFactory.select(
                Projections.fields(Member.class,
                        member.username.as("name"),

                        ExpressionUtils.as(JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub), "age"
                        ))
        ).from(member)
        .fetch();

    }

    public void dynamicQuery_BooleanBuilder(String usernameCond, Integer ageCond) {

        BooleanBuilder builder = new BooleanBuilder();
        if(usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }
        if(ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }
        queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }


    public void dynimic_WhereParam() throws Exception {
        String usernameParam = "member1";
        Integer ageParam = 10;
        List<Member> result = searchMember2(usernameParam, ageParam);
    }
    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(usernameEq(usernameCond), ageEq(ageCond))
                .fetch();
    }
    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }
    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    /*  동적 쿼리 dto 조회 하기
    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getUsername())) {
            builder.and(member.username.eq(condition.getUsername()));
        }
        if (hasText(condition.getTeamName())) {
            builder.and(team.name.eq(condition.getTeamName()));
        }
        if (condition.getAgeGoe() != null) {
            builder.and(member.age.goe(condition.getAgeGoe()));
        }
        if (condition.getAgeLoe() != null) {
            builder.and(member.age.loe(condition.getAgeLoe()));
        }
        return queryFactory
                .select(new QMemberTeamDto(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name))
                .from(member)
                .leftJoin(member.team, team)
                .where(builder)
                .fetch();
    }

    // whrer 동적 쿼리 dto 조회
    public List<MemberTeamDto> search(MemberSearchCondition condition) {
        return queryFactory
            .select(new QMemberTeamDto(
                                    member.id,
                                    member.username,
                                    member.age,
                                    team.id,
                                    team.name))
                            .from(member)
                            .leftJoin(member.team, team)
                            .where(usernameEq(condition.getUsername()),
                                    teamNameEq(condition.getTeamName()),
                                    ageGoe(condition.getAgeGoe()),
                                    ageLoe(condition.getAgeLoe()))
                            .fetch();
    }
        private BooleanExpression usernameEq(String username) {
        return isEmpty(username) ? null : member.username.eq(username);
    }
        private BooleanExpression teamNameEq(String teamName) {
        return isEmpty(teamName) ? null : team.name.eq(teamName);
    }
        private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe == null ? null : member.age.goe(ageGoe);
    }
        private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe == null ? null : member.age.loe(ageLoe);
    }
    참고: where 절에 파라미터 방식을 사용

    */

    
}
