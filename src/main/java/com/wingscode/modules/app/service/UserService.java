package com.wingscode.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.modules.app.entity.UserEntity;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);


}
