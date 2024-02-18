package cn.kiroe.mall.search.controller;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.search.dto.SearchResponseDTO;
import cn.kiroe.mall.search.param.SearchParam;
import cn.kiroe.mall.search.service.SearchService;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author Kiro
 * @Date 2024/01/27 15:51
 **/
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /**
     * 上架商品
     *
     * @param skuId
     * @return
     */
    @GetMapping("/api/list/inner/upperGoods/{skuId}")
    public Result upperGoods(@PathVariable("skuId") Long skuId) {
        searchService.upperGoods(skuId);
        return Result.ok();
    }

    /**
     * 下架商品
     *
     * @param skuId
     * @return
     */
    @GetMapping("/api/list/inner/lowerGoods/{skuId}")
    public Result lowerGoods(@PathVariable("skuId") Long skuId) {
        searchService.lowerGoods(skuId);
        return Result.ok();
    }

    /**
     * 更新商品incrHotScore
     *
     * @param skuId
     * @return
     */
    @GetMapping("/api/list/inner/incrHotScore/{skuId}")
    public Result incrHotScore(@PathVariable("skuId") Long skuId) {
        searchService.incrHotScore(skuId);
        return Result.ok();
    }

    @GetMapping("/list")
    public Result list(SearchParam searchParam){
        SearchResponseDTO search = searchService.search(searchParam);
        return Result.ok(search);
    }

}
