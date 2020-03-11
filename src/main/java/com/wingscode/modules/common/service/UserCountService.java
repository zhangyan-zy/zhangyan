package com.wingscode.modules.common.service;

import java.util.List;
import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-11 11:01
 * Description：<描述>
 */
public interface UserCountService {

    List  leadersList(Map<String, Object> params);

    List orderAgentList(Map<String, Object> params);

    // List  zxLeadersList(Map<String, Object> params);
    // List addAndShowLeadsList(Map<String, Object> params);
}
