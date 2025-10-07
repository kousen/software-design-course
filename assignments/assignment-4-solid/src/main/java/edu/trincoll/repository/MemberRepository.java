package edu.trincoll.repository;

import edu.trincoll.model.Member;
import edu.trincoll.model.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    List<Member> findByMembershipType(MembershipType membershipType);

    List<Member> findByBooksCheckedOutGreaterThan(int count);
}
