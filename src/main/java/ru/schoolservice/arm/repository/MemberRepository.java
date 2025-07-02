package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    List<Member> findMembersByClaimId(Integer claimId);
}
