package com.chuanqihou.powershop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuanqihou.powershop.constant.ProductConstant;
import com.chuanqihou.powershop.domain.Member;
import com.chuanqihou.powershop.domain.ProdComm;
import com.chuanqihou.powershop.feign.ProductMemberFeign;
import com.chuanqihou.powershop.mapper.ProdCommMapper;
import com.chuanqihou.powershop.service.ProdCommService;
import com.chuanqihou.powershop.util.AuthUtil;
import com.chuanqihou.powershop.vo.ProdCommOverviewVo;
import com.chuanqihou.powershop.vo.ProdCommVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 传奇后
 * @date 2023/6/25 10:35
 * @description
 */
@Service
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService{

    @Autowired
    private ProdCommMapper prodCommMapper;

    @Autowired
    private ProductMemberFeign productMemberFeign;

    @Override
    public Page<ProdComm> findProCommPage(Page<ProdComm> page, ProdComm prodComm) {
        return prodCommMapper.selectPage(page, new LambdaQueryWrapper<ProdComm>()
                .eq(!AuthUtil.getShopId().equals(ProductConstant.ADMIN_SHOP_ID), ProdComm::getShopId, AuthUtil.getShopId())
                .like(StringUtils.hasText(prodComm.getProdName()), ProdComm::getProdName, prodComm.getProdName())
                .eq(!ObjectUtils.isEmpty(prodComm.getStatus()), ProdComm::getStatus, prodComm.getStatus())
                .orderByDesc(ProdComm::getCreateTime)
        );
    }

    @Override
    public void modifyProdComm(ProdComm prodComm) {
        ProdComm newProdComm = prodCommMapper.selectById(prodComm.getProdCommId());
        newProdComm.setReplyContent(prodComm.getReplyContent());
        newProdComm.setStatus(prodComm.getStatus());
        prodCommMapper.updateById(prodComm);
    }

    @Override
    public ProdCommOverviewVo findProdCommCount(Long prodId) {

        ProdCommOverviewVo prodCommOverviewVo = prodCommMapper.selectProdCommCount(prodId);
        if (!prodCommOverviewVo.getPraiseNumber().equals(0)) {
            BigDecimal bigDecimal = new BigDecimal(prodCommOverviewVo.getPraiseNumber())
                    .divide(new BigDecimal(prodCommOverviewVo.getNumber()), 2, RoundingMode.UP)
                    .multiply(new BigDecimal(100));
            prodCommOverviewVo.setPositiveRating(bigDecimal);
        }
        return prodCommOverviewVo;
    }

    /**
     * 查询商品评论详细信息
     * @param page 分页条件
     * @param prodComm 评论中的商品id
     * @return
     */
    @Override
    public Page<ProdCommVO> findWxProdCommCount(Page<ProdComm> page, ProdComm prodComm) {
        Integer evaluate = prodComm.getEvaluate();
        // 根据条件分页查询
        Page<ProdComm> prodCommPage = prodCommMapper.selectPage(page, new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodComm.getProdId())
                .eq(ProdComm::getStatus, 1)
                .eq(!ObjectUtils.isEmpty(evaluate) && !(evaluate.equals(-1) || evaluate.equals(3)), ProdComm::getEvaluate, evaluate)
                .isNotNull(evaluate.equals(3),ProdComm::getPics)
        );

        Page<ProdCommVO> prodCommVOPage = new Page<>();
        BeanUtils.copyProperties(prodCommPage,prodCommVOPage,"records");
        // 获取所有评论所对应的用户openid
        List<ProdComm> records = prodCommPage.getRecords();
        List<String> openidList = records.stream().map(ProdComm::getOpenId).collect(Collectors.toList());
        // RPC 根据评论内的openid获取用户头像和昵称信息
        List<Member> memberList = productMemberFeign.getMemberListByRemoteAndOpenIds(openidList);
/*        List<Member> memberList = new ArrayList<>();
        String json = "  {\n" +
                "    \"id\": 6,\n" +
                "    \"openId\": \"ohbMy6rgx3WFbMcwBxndKkqTcjDI\",\n" +
                "    \"nickName\": \"传奇后\",\n" +
                "    \"realName\": null,\n" +
                "    \"userMail\": null,\n" +
                "    \"payPassword\": null,\n" +
                "    \"userMobile\": \"18774137163\",\n" +
                "    \"updateTime\": \"2023-06-30 08:33:35\",\n" +
                "    \"createTime\": \"2023-06-29 14:07:59\",\n" +
                "    \"userRegip\": \"192.168.137.102\",\n" +
                "    \"userLasttime\": \"2023-07-03 14:21:55\",\n" +
                "    \"userLastip\": \"192.168.137.102\",\n" +
                "    \"sex\": \"0\",\n" +
                "    \"birthDate\": null,\n" +
                "    \"pic\": \"https://thirdwx.qlogo.cn/mmopen/vi_32/qeuRr1yS5A1M7NK41g8ZJFS3xs5HZURj4PXbiaYhWhmhdlPTrwjtiaj9GsMzIcy3f6t2jEvYMOx9MY3Ad7rR7Heg/132\",\n" +
                "    \"status\": 1,\n" +
                "    \"score\": null\n" +
                "  }";
        Member member1 = JSON.parseObject(json, Member.class);*/
        //memberList.add(member1);

        productMemberFeign.testInfo();

        List<ProdCommVO> prodCommVOS = new ArrayList<>();
        records.forEach(pc ->{
            ProdCommVO prodCommVO = new ProdCommVO();
            BeanUtils.copyProperties(pc, prodCommVO);
            // 找到评论所对应的用户
            for (Member member : memberList) {
                if (pc.getOpenId().equals(member.getOpenId())) {
                    prodCommVO.setPic(member.getPic());
                    prodCommVO.setNickName(member.getNickName());
                    break;
                }
            }
            prodCommVOS.add(prodCommVO);
        });
        prodCommVOPage.setRecords(prodCommVOS);
        return prodCommVOPage;
    }
}
