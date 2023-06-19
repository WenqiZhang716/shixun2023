package org.ejavaexample.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.ejavaexample.auth.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Optional<User> findByUsernameAndPassword(String username, String password);

  @Modifying
  @Query(value="update User u set u.password=:newPW where u.username like %:name")
  int updatePassword(@Param("name") String name, @Param("newPW") String newPW);

  @Modifying
  @Query(value="update User u set u.nickName=:nickName,u.realName=:realName, u.age=:age, u.sex=:sex, u.defaultAddress=:defaultAddress where u.username like %:name")
  int updateUserInfo(@Param("name") String name,@Param("realName") String realName,@Param("nickName") String nickName,@Param("age") int age,@Param("sex") int sex,
                     @Param("defaultAddress") String address);


  int deleteByUsername(String username);
  @Modifying
  @Query(value="update User u set u.isCheck=1, u.realName=:realName where u.username like %:name")
  int checkIt(@Param("name")String name,@Param("realName")String realName);
}
