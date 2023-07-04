package org.hejia.jrb.core.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.UserLoginRecord;
import org.hejia.jrb.core.service.UserLoginRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户登录历史记录api
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/core/userLoginRecord")
public class AdminUserLoginRecordController {

    private final UserLoginRecordService userLoginRecordService;

    @GetMapping("/listTop/{userId}")
    public Result listTop(@PathVariable("userId") Long userId) {
        List<UserLoginRecord> userLoginRecordList = userLoginRecordService.listTop(userId);
        return Result.success().data("list", userLoginRecordList);
    }

}
