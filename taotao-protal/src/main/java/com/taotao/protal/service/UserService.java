package com.taotao.protal.service;

import com.taotao.pojo.TbUser;

public interface UserService {

	public TbUser getUserByToken(String token);
}
