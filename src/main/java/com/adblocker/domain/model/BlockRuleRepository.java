package com.adblocker.domain.model;

import java.util.List;

public interface BlockRuleRepository {
    List<BlockRule> findActiveRulesByUserId(String userId);
}