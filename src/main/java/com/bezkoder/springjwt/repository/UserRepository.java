package com.bezkoder.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.User;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	@Modifying
	@Query(value="update User u set u.password=:newPW where u.username like %:name")
	int updatePassword(@Param("name") String name, @Param("newPW") String newPW);

//	@Modifying
//	@Query(value="update User u set u.realName=:realName, u.workId=:workId, u.depart=:depart, u.phone=:phone where u.username like %:name")
//	int updateUserInfo(@Param("name") String name, @Param("realName") String realName,@Param("workId") String workId,
//	@Param("depart") String depart,@Param("phone") String phone);


	int deleteByUsername(String username);
}
