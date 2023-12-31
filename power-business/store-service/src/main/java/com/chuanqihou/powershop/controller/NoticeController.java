package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.Notice;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.NoticeService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/28 9:33
 * @description
 */
@RestController
@RequestMapping("/shop/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/page")
    public Result<Page<Notice>> getNoticeByPage(Page<Notice> page, Notice notice) {
        Page<Notice> noticePage = noticeService.findNoticeByPage(page, notice);
        return Result.success(noticePage);
    }

    @GetMapping("/topNoticeList")
    public Result<List<Notice>> getTopNoticeList() {
        List<Notice> noticeList = noticeService.findTopNoticeList();
        return Result.success(noticeList);
    }

    @GetMapping("/noticeList")
    public Result<Page<Notice>> getIndexNoticeByPage() {
        Page<Notice> noticePage = noticeService.findIndexNoticeByPage();
        return Result.success(noticePage);
    }

    @GetMapping("/info/{noticeId}")
    public Result<Notice> getNoticeInfo(@PathVariable("noticeId") Long noticeId) {
        Notice notice = noticeService.getById(noticeId);
        return Result.success(notice);
    }

}
