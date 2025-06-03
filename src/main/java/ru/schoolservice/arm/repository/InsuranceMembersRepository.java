package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.InsuranceMembers;
import ru.schoolservice.arm.model.Member;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface InsuranceMembersRepository extends JpaRepository<InsuranceMembers, Integer> {

    @Query("SELECT m FROM Member m WHERE m.id IN (SELECT im.memberId FROM InsuranceMembers im WHERE im.insuranceId = :insuranceId)")
    List<Member> findMembersByInsuranceId(@Param("insuranceId") Integer insuranceId);


    @Query("SELECT DISTINCT m FROM Member m WHERE m.id IN (SELECT im.memberId FROM InsuranceMembers im WHERE im.insuranceId IN :insuranceIds)")
    List<Member> findMembersByInsuranceIds(@Param("insuranceIds") List<Integer> insuranceIds);


    @Query("SELECT im FROM InsuranceMembers im WHERE im.insuranceId = :insuranceId AND im.memberId IN :memberIds")
    List<InsuranceMembers> findByInsuranceIdAndMemberIdIn(
            @Param("insuranceId") Integer insuranceId,
            @Param("memberIds") List<Integer> memberIds
    );
}
