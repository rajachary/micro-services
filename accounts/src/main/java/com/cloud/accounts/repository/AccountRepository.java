package com.cloud.accounts.repository;

import com.cloud.accounts.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
}
