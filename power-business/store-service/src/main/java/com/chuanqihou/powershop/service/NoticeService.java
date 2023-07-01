package com.chuanqihou.powershop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
public interface NoticeService extends IService<Notice>{


    Page<Notice> findNoticeByPage(Page<Notice> page, Notice notice);

    List<Notice> findTopNoticeList();

    Page<Notice> findIndexNoticeByPage();

}
