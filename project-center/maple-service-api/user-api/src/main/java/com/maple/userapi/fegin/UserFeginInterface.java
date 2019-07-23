package com.maple.userapi.fegin;

import com.maple.userapi.bean.BaseUser;
import org.springframework.web.bind.annotation.RequestMapping;

public interface UserFeginInterface {

    @RequestMapping(value = "getUserInfo")
    BaseUser getUserInfo();
}
