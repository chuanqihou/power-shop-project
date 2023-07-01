package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.domain.Notice;
import com.chuanqihou.powershop.mapper.NoticeMapper;
import com.chuanqihou.powershop.service.NoticeService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Page<Notice> findNoticeByPage(Page<Notice> page, Notice notice) {
        return noticeMapper.selectPage(page, new LambdaQueryWrapper<Notice>()
                .like(StringUtils.hasText(notice.getTitle()), Notice::getTitle, notice.getTitle())
                .eq(!ObjectUtils.isEmpty(notice.getStatus()), Notice::getStatus, notice.getStatus())
                .eq(!ObjectUtils.isEmpty(notice.getIsTop()), Notice::getIsTop, notice.getIsTop())
                .orderByDesc(Notice::getCreateTime)
        );
    }

    @Override
    public List<Notice> findTopNoticeList() {
        return noticeMapper.selectList(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getStatus, 1)
                .eq(Notice::getIsTop, 1)
                .orderByDesc(Notice::getCreateTime)
        );
    }

    @Override
    public Page<Notice> findIndexNoticeByPage() {
        return noticeMapper.selectPage(new Page<Notice>(1,10),new LambdaQueryWrapper<Notice>()
                .eq(Notice::getStatus, 1)
                .eq(Notice::getIsTop, 1)
                .orderByDesc(Notice::getCreateTime)
        );
    }

}
