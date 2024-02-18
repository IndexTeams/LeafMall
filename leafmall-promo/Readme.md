# 查询出今天参与秒杀活动的商品
1. 必须通过审核
2. 开始时间的日期必须 今天的时间相同
3. 库存>0
```sql
select * from seckill_goods
where status = 'CHECKED_PASS'
and '2024-02-05' = DATE_FORMAT(start_time,'%Y-%m-%d')
and stock_count > 0
```

```sql
select  DATE_FORMAT("2025-02-06",'%Y-%m-%d')

```
