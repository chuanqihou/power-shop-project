package com.chuanqihou.powershop.service.impl;

import com.chuanqihou.powershop.service.ImportService;
import com.chuanqihou.powershop.service.ProdService;
import com.chuanqihou.powershop.util.ThreadPoolUtil;
import com.chuanqihou.powershop.dao.ProdEsDao;
import lombok.extern.slf4j.Slf4j;
import com.chuanqihou.powershop.model.ProdEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author 传奇后
 * @date 2023/6/30 14:51
 * @description mysql数据导入到es中
 */
@Slf4j
@Service
public class ImportServiceImpl implements CommandLineRunner, ImportService {

    /**
     * 注入商品业务层接口
     */
    @Autowired
    private ProdService prodService;

    /**
     * 注入商品ESmapper层接口
     */
    @Autowired
    private ProdEsDao prodEsDao;

    /**
     * 分页查询每页查询数量
     * 即每次从数据库中分页查询 size 条数据并将其导入到es中
     */
    @Value("${importes.size}")
    private int size;

    /**
     * 分页条件：商品更新起始时间
     */
    private Date t1;

    /**
     * 分页条件：商品更新结束时间
     */
    private Date t2;

    /**
     * 全量导入（项目启动后自动导入全部数据到es）
     *  使用批量导入，根据分页，每次导入一页。
     *  使用线程池，多线程导入，提高效率
     */
    @Override
    public void importAll() {
        log.info("===========全量导入开始==========");
        // 查询需要导入的数据的总条数
        int count = prodService.selectProdCount(null, null);
        // 根据总条数计算总页数
        int pageTotal = count % size == 0 ? count / size : (count / size) + 1;
        // 循环总页数，每次循环将当前页的数据导入到es，使用多线程提高速度和效率，减少导入时间
        for (int i = 1; i <= pageTotal; i++) {
            // 当前页码
            final int currentPage = i;
            ThreadPoolUtil.executor.execute(() -> {
                // 查询当前页数据
                List<ProdEs> prodEsList = prodService.selectProdEsByPage(currentPage,size,null,null);
                // 导入到es中
                prodEsDao.saveAll(prodEsList);
            });
        }
        // 记录导入结束后的时间 t1
        t1 = new Date();
    }

    /**
     * 增量导入（定时任务）
     */
    @Override
    @Scheduled(initialDelay = 2*60*1000,fixedDelay = 2*60*1000)
    public void importUpdate() {
        log.info("===========增量导入开始==========");
        // 记录增量导入开始的时间 t2
        t2 = new Date();
        // 查询需要导入的数据的总条数
        int count = prodService.selectProdCount(t1, t2);
        if (count == 0) {
            log.info("没有需要增量导入的数据");
            return;
        }
        // 根据总条数计算总页数
        int pageTotal = count % size == 0 ? count / size : (count / size) + 1;
        //定义一个计数器（线程数为总页数）
        CountDownLatch countDownLatch = new CountDownLatch(pageTotal);
        // 循环总页数，每次循环将当前页的数据导入到es
        for (int i = 1; i <= pageTotal; i++) {
            final int currentPage = i;
            ThreadPoolUtil.executor.execute(() -> {
                // 查询当前页数据
                List<ProdEs> prodEsList = prodService.selectProdEsByPage(currentPage,size,t1,t2);
                // 导入到es中
                prodEsDao.saveAll(prodEsList);
                // 每执行完成一个线程，计数器 -1
                countDownLatch.countDown();
            });
        }
        try {
            // 在所有线程没有执行完成之前程序阻塞在这里
            // 若阻塞时间超过10秒，或者计数器线程数为0 则终止阻塞，继续执行剩余代码
            countDownLatch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 更新起始时间
        t1 = t2;
    }

    /**
     * 项目启动后执行此方法
     * @param args 参数
     * @throws Exception 异常
     */
    @Override
    public void run(String... args) throws Exception {
        // 调用全量导入方法
        //importAll();
    }
}
