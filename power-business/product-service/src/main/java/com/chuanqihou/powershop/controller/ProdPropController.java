package com.chuanqihou.powershop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuanqihou.powershop.domain.ProdProp;
import com.chuanqihou.powershop.domain.ProdPropValue;
import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.service.ProdPropService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/6/27 18:36
 * @description 商品规格属性管理接口
 */
@RestController
@RequestMapping("/prod/spec")
@Api(tags = "商品规格属性管理接口")
public class ProdPropController {

    /**
     * 商品规格属性业务层接口
     */
    @Autowired
    private ProdPropService prodPropService;

    /**
     * 分页查询所有规格信息（包括规格属性所对应的值）
     * @param page 分页属性
     * @param prodProp 分页条件
     * @return 分页对象
     */
    @GetMapping("/page")
    @ApiOperation("分页查询所有规格信息（包括规格属性所对应的值）")
    @PreAuthorize("hasAuthority('prod:spec:page')")
    public Result<Page<ProdProp>> getProdPropAndValueByPage(Page<ProdProp> page, ProdProp prodProp) {
        // 调用业务层的分页方法
        Page<ProdProp> prodPropPage = prodPropService.findProdPropAndValueByPage(page, prodProp);
        // 返回数据
        return Result.success(prodPropPage);
    }

    /**
     * 查询所有规格信息（不包括所对应的值）
     * @return 规格信息
     */
    @GetMapping("/list")
    @ApiOperation("查询所有规格信息（不包括所对应的值）")
    public Result<List<ProdProp>> getProdPropAll() {
        // 调用业务层查询方法
        List<ProdProp> prodPropList = prodPropService.findProdPropAll();
        // 返回数据
        return Result.success(prodPropList);
    }

    /**
     * 根据规格Id查询对应的规格值
     * @param prodId 规格ID
     * @return 规格值
     */
    @GetMapping("/listSpecValue/{propId}")
    @ApiOperation("根据规格Id查询对应的规格值")
    @PreAuthorize("hasAuthority('prod:spec:info')")
    public Result<List<ProdPropValue>> getAllProdPropValue(@PathVariable("propId") Long prodId) {
        // 调用业务层的查询方法
        List<ProdPropValue> prodPropValueList = prodPropService.findProdPropValueByPropId(prodId);
        // 返回数据
        return Result.success(prodPropValueList);
    }

    /**
     * 新增规格信息和所对应的规格值
     *
     * @param prodProp 规格信息
     * @return result
     */
    @PostMapping
    @ApiOperation("新增规格信息和所对应的规格值")
    @PreAuthorize("hasAuthority('prod:spec:save')")
    public Result<Object> addProdPropAndValue(@RequestBody ProdProp prodProp) {
        // 调用业务层的新增方法
        prodPropService.saveProdPropAndValue(prodProp);
        // 返回数据
        return Result.success();
    }

    /**
     * 修改规格信息
     *
     * @param prodProp 规格信息
     * @return result
     */
    @PutMapping
    @ApiOperation("修改规格信息")
    @PreAuthorize("hasAuthority('prod:spec:update')")
    public Result<Object> editProdPropAndValue(@RequestBody ProdProp prodProp) {
        // 调用业务层的修改方法
        prodPropService.modifyProdPropAndValue(prodProp);
        // 返回数据
        return Result.success();
    }

    /**
     * 根据规格ID删除规格信息以及所对应的规格值
     *
     * @param prodPropId 规格ID
     * @return result
     */
    @DeleteMapping("/{prodPropId}")
    @ApiOperation("根据规格ID删除规格信息以及所对应的规格值")
    @PreAuthorize("hasAuthority('prod:spec:delete')")
    public Result<Object> cutProdPropAndValue(@PathVariable("prodPropId") Long prodPropId) {
        // 调用业务层的删除方法
        prodPropService.removeProdPropAndValue(prodPropId);
        // 返回数据
        return Result.success();
    }

}
