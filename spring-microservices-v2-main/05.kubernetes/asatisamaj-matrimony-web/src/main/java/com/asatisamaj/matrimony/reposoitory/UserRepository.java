/**
 * 
 */
package com.asatisamaj.matrimony.reposoitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asatisamaj.matrimony.domain.User;

/**
 * @author pavan.solapure
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT * FROM MYUSERS", nativeQuery = true)
	List<User> findAllByUsernames(List<String> listOfUsernames);
}
