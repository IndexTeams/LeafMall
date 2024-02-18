# WEB API DOC

**HOST**:http://124.221.21.69

**Version**:1.0


[TOC]

## 获取所有的一级类目接口


**接口地址**:`/admin/product/getCategory1`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`


**接口描述**:

**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明                         | schema                                        |
| ------ | ---------------------------- | --------------------------------------------- |
| 200    | 获取成功，返回所有的一级类目 | 全局统一返回结果«List«FirstLevelCategoryDTO»» |
| 201    | 获取数据失败                 |                                               |
| 401    | Unauthorized                 |                                               |
| 403    | Forbidden                    |                                               |
| 404    | Not Found                    |                                               |

**响应参数**:


| 参数名称         | 参数说明     | 类型           | schema                |
| ---------------- | ------------ | -------------- | --------------------- |
| code             | 返回码       | integer(int32) | integer(int32)        |
| data             | 返回数据     | array          | FirstLevelCategoryDTO |
| &emsp;&emsp;id   | 一级目录的id | integer(int64) |                       |
| &emsp;&emsp;name | 一级目录名称 | string         |                       |
| message          | 返回消息     | string         |                       |
| ok               |              | boolean        |                       |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"id": 0,
			"name": ""
		}
	],
	"message": "",
	"ok": true
}
```

## 获取目标一级类目所属的二级类目接口


**接口地址**:`/admin/product/getCategory2/{firstLevelCategoryId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称             | 参数说明   | 请求类型 | 是否必须 | 数据类型       | schema |
| -------------------- | ---------- | -------- | -------- | -------------- | ------ |
| firstLevelCategoryId | 一级类目Id | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                                         | schema                                         |
| ------ | -------------------------------------------- | ---------------------------------------------- |
| 200    | 获取成功，返回指定一级类目所属的所有二级类目 | 全局统一返回结果«List«SecondLevelCategoryDTO»» |
| 201    | 获取数据失败                                 |                                                |
| 401    | Unauthorized                                 |                                                |
| 403    | Forbidden                                    |                                                |
| 404    | Not Found                                    |                                                |


**响应参数**:


| 参数名称                         | 参数说明     | 类型           | schema                 |
| -------------------------------- | ------------ | -------------- | ---------------------- |
| code                             | 返回码       | integer(int32) | integer(int32)         |
| data                             | 返回数据     | array          | SecondLevelCategoryDTO |
| &emsp;&emsp;firstLevelCategoryId | 一级分类编号 | integer(int64) |                        |
| &emsp;&emsp;id                   | 二级分类id   | integer(int64) |                        |
| &emsp;&emsp;name                 | 二级分类名称 | string         |                        |
| message                          | 返回消息     | string         |                        |
| ok                               |              | boolean        |                        |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"firstLevelCategoryId": 0,
			"id": 0,
			"name": ""
		}
	],
	"message": "",
	"ok": true
}
```

## 获取目标二级类目所属的三级类目接口


**接口地址**:`/admin/product/getCategory3/{secondLevelCategoryId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称              | 参数说明   | 请求类型 | 是否必须 | 数据类型       | schema |
| --------------------- | ---------- | -------- | -------- | -------------- | ------ |
| secondLevelCategoryId | 二级类目Id | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                                         | schema                                        |
| ------ | -------------------------------------------- | --------------------------------------------- |
| 200    | 获取成功，返回指定二级类目所属的所有三级类目 | 全局统一返回结果«List«ThirdLevelCategoryDTO»» |
| 201    | 取数据失败                                   |                                               |
| 401    | Unauthorized                                 |                                               |
| 403    | Forbidden                                    |                                               |
| 404    | Not Found                                    |                                               |


**响应参数**:


| 参数名称                          | 参数说明     | 类型           | schema                |
| --------------------------------- | ------------ | -------------- | --------------------- |
| code                              | 返回码       | integer(int32) | integer(int32)        |
| data                              | 返回数据     | array          | ThirdLevelCategoryDTO |
| &emsp;&emsp;id                    | 三级分类id   | integer(int64) |                       |
| &emsp;&emsp;name                  | 三级分类名称 | string         |                       |
| &emsp;&emsp;secondLevelCategoryId | 二级分类编号 | integer(int64) |                       |
| message                           | 返回消息     | string         |                       |
| ok                                |              | boolean        |                       |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"id": 0,
			"name": "",
			"secondLevelCategoryId": 0
		}
	],
	"message": "",
	"ok": true
}
```

## 获取类目所属平台属性及平台属性值集合接口


**接口地址**:`/admin/product/attrInfoList/{firstLevelCategoryId}/{secondLevelCategoryId}/{thirdLevelCategoryId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称              | 参数说明              | 请求类型 | 是否必须 | 数据类型       | schema |
| --------------------- | --------------------- | -------- | -------- | -------------- | ------ |
| firstLevelCategoryId  | firstLevelCategoryId  | path     | true     | integer(int64) |        |
| secondLevelCategoryId | secondLevelCategoryId | path     | true     | integer(int64) |        |
| thirdLevelCategoryId  | thirdLevelCategoryId  | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                                         | schema                                           |
| ------ | -------------------------------------------- | ------------------------------------------------ |
| 200    | 获取平台属性成功, 返回类目所属的平台属性列表 | 全局统一返回结果«List«PlatformAttributeInfoDTO»» |
| 401    | Unauthorized                                 |                                                  |
| 403    | Forbidden                                    |                                                  |
| 404    | Not Found                                    |                                                  |
| 500    | 服务器异常获取数据失败                       |                                                  |


**响应参数**:


| 参数名称                          | 参数说明       | 类型           | schema                    |
| --------------------------------- | -------------- | -------------- | ------------------------- |
| code                              | 返回码         | integer(int32) | integer(int32)            |
| data                              | 返回数据       | array          | PlatformAttributeInfoDTO  |
| &emsp;&emsp;attrName              | 属性名称       | string         |                           |
| &emsp;&emsp;attrValueList         | 平台属性值列表 | array          | PlatformAttributeValueDTO |
| &emsp;&emsp;&emsp;&emsp;attrId    | 属性id         | integer        |                           |
| &emsp;&emsp;&emsp;&emsp;id        | 平台属性值id   | integer        |                           |
| &emsp;&emsp;&emsp;&emsp;valueName | 属性值名称     | string         |                           |
| &emsp;&emsp;categoryId            | 分类id         | integer(int64) |                           |
| &emsp;&emsp;categoryLevel         | 分类层级       | integer(int32) |                           |
| &emsp;&emsp;id                    | 平台属性id     | integer(int64) |                           |
| message                           | 返回消息       | string         |                           |
| ok                                |                | boolean        |                           |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"attrName": "",
			"attrValueList": [
				{
					"attrId": 0,
					"id": 0,
					"valueName": ""
				}
			],
			"categoryId": 0,
			"categoryLevel": 0,
			"id": 0
		}
	],
	"message": "",
	"ok": true
}
```

## 保存|修改 平台属性及平台属性值


**接口地址**:`/admin/product/saveAttrInfo`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "attrName": "",
  "attrValueList": [
    {
      "attrId": 0,
      "id": 0,
      "valueName": ""
    }
  ],
  "categoryId": 0,
  "categoryLevel": 0,
  "id": 0
}
```


**请求参数**:


| 参数名称                          | 参数说明               | 请求类型 | 是否必须 | 数据类型               | schema                      |
| --------------------------------- | ---------------------- | -------- | -------- | ---------------------- | --------------------------- |
| platformAttributeParam            | PlatformAttributeParam | body     | true     | PlatformAttributeParam | PlatformAttributeParam      |
| &emsp;&emsp;attrName              | 属性名称               |          | false    | string                 |                             |
| &emsp;&emsp;attrValueList         | 平台属性值列表         |          | false    | array                  | PlatformAttributeValueParam |
| &emsp;&emsp;&emsp;&emsp;attrId    | 属性id                 |          | false    | integer                |                             |
| &emsp;&emsp;&emsp;&emsp;id        | 平台属性值id           |          | false    | integer                |                             |
| &emsp;&emsp;&emsp;&emsp;valueName | 属性值名称             |          | false    | string                 |                             |
| &emsp;&emsp;categoryId            | 分类id                 |          | false    | integer(int64)         |                             |
| &emsp;&emsp;categoryLevel         | 分类层级               |          | false    | integer(int32)         |                             |
| &emsp;&emsp;id                    | 平台属性id             |          | false    | integer(int64)         |                             |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 保存成功     | 全局统一返回结果 |
| 201    | 保存失败     |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 获取平台属性值列表接口


**接口地址**:`/admin/product/getAttrValueList/{attrId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明   | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | ---------- | -------- | -------- | -------------- | ------ |
| attrId   | 平台属性Id | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                                       | schema                                            |
| ------ | ------------------------------------------ | ------------------------------------------------- |
| 200    | 获取平台属性值列表成功, 返回平台属性值列表 | 全局统一返回结果«List«PlatformAttributeValueDTO»» |
| 201    | 获取数据失败                               |                                                   |
| 401    | Unauthorized                               |                                                   |
| 403    | Forbidden                                  |                                                   |
| 404    | Not Found                                  |                                                   |


**响应参数**:


| 参数名称              | 参数说明     | 类型           | schema                    |
| --------------------- | ------------ | -------------- | ------------------------- |
| code                  | 返回码       | integer(int32) | integer(int32)            |
| data                  | 返回数据     | array          | PlatformAttributeValueDTO |
| &emsp;&emsp;attrId    | 属性id       | integer(int64) |                           |
| &emsp;&emsp;id        | 平台属性值id | integer(int64) |                           |
| &emsp;&emsp;valueName | 属性值名称   | string         |                           |
| message               | 返回消息     | string         |                           |
| ok                    |              | boolean        |                           |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"attrId": 0,
			"id": 0,
			"valueName": ""
		}
	],
	"message": "",
	"ok": true
}
```

## 图片文件上传接口


**接口地址**:`/admin/product/fileUpload`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json,application/octet-stream`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明                            | schema           |
| ------ | ------------------------------- | ---------------- |
| 200    | 文件上传成功，返回访问文件的url | 全局统一返回结果 |
| 201    | 文件上传失败                    |                  |
| 401    | Unauthorized                    |                  |
| 403    | Forbidden                       |                  |
| 404    | Not Found                       |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```jjson
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 获取品牌列表


**接口地址**:`/admin/product/baseTrademark/{page}/{limit}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明       | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | -------------- | -------- | -------- | -------------- | ------ |
| limit    | 一页显示的条数 | path     | true     | integer(int64) |        |
| page     | 当前页数       | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                       | schema           |
| ------ | -------------------------- | ---------------- |
| 200    | 获取成功，返回该页品牌数据 | 全局统一返回结果 |
| 201    | 取数据失败                 |                  |
| 401    | Unauthorized               |                  |
| 403    | Forbidden                  |                  |
| 404    | Not Found                  |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"records": [
			{
				"id": 0,
				"logoUrl": "",
				"tmName": ""
			}
		],
		"total": 0
	},
	"message": "",
	"ok": true
}
```

## 新增Trademark


**接口地址**:`/admin/product/baseTrademark/save`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "logoUrl": "",
  "tmName": ""
}
```


**请求参数**:


| 参数名称            | 参数说明           | 请求类型 | 是否必须 | 数据类型       | schema         |
| ------------------- | ------------------ | -------- | -------- | -------------- | -------------- |
| trademarkParam      | TrademarkParam     | body     | true     | TrademarkParam | TrademarkParam |
| &emsp;&emsp;id      | 品牌id             |          | false    | integer(int64) |                |
| &emsp;&emsp;logoUrl | 品牌logo的图片路径 |          | false    | string         |                |
| &emsp;&emsp;tmName  | 属性值             |          | false    | string         |                |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 添加品牌成功 | 全局统一返回结果 |
| 201    | 添加品牌失败 |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 删除Trademark


**接口地址**:`/admin/product/baseTrademark/remove/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | -------- | -------- | -------- | -------------- | ------ |
| id       | 品牌Id   | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 删除品牌成功 | 全局统一返回结果 |
| 201    | 删除品牌失败 |                  |
| 204    | No Content   |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 获取Trademark


**接口地址**:`/admin/product/baseTrademark/get/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | -------- | -------- | -------- | -------------- | ------ |
| id       | 品牌Id   | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                       | schema           |
| ------ | -------------------------- | ---------------- |
| 200    | 获取品牌成功，返回品牌信息 | 全局统一返回结果 |
| 201    | 获取品牌失败               |                  |
| 401    | Unauthorized               |                  |
| 403    | Forbidden                  |                  |
| 404    | Not Found                  |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"id": 0,
		"logoUrl": "",
		"tmName": ""
	},
	"message": "",
	"ok": true
}
```

## 修改Trademark


**接口地址**:`/admin/product/baseTrademark/update`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "logoUrl": "",
  "tmName": ""
}
```


**请求参数**:


| 参数名称            | 参数说明           | 请求类型 | 是否必须 | 数据类型       | schema         |
| ------------------- | ------------------ | -------- | -------- | -------------- | -------------- |
| trademarkParam      | TrademarkParam     | body     | true     | TrademarkParam | TrademarkParam |
| &emsp;&emsp;id      | 品牌id             |          | false    | integer(int64) |                |
| &emsp;&emsp;logoUrl | 品牌logo的图片路径 |          | false    | string         |                |
| &emsp;&emsp;tmName  | 属性值             |          | false    | string         |                |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 更新品牌成功 | 全局统一返回结果 |
| 201    | 更新品牌失败 |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 添加类目品牌


**接口地址**:`/admin/product/baseCategoryTrademark/save`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "category3Id": 0,
  "trademarkIdList": []
}
```


**请求参数**:


| 参数名称                    | 参数说明               | 请求类型 | 是否必须 | 数据类型               | schema                 |
| --------------------------- | ---------------------- | -------- | -------- | ---------------------- | ---------------------- |
| categoryTrademarkParam      | CategoryTrademarkParam | body     | true     | CategoryTrademarkParam | CategoryTrademarkParam |
| &emsp;&emsp;category3Id     | 三级分类编号           |          | false    | integer(int64)         |                        |
| &emsp;&emsp;trademarkIdList | 品牌id                 |          | false    | array                  | integer                |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 添加成功     | 全局统一返回结果 |
| 201    | 添加失败     |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 查询三级类目下的品牌集合


**接口地址**:`/admin/product/baseCategoryTrademark/findTrademarkList/{thirdLevelCategoryId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称             | 参数说明   | 请求类型 | 是否必须 | 数据类型       | schema |
| -------------------- | ---------- | -------- | -------- | -------------- | ------ |
| thirdLevelCategoryId | 三级类目Id | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                               | schema           |
| ------ | ---------------------------------- | ---------------- |
| 200    | 查询成功，返回当前类目下的品牌集合 | 全局统一返回结果 |
| 201    | 查询失败                           |                  |
| 401    | Unauthorized                       |                  |
| 403    | Forbidden                          |                  |
| 404    | Not Found                          |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"id": 0,
			"logoUrl": "",
			"tmName": ""
		}
	],
	"message": "",
	"ok": true
}
```

## 查询三级类目下的未关联的品牌集合


**接口地址**:`/admin/product/baseCategoryTrademark/findCurrentTrademarkList/{thirdLevelCategoryId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称             | 参数说明   | 请求类型 | 是否必须 | 数据类型       | schema |
| -------------------- | ---------- | -------- | -------- | -------------- | ------ |
| thirdLevelCategoryId | 三级类目Id | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                                       | schema           |
| ------ | ------------------------------------------ | ---------------- |
| 200    | 查询成功，返回未关联到当前类目下的品牌集合 | 全局统一返回结果 |
| 201    | 查询失败                                   |                  |
| 401    | Unauthorized                               |                  |
| 403    | Forbidden                                  |                  |
| 404    | Not Found                                  |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"id": 0,
			"logoUrl": "",
			"tmName": ""
		}
	],
	"message": "",
	"ok": true
}
```

## 删除目录品牌关联


**接口地址**:`/admin/product/baseCategoryTrademark/remove/{thirdLevelCategoryId}/{trademarkId}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称             | 参数说明   | 请求类型 | 是否必须 | 数据类型       | schema |
| -------------------- | ---------- | -------- | -------- | -------------- | ------ |
| thirdLevelCategoryId | 三级类目Id | path     | true     | integer(int64) |        |
| trademarkId          | 品牌Id     | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 删除成功     | 全局统一返回结果 |
| 201    | 删除失败     |                  |
| 204    | No Content   |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 在添加SPU页面中，查询可以添加的销售属性


**接口地址**:`/admin/product/baseSaleAttrList`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明                       | schema           |
| ------ | -------------------------- | ---------------- |
| 200    | 查询成功，返回销售属性集合 | 全局统一返回结果 |
| 201    | 查询失败                   |                  |
| 401    | Unauthorized               |                  |
| 403    | Forbidden                  |                  |
| 404    | Not Found                  |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"id": 0,
			"name": ""
		}
	],
	"message": "",
	"ok": true
}
```

## SPU列表查询


**接口地址**:`/admin/product/{page}/{size}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称    | 参数说明       | 请求类型 | 是否必须 | 数据类型       | schema |
| ----------- | -------------- | -------- | -------- | -------------- | ------ |
| category3Id | 三级类目Id     | query    | true     | integer(int64) |        |
| page        | 当前查询的页数 | path     | true     | integer(int64) |        |
| size        | 每页条数       | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                            | schema           |
| ------ | ------------------------------- | ---------------- |
| 200    | 文件上传成功，返回访问文件的url | 全局统一返回结果 |
| 201    | 文件上传失败                    |                  |
| 401    | Unauthorized                    |                  |
| 403    | Forbidden                       |                  |
| 404    | Not Found                       |                  |

**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"records": [
			{
				"description": "",
				"id": 0,
				"spuName": "",
				"thirdLevelCategoryId": 0,
				"tmId": 0
			}
		],
		"total": 0
	},
	"message": "",
	"ok": true
}
```

## 保存SPU


**接口地址**:`/admin/product/saveSpuInfo`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "category3Id": 0,
  "description": "",
  "id": 0,
  "spuImageList": [
    {
      "id": 0,
      "imgName": "",
      "imgUrl": "",
      "isDefault": "",
      "skuId": 0,
      "spuImgId": 0
    }
  ],
  "spuName": "",
  "spuPosterList": [
    {
      "id": 0,
      "imgName": "",
      "imgUrl": "",
      "spuId": 0
    }
  ],
  "spuSaleAttrList": [
    {
      "baseSaleAttrId": 0,
      "id": 0,
      "saleAttrName": "",
      "spuId": 0,
      "spuSaleAttrValueList": [
        {
          "baseSaleAttrId": 0,
          "id": 0,
          "saleAttrValueName": "",
          "spuId": 0
        }
      ]
    }
  ],
  "tmId": 0
}
```


**请求参数**:


| 参数名称                                              | 参数说明            | 请求类型 | 是否必须 | 数据类型       | schema                     |
| ----------------------------------------------------- | ------------------- | -------- | -------- | -------------- | -------------------------- |
| spuInfoParam                                          | SpuInfoParam        | body     | true     | SpuInfoParam   | SpuInfoParam               |
| &emsp;&emsp;category3Id                               | 三级分类id          |          | false    | integer(int64) |                            |
| &emsp;&emsp;description                               | 商品描述(后台简述） |          | false    | string         |                            |
| &emsp;&emsp;id                                        | spu商品id           |          | false    | integer(int64) |                            |
| &emsp;&emsp;spuImageList                              |                     |          | false    | array          | SpuImageParam              |
| &emsp;&emsp;&emsp;&emsp;id                            | id                  |          | false    | integer        |                            |
| &emsp;&emsp;&emsp;&emsp;imgName                       | 图片名称（冗余）    |          | false    | string         |                            |
| &emsp;&emsp;&emsp;&emsp;imgUrl                        | 图片路径(冗余)      |          | false    | string         |                            |
| &emsp;&emsp;&emsp;&emsp;isDefault                     | 是否默认            |          | false    | string         |                            |
| &emsp;&emsp;&emsp;&emsp;skuId                         | 商品id              |          | false    | integer        |                            |
| &emsp;&emsp;&emsp;&emsp;spuImgId                      | 商品图片id          |          | false    | integer        |                            |
| &emsp;&emsp;spuName                                   |                     |          | false    | string         |                            |
| &emsp;&emsp;spuPosterList                             |                     |          | false    | array          | SpuPosterParam             |
| &emsp;&emsp;&emsp;&emsp;id                            | id                  |          | false    | integer        |                            |
| &emsp;&emsp;&emsp;&emsp;imgName                       | 文件名称            |          | false    | string         |                            |
| &emsp;&emsp;&emsp;&emsp;imgUrl                        | 文件路径            |          | false    | string         |                            |
| &emsp;&emsp;&emsp;&emsp;spuId                         | 商品id              |          | false    | integer        |                            |
| &emsp;&emsp;spuSaleAttrList                           | 销售属性集合        |          | false    | array          | SpuSaleAttributeInfoParam  |
| &emsp;&emsp;&emsp;&emsp;baseSaleAttrId                | 销售属性id          |          | false    | integer        |                            |
| &emsp;&emsp;&emsp;&emsp;id                            |                     |          | false    | integer        |                            |
| &emsp;&emsp;&emsp;&emsp;saleAttrName                  | 销售属性名称(冗余)  |          | false    | string         |                            |
| &emsp;&emsp;&emsp;&emsp;spuId                         | 商品id              |          | false    | integer        |                            |
| &emsp;&emsp;&emsp;&emsp;spuSaleAttrValueList          |                     |          | false    | array          | SpuSaleAttributeValueParam |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;baseSaleAttrId    | spu销售属性id       |          | false    | integer        |                            |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id                | id                  |          | false    | integer        |                            |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;saleAttrValueName | spu销售属性值名称   |          | false    | string         |                            |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;spuId             | spu_id(冗余)        |          | false    | integer        |                            |
| &emsp;&emsp;tmId                                      | 品牌id              |          | false    | integer(int64) |                            |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 保存成功     | 全局统一返回结果 |
| 201    | 保存失败     |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 查询SPU中包含的所有SKU商品图片


**接口地址**:`/admin/product/spuImageList/{spuId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | -------- | -------- | -------- | -------------- | ------ |
| spuId    | spu的Id  | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                                         | schema                              |
| ------ | -------------------------------------------- | ----------------------------------- |
| 200    | 获取成功，返回SPU中包含的所有SKU商品图片信息 | 全局统一返回结果«List«SpuImageDTO»» |
| 201    | 获取失败                                     |                                     |
| 401    | Unauthorized                                 |                                     |
| 403    | Forbidden                                    |                                     |
| 404    | Not Found                                    |                                     |


**响应参数**:


| 参数名称              | 参数说明         | 类型           | schema         |
| --------------------- | ---------------- | -------------- | -------------- |
| code                  | 返回码           | integer(int32) | integer(int32) |
| data                  | 返回数据         | array          | SpuImageDTO    |
| &emsp;&emsp;id        | id               | integer(int64) |                |
| &emsp;&emsp;imgName   | 图片名称（冗余） | string         |                |
| &emsp;&emsp;imgUrl    | 图片路径(冗余)   | string         |                |
| &emsp;&emsp;isDefault | 是否默认         | string         |                |
| &emsp;&emsp;skuId     | 商品id           | integer(int64) |                |
| &emsp;&emsp;spuImgId  | 商品图片id       | integer(int64) |                |
| message               | 返回消息         | string         |                |
| ok                    |                  | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"id": 0,
			"imgName": "",
			"imgUrl": "",
			"isDefault": "",
			"skuId": 0,
			"spuImgId": 0
		}
	],
	"message": "",
	"ok": true
}
```

## 查询SPU中包含的所有销售属性及销售属性值集合


**接口地址**:`/admin/product/spuSaleAttrList/{spuId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | -------- | -------- | -------- | -------------- | ------ |
| spuId    | spu的Id  | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                                         | schema                           |
| ------ | -------------------------------------------- | -------------------------------- |
| 200    | 获取成功，返回SPU中包含的所有SKU商品图片信息 | 全局统一返回结果«List«销售属性»» |
| 201    | 获取失败                                     |                                  |
| 401    | Unauthorized                                 |                                  |
| 403    | Forbidden                                    |                                  |
| 404    | Not Found                                    |                                  |


**响应参数**:


| 参数名称                                     | 参数说明                      | 类型           | schema         |
| -------------------------------------------- | ----------------------------- | -------------- | -------------- |
| code                                         | 返回码                        | integer(int32) | integer(int32) |
| data                                         | 返回数据                      | array          | 销售属性       |
| &emsp;&emsp;id                               |                               | integer(int64) |                |
| &emsp;&emsp;saleAttrId                       | 销售属性id                    | integer(int64) |                |
| &emsp;&emsp;saleAttrName                     | 销售属性名称                  | string         |                |
| &emsp;&emsp;spuId                            | 商品Spu id                    | integer(int64) |                |
| &emsp;&emsp;spuSaleAttrValueList             | 销售属性对应的销售属性值集合  | array          | 销售属性值     |
| &emsp;&emsp;&emsp;&emsp;id                   | id                            | integer        |                |
| &emsp;&emsp;&emsp;&emsp;isChecked            | 是否是当前sku商品的销售属性值 | string         |                |
| &emsp;&emsp;&emsp;&emsp;spuId                | 商品id                        | integer        |                |
| &emsp;&emsp;&emsp;&emsp;spuSaleAttrId        | 销售属性id                    | integer        |                |
| &emsp;&emsp;&emsp;&emsp;spuSaleAttrValueName | 销售属性值名称                | string         |                |
| message                                      | 返回消息                      | string         |                |
| ok                                           |                               | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"id": 0,
			"saleAttrId": 0,
			"saleAttrName": "",
			"spuId": 0,
			"spuSaleAttrValueList": [
				{
					"id": 0,
					"isChecked": "",
					"spuId": 0,
					"spuSaleAttrId": 0,
					"spuSaleAttrValueName": ""
				}
			]
		}
	],
	"message": "",
	"ok": true
}
```

## 保存SKU信息接口


**接口地址**:`/admin/product/saveSkuInfo`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "category3Id": 0,
  "id": 0,
  "isSale": 0,
  "price": 0,
  "skuAttrValueList": [
    {
      "attrId": 0,
      "id": 0,
      "skuId": 0,
      "valueId": 0
    }
  ],
  "skuDefaultImg": "",
  "skuDesc": "",
  "skuImageList": [
    {
      "id": 0,
      "imgName": "",
      "imgUrl": "",
      "isDefault": "",
      "skuId": 0,
      "spuImgId": 0
    }
  ],
  "skuName": "",
  "skuSaleAttrValueList": [
    {
      "id": 0,
      "saleAttrValueId": 0,
      "skuId": 0,
      "spuId": 0
    }
  ],
  "spuId": 0,
  "tmId": 0,
  "weight": ""
}
```


**请求参数**:


| 参数名称                                | 参数说明                | 请求类型 | 是否必须 | 数据类型           | schema                         |
| --------------------------------------- | ----------------------- | -------- | -------- | ------------------ | ------------------------------ |
| skuInfoParam                            | SkuInfoParam            | body     | true     | SkuInfoParam       | SkuInfoParam                   |
| &emsp;&emsp;category3Id                 | 三级分类id（冗余)       |          | false    | integer(int64)     |                                |
| &emsp;&emsp;id                          | id                      |          | false    | integer(int64)     |                                |
| &emsp;&emsp;isSale                      | 是否销售（1：是 0：否） |          | false    | integer(int32)     |                                |
| &emsp;&emsp;price                       | 价格                    |          | false    | number(bigdecimal) |                                |
| &emsp;&emsp;skuAttrValueList            |                         |          | false    | array              | SkuPlatformAttributeValueParam |
| &emsp;&emsp;&emsp;&emsp;attrId          | 属性id                  |          | false    | integer            |                                |
| &emsp;&emsp;&emsp;&emsp;id              | id                      |          | false    | integer            |                                |
| &emsp;&emsp;&emsp;&emsp;skuId           | skuid                   |          | false    | integer            |                                |
| &emsp;&emsp;&emsp;&emsp;valueId         | 属性值id                |          | false    | integer            |                                |
| &emsp;&emsp;skuDefaultImg               | 默认显示图片(冗余)      |          | false    | string             |                                |
| &emsp;&emsp;skuDesc                     | 商品规格描述            |          | false    | string             |                                |
| &emsp;&emsp;skuImageList                |                         |          | false    | array              | SkuImageParam                  |
| &emsp;&emsp;&emsp;&emsp;id              | id                      |          | false    | integer            |                                |
| &emsp;&emsp;&emsp;&emsp;imgName         | 图片名称（冗余）        |          | false    | string             |                                |
| &emsp;&emsp;&emsp;&emsp;imgUrl          | 图片路径(冗余)          |          | false    | string             |                                |
| &emsp;&emsp;&emsp;&emsp;isDefault       | 是否默认                |          | false    | string             |                                |
| &emsp;&emsp;&emsp;&emsp;skuId           | 商品id                  |          | false    | integer            |                                |
| &emsp;&emsp;&emsp;&emsp;spuImgId        | 商品图片id              |          | false    | integer            |                                |
| &emsp;&emsp;skuName                     | sku名称                 |          | false    | string             |                                |
| &emsp;&emsp;skuSaleAttrValueList        |                         |          | false    | array              | SkuSaleAttributeValueParam     |
| &emsp;&emsp;&emsp;&emsp;id              | id                      |          | false    | integer            |                                |
| &emsp;&emsp;&emsp;&emsp;saleAttrValueId | 销售属性值id            |          | false    | integer            |                                |
| &emsp;&emsp;&emsp;&emsp;skuId           | 库存单元id              |          | false    | integer            |                                |
| &emsp;&emsp;&emsp;&emsp;spuId           | spu_id(冗余)            |          | false    | integer            |                                |
| &emsp;&emsp;spuId                       | 商品id                  |          | false    | integer(int64)     |                                |
| &emsp;&emsp;tmId                        | 品牌(冗余)              |          | false    | integer(int64)     |                                |
| &emsp;&emsp;weight                      | 重量                    |          | false    | string             |                                |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 保存成功     | 全局统一返回结果 |
| 201    | 保存失败     |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 分页查询SKU列表接口


**接口地址**:`/admin/product/list/{page}/{limit}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | -------- | -------- | -------- | -------------- | ------ |
| limit    | 每页条数 | path     | true     | integer(int64) |        |
| page     | 当前页数 | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                      | schema           |
| ------ | ------------------------- | ---------------- |
| 200    | 查询成功，返回该页SKU信息 | 全局统一返回结果 |
| 201    | 查询失败                  |                  |
| 401    | Unauthorized              |                  |
| 403    | Forbidden                 |                  |
| 404    | Not Found                 |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"records": [
			{
				"id": 0,
				"isSale": 0,
				"price": 0,
				"skuDefaultImg": "",
				"spuId": 0,
				"thirdLevelCategoryId": 0,
				"tmId": 0,
				"weight": ""
			}
		],
		"total": 0
	},
	"message": "",
	"ok": true
}
```

## 商品上架接口


**接口地址**:`/admin/product/onSale/{skuId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明  | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | --------- | -------- | -------- | -------------- | ------ |
| skuId    | sku商品Id | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 上架成功     | 全局统一返回结果 |
| 201    | 上架失败     |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 商品下架接口


**接口地址**:`/admin/product/cancelSale/{skuId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明  | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | --------- | -------- | -------- | -------------- | ------ |
| skuId    | sku商品Id | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 下架成功     | 全局统一返回结果 |
| 201    | 下架失败     |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 获取指定sku商品的详情信息

**接口地址**:`/goods/{skuId}`


**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明    | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | ----------- | -------- | -------- | -------------- | ------ |
| skuId    | 商品的skuId | path     | true     | integer(int64) |        |

**响应状态**:


| 状态码 | 说明                   | schema                         |
| ------ | ---------------------- | ------------------------------ |
| 200    | 请求成功, 商品详情数据 | 全局统一返回结果«商品详情数据» |
| 201    | 获取详情数据失败       |                                |
| 401    | Unauthorized           |                                |
| 403    | Forbidden              |                                |
| 404    | Not Found              |                                |
| 500    | 服务器异常             |                                |


**响应参数**:


| 参数名称                                                 | 参数说明                | 类型                 | schema                       |
| -------------------------------------------------------- | ----------------------- | -------------------- | ---------------------------- |
| code                                                     | 返回码                  | integer(int32)       | integer(int32)               |
| data                                                     | 返回数据                | ProductDetailDTO     | ProductDetailDTO             |
| &emsp;&emsp;categoryHierarchy                            |                         | CategoryHierarchyDTO | CategoryHierarchyDTO         |
| &emsp;&emsp;&emsp;&emsp;firstLevelCategoryId             | 一级分类编号            | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;firstLevelCategoryName           | 一级分类名称            | string               |                              |
| &emsp;&emsp;&emsp;&emsp;id                               | id                      | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;secondLevelCategoryId            | 二级分类编号            | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;secondLevelCategoryName          | 二级分类名称            | string               |                              |
| &emsp;&emsp;&emsp;&emsp;thirdLevelCategoryId             | 三级分类编号            | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;thirdLevelCategoryName           | 三级分类名称            | string               |                              |
| &emsp;&emsp;price                                        |                         | number(bigdecimal)   |                              |
| &emsp;&emsp;skuAttrList                                  |                         | array                | SkuSpecification             |
| &emsp;&emsp;&emsp;&emsp;attrName                         |                         | string               |                              |
| &emsp;&emsp;&emsp;&emsp;attrValue                        |                         | string               |                              |
| &emsp;&emsp;skuInfo                                      |                         | SkuInfoDTO           | SkuInfoDTO                   |
| &emsp;&emsp;&emsp;&emsp;id                               | id                      | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;isSale                           | 是否销售（1：是 0：否） | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;price                            | 价格                    | number               |                              |
| &emsp;&emsp;&emsp;&emsp;skuDefaultImg                    | 默认显示图片(冗余)      | string               |                              |
| &emsp;&emsp;&emsp;&emsp;skuDesc                          | 商品规格描述            | string               |                              |
| &emsp;&emsp;&emsp;&emsp;skuImageList                     | sku商品图片列表         | array                | SkuImageDTO                  |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id                   | id                      | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;imgName              | 图片名称（冗余）        | string               |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;imgUrl               | 图片路径(冗余)          | string               |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;isDefault            | 是否默认                | string               |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;skuId                | 商品id                  | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;spuImgId             | 商品图片id              | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;skuName                          | sku名称                 | string               |                              |
| &emsp;&emsp;&emsp;&emsp;skuPlatformAttributeValueList    | sku商品平台属性值集合   | array                | SkuPlatformAttributeValueDTO |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;attrId               | 属性id（冗余)           | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id                   | id                      | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;skuId                | skuid                   | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;valueId              | 属性值id                | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;skuSaleAttributeValueList        | sku商品销售属性值集合   | array                | SkuSaleAttributeValueDTO     |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id                   | id                      | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;saleAttrValueId      | 销售属性值id            | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;skuId                | 库存单元id              | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;spuId                | spu_id(冗余)            | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;spuId                            | 商品id                  | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;thirdLevelCategoryId             | 三级分类id（冗余)       | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;tmId                             | 品牌(冗余)              | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;weight                           | 重量                    | string               |                              |
| &emsp;&emsp;spuPosterList                                |                         | array                | SpuPosterDTO                 |
| &emsp;&emsp;&emsp;&emsp;id                               | id                      | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;imgName                          | 文件名称                | string               |                              |
| &emsp;&emsp;&emsp;&emsp;imgUrl                           | 文件路径                | string               |                              |
| &emsp;&emsp;&emsp;&emsp;spuId                            | 商品id                  | integer              |                              |
| &emsp;&emsp;spuSaleAttrList                              |                         | array                | SpuSaleAttributeInfoDTO      |
| &emsp;&emsp;&emsp;&emsp;id                               |                         | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;saleAttrId                       | 销售属性id              | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;saleAttrName                     | 销售属性名称(冗余)      | string               |                              |
| &emsp;&emsp;&emsp;&emsp;spuId                            | 商品id                  | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;spuSaleAttrValueList             |                         | array                | SpuSaleAttributeValueDTO     |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id                   | id                      | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;isChecked            | 是否关联到了sku的属性值 | string               |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;spuId                | 商品id                  | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;spuSaleAttrId        | 销售属性id              | integer              |                              |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;spuSaleAttrValueName | 销售属性值名称          | string               |                              |
| &emsp;&emsp;valuesSkuJson                                |                         | string               |                              |
| message                                                  | 返回消息                | string               |                              |
| ok                                                       |                         | boolean              |                              |

**响应示例**:

```
{
	"code": 0,
	"data": {
		"categoryHierarchy": {
			"firstLevelCategoryId": 0,
			"firstLevelCategoryName": "",
			"id": 0,
			"secondLevelCategoryId": 0,
			"secondLevelCategoryName": "",
			"thirdLevelCategoryId": 0,
			"thirdLevelCategoryName": ""
		},
		"price": 0,
		"skuAttrList": [
			{
				"attrName": "",
				"attrValue": ""
			}
		],
		"skuInfo": {
			"id": 0,
			"isSale": 0,
			"price": 0,
			"skuDefaultImg": "",
			"skuDesc": "",
			"skuImageList": [
				{
					"id": 0,
					"imgName": "",
					"imgUrl": "",
					"isDefault": "",
					"skuId": 0,
					"spuImgId": 0
				}
			],
			"skuName": "",
			"skuPlatformAttributeValueList": [
				{
					"attrId": 0,
					"id": 0,
					"skuId": 0,
					"valueId": 0
				}
			],
			"skuSaleAttributeValueList": [
				{
					"id": 0,
					"saleAttrValueId": 0,
					"skuId": 0,
					"spuId": 0
				}
			],
			"spuId": 0,
			"thirdLevelCategoryId": 0,
			"tmId": 0,
			"weight": ""
		},
		"spuPosterList": [
			{
				"id": 0,
				"imgName": "",
				"imgUrl": "",
				"spuId": 0
			}
		],
		"spuSaleAttrList": [
			{
				"id": 0,
				"saleAttrId": 0,
				"saleAttrName": "",
				"spuId": 0,
				"spuSaleAttrValueList": [
					{
						"id": 0,
						"isChecked": "",
						"spuId": 0,
						"spuSaleAttrId": 0,
						"spuSaleAttrValueName": ""
					}
				]
			}
		],
		"valuesSkuJson": ""
	},
	"message": "",
	"ok": true
}
```



## 返回首页的三级商品类目


**接口地址**:`/index`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:


**请求参数**:


暂无

**响应状态**:


| 状态码 | 说明                       | schema                                            |
| ------ | -------------------------- | ------------------------------------------------- |
| 200    | 请求成功, 首页三级类目数据 | 全局统一返回结果«List«FirstLevelCategoryNodeDTO»» |
| 201    | 获取首页三级类目失败       |                                                   |
| 401    | Unauthorized               |                                                   |
| 403    | Forbidden                  |                                                   |
| 404    | Not Found                  |                                                   |
| 500    | 服务器异常                 |                                                   |


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- |
|code|返回码|integer(int32)|integer(int32)|
|data|返回数据|array|FirstLevelCategoryNodeDTO|
|&emsp;&emsp;categoryChild|一级目录所包含的二级目录|array|SecondLevelCategoryNodeDTO|
|&emsp;&emsp;&emsp;&emsp;categoryChild|二级目录所包含的三级目录|array|ThirdLevelCategoryNodeDTO|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;categoryId||integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;categoryName||string||
|&emsp;&emsp;&emsp;&emsp;categoryId|二级目录id|integer||
|&emsp;&emsp;&emsp;&emsp;categoryName|二级目录名称|string||
|&emsp;&emsp;categoryId|一级目录的id|integer(int64)||
|&emsp;&emsp;categoryName|一级目录的名称|string||
|&emsp;&emsp;index|一级目录的位序，给遍历顺序即可|integer(int32)||
|message|返回消息|string||
|ok||boolean||


**响应示例**:
```javascript
{
	"code": 0,
	"data": [
		{
			"categoryChild": [
				{
					"categoryChild": [
						{
							"categoryId": 0,
							"categoryName": ""
						}
					],
					"categoryId": 0,
					"categoryName": ""
				}
			],
			"categoryId": 0,
			"categoryName": "",
			"index": 0
		}
	],
	"message": "",
	"ok": true
}
```

## 搜索接口


**接口地址**:`/list`


**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:


**请求参数**:


| 参数名称              | 参数说明                                            | 请求类型 | 是否必须 | 数据类型       | schema |
| --------------------- | --------------------------------------------------- | -------- | -------- | -------------- | ------ |
| firstLevelCategoryId  | 搜索商品一级类目                                    | query    | false    | integer(int64) |        |
| keyword               | 搜索关键词                                          | query    | false    | string         |        |
| order                 | 排序参数 1:hotScore 或者 2:price，默认1:hotScore    | query    | false    | string         |        |
| pageNo                | 查询的页数                                          | query    | false    | integer(int32) |        |
| pageSize              | 每页包含的商品数                                    | query    | false    | integer(int32) |        |
| props                 | 平台属性条件 props=23:4G:运行内存                   | query    | false    | array          | string |
| secondLevelCategoryId | 搜索商品二级类目                                    | query    | false    | integer(int64) |        |
| thirdLevelCategoryId  | 搜索商品三级类目                                    | query    | false    | integer(int64) |        |
| trademark             | 品牌条件 trademark=2:华为 2是品牌id，华为是品牌名称 | query    | false    | string         |        |

**响应状态**:


| 状态码 | 说明                       | schema                     |
| ------ | -------------------------- | -------------------------- |
| 200    | 搜索请求成功，返回搜索结果 | 全局统一返回结果«搜索结果» |
| 201    | 搜索失败                   |                            |
| 401    | Unauthorized               |                            |
| 403    | Forbidden                  |                            |
| 404    | Not Found                  |                            |
| 500    | 服务器异常                 |                            |


**响应参数**:


| 参数名称                                        | 参数说明               | 类型           | schema         |
| ----------------------------------------------- | ---------------------- | -------------- | -------------- |
| code                                            | 返回码                 | integer(int32) | integer(int32) |
| data                                            | 返回数据               | 搜索结果       | 搜索结果       |
| &emsp;&emsp;attrsList                           | 平台属性列表           | array          | 平台属性       |
| &emsp;&emsp;&emsp;&emsp;attrId                  | 平台属性Id             | integer        |                |
| &emsp;&emsp;&emsp;&emsp;attrName                | 平台属性名称           | string         |                |
| &emsp;&emsp;&emsp;&emsp;attrValueList           | 平台属性值集合         | array          | string         |
| &emsp;&emsp;goodsList                           | 商品列表               | array          | 搜索商品       |
| &emsp;&emsp;&emsp;&emsp;attrs                   | 商品平台属性值集合     | array          | 商品平台属性   |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;attrId      | 平台属性Id             | integer        |                |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;attrName    | 平台名称               | string         |                |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;attrValue   | 平台属性值             | string         |                |
| &emsp;&emsp;&emsp;&emsp;defaultImg              | 商品默认图片url        | string         |                |
| &emsp;&emsp;&emsp;&emsp;firstLevelCategoryId    | 商品一级类目Id         | integer        |                |
| &emsp;&emsp;&emsp;&emsp;firstLevelCategoryName  | 商品一级类目名称       | string         |                |
| &emsp;&emsp;&emsp;&emsp;hotScore                | 商品热度               | integer        |                |
| &emsp;&emsp;&emsp;&emsp;id                      | 商品skuId              | integer        |                |
| &emsp;&emsp;&emsp;&emsp;price                   | 价格                   | number         |                |
| &emsp;&emsp;&emsp;&emsp;secondLevelCategoryId   | 商品二级类目Id         | integer        |                |
| &emsp;&emsp;&emsp;&emsp;secondLevelCategoryName | 商品二级类目名称       | string         |                |
| &emsp;&emsp;&emsp;&emsp;thirdLevelCategoryId    | 商品三级级类目Id       | integer        |                |
| &emsp;&emsp;&emsp;&emsp;thirdLevelCategoryName  | 商品三级类目名称       | string         |                |
| &emsp;&emsp;&emsp;&emsp;title                   | 商品名称               | string         |                |
| &emsp;&emsp;&emsp;&emsp;tmId                    | 商品品牌Id             | integer        |                |
| &emsp;&emsp;&emsp;&emsp;tmLogoUrl               | 商品品牌logourl        | string         |                |
| &emsp;&emsp;&emsp;&emsp;tmName                  | 商品品牌名称           | string         |                |
| &emsp;&emsp;pageNo                              | 当前页数               | integer(int32) |                |
| &emsp;&emsp;pageSize                            | 每页包含的商品数量     | integer(int32) |                |
| &emsp;&emsp;total                               | 总的满足条件的商品数量 | integer(int64) |                |
| &emsp;&emsp;totalPages                          | 总页数                 | integer(int64) |                |
| &emsp;&emsp;trademarkList                       | 品牌列表               | array          | 品牌信息       |
| &emsp;&emsp;&emsp;&emsp;tmId                    | 品牌Id                 | integer        |                |
| &emsp;&emsp;&emsp;&emsp;tmLogoUrl               | 品牌图片url            | string         |                |
| &emsp;&emsp;&emsp;&emsp;tmName                  | 品牌名称               | string         |                |
| message                                         | 返回消息               | string         |                |
| ok                                              |                        | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"attrsList": [
			{
				"attrId": 0,
				"attrName": "",
				"attrValueList": []
			}
		],
		"goodsList": [
			{
				"attrs": [
					{
						"attrId": 0,
						"attrName": "",
						"attrValue": ""
					}
				],
				"defaultImg": "",
				"firstLevelCategoryId": 0,
				"firstLevelCategoryName": "",
				"hotScore": 0,
				"id": 0,
				"price": 0,
				"secondLevelCategoryId": 0,
				"secondLevelCategoryName": "",
				"thirdLevelCategoryId": 0,
				"thirdLevelCategoryName": "",
				"title": "",
				"tmId": 0,
				"tmLogoUrl": "",
				"tmName": ""
			}
		],
		"pageNo": 0,
		"pageSize": 0,
		"total": 0,
		"totalPages": 0,
		"trademarkList": [
			{
				"tmId": 0,
				"tmLogoUrl": "",
				"tmName": ""
			}
		]
	},
	"message": "",
	"ok": true
}
```

## 用户登录接口

**接口地址**:`/user/login`


**请求方式**:`POST`

**请求数据类型**:`application/json`


**接口描述**:


**请求示例**:


```javascript
{
  "email": "",
  "headImg": "",
  "id": 0,
  "loginName": "",
  "name": "",
  "nickName": "",
  "passwd": "",
  "phoneNum": "",
  "userLevel": ""
}
```


**请求参数**:


| 参数名称              | 参数说明      | 请求类型 | 是否必须 | 数据类型       | schema        |
| --------------------- | ------------- | -------- | -------- | -------------- | ------------- |
| userInfoParam         | UserInfoParam | body     | true     | UserInfoParam  | UserInfoParam |
| &emsp;&emsp;email     | 邮箱          |          | false    | string         |               |
| &emsp;&emsp;headImg   | 头像          |          | false    | string         |               |
| &emsp;&emsp;id        |               |          | false    | integer(int64) |               |
| &emsp;&emsp;loginName | 用户名称      |          | false    | string         |               |
| &emsp;&emsp;name      | 用户姓名      |          | false    | string         |               |
| &emsp;&emsp;nickName  | 用户昵称      |          | false    | string         |               |
| &emsp;&emsp;passwd    | 用户密码      |          | false    | string         |               |
| &emsp;&emsp;phoneNum  | 手机号        |          | false    | string         |               |
| &emsp;&emsp;userLevel | 用户级别      |          | false    | string         |               |


**响应状态**:


| 状态码 | 说明                        | schema                         |
| ------ | --------------------------- | ------------------------------ |
| 200    | 登录成功, 返回用户名和token | 全局统一返回结果«登录成功响应» |
| 201    | Created                     |                                |
| 401    | Unauthorized                |                                |
| 403    | Forbidden                   |                                |
| 404    | Not Found                   |                                |
| 500    | 服务器异常                  |                                |
| 1001   | 用户名或密码错误            |                                |


**响应参数**:


| 参数名称             | 参数说明            | 类型           | schema         |
| -------------------- | ------------------- | -------------- | -------------- |
| code                 | 返回码              | integer(int32) | integer(int32) |
| data                 | 返回数据            | 登录成功响应   | 登录成功响应   |
| &emsp;&emsp;nickName | 用户名显示在首页    | string         |                |
| &emsp;&emsp;token    | 用户登录之后的token | string         |                |
| message              | 返回消息            | string         |                |
| ok                   |                     | boolean        |                |


**响应示例**:

```javascript
{
	"code": 0,
	"data": {
		"nickName": "",
		"token": ""
	},
	"message": "",
	"ok": true
}
```


## 用户退出登录

**接口地址**:`/user/logout`


**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:

**请求参数**:


暂无

**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 登出成功     | 全局统一返回结果 |
| 201    | 登出失败     |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |
| 500    | 服务器异常   |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |


**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 添加购物车

**接口地址**:`/cart/add/{skuId}/{skuNum}`


**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明         | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | ---------------- | -------- | -------- | -------------- | ------ |
| skuId    | 商品skuId        | path     | true     | integer(int64) |        |
| skuNum   | 购物车商品的数量 | path     | true     | integer(int32) |        |

**是否需要Cookie**：是

| cookie名称 | cookie值                         | 说明                                                         |
| ---------- | -------------------------------- | ------------------------------------------------------------ |
| userTempId | 66534120045340e58f95e1792a2a7795 | 用户添加购物车时若未登录，且没有临时id，前端 生成并保存到Cookie中 |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie                                 |

注意：以上两个cookie至少得有一个！

响应状态**:


| 状态码 | 说明           | schema           |
| ------ | -------------- | ---------------- |
| 200    | 添加购物车成功 | 全局统一返回结果 |
| 201    | 添加购物车失败 |                  |
| 401    | Unauthorized   |                  |
| 403    | Forbidden      |                  |
| 404    | Not Found      |                  |
| 500    | 服务器异常     |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 获取购物车商品列表

**接口地址**:`/cart`


**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:


**请求参数**:

暂无

**是否需要Cookie**：是

| cookie名称 | cookie值                         | 说明                                                         |
| ---------- | -------------------------------- | ------------------------------------------------------------ |
| userTempId | 66534120045340e58f95e1792a2a7795 | 用户添加购物车时若未登录，且没有临时id，前端 生成并保存到Cookie中 |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie                                 |

注意：以上两个cookie至少得有一个！


**响应状态**:


| 状态码 | 说明           | schema                              |
| ------ | -------------- | ----------------------------------- |
| 200    | 获取购物车列表 | 全局统一返回结果«List«CartInfoDTO»» |
| 201    | 订单提交失败   |                                     |
| 401    | Unauthorized   |                                     |
| 403    | Forbidden      |                                     |
| 404    | Not Found      |                                     |
| 500    | 服务器异常     |                                     |


**响应参数**:


| 参数名称               | 参数说明         | 类型               | schema         |
| ---------------------- | ---------------- | ------------------ | -------------- |
| code                   | 返回码           | integer(int32)     | integer(int32) |
| data                   | 返回数据         | array              | CartInfoDTO    |
| &emsp;&emsp;cartPrice  | 放入购物车时价格 | number(bigdecimal) |                |
| &emsp;&emsp;createTime | 创建时间         | string(date-time)  |                |
| &emsp;&emsp;imgUrl     | 图片文件         | string             |                |
| &emsp;&emsp;isChecked  | 是否选中         | integer(int32)     |                |
| &emsp;&emsp;skuId      | skuid            | integer(int64)     |                |
| &emsp;&emsp;skuName    | sku名称 (冗余)   | string             |                |
| &emsp;&emsp;skuNum     | 数量             | integer(int32)     |                |
| &emsp;&emsp;skuPrice   | 价格             | number(bigdecimal) |                |
| &emsp;&emsp;updateTime | 更新时间         | string(date-time)  |                |
| &emsp;&emsp;userId     | 用户id           | string             |                |
| message                | 返回消息         | string             |                |
| ok                     |                  | boolean            |                |

**响应示例**:

```json
{
	"code": 0,
	"data": [
		{
			"cartPrice": 0,
			"createTime": "",
			"imgUrl": "",
			"isChecked": 0,
			"skuId": 0,
			"skuName": "",
			"skuNum": 0,
			"skuPrice": 0,
			"updateTime": "",
			"userId": ""
		}
	],
	"message": "",
	"ok": true
}
```



## 修改购物车的选中状态

**接口地址**:`/cart/checkCart/{skuId}/{isChecked}`

**请求方式**:`PUT`

**请求数据类型**:`application/x-www-form-urlencoded`

**接口描述**:

**是否需要Cookie**：是

| cookie名称 | cookie值                         | 说明                                                         |
| ---------- | -------------------------------- | ------------------------------------------------------------ |
| userTempId | 66534120045340e58f95e1792a2a7795 | 用户添加购物车时若未登录，且没有临时id，前端 生成并保存到Cookie中 |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie                                 |

注意：以上两个cookie至少得有一个！

**请求参数**:


| 参数名称  | 参数说明               | 请求类型 | 是否必须 | 数据类型       | schema |
| --------- | ---------------------- | -------- | -------- | -------------- | ------ |
| isChecked | 是否选中1选中，0未选中 | path     | true     | integer(int32) |        |
| skuId     | 商品skuId              | path     | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明             | schema           |
| ------ | ---------------- | ---------------- |
| 200    | 修改选中状态成功 | 全局统一返回结果 |
| 201    | 修改选中状态失败 |                  |
| 401    | Unauthorized     |                  |
| 403    | Forbidden        |                  |
| 404    | Not Found        |                  |
| 500    | 服务器异常       |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |


**响应示例**:

```javascript
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```


## 删除指定的购物车商品

**接口地址**:`/cart/{skuId}`

**请求方式**:`DELETE`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | -------- | -------- | -------- | -------------- | ------ |
| skuId    | skuId    | path     | true     | integer(int64) |        |

**是否需要Cookie**：是

| cookie名称 | cookie值                         | 说明                                                         |
| ---------- | -------------------------------- | ------------------------------------------------------------ |
| userTempId | 66534120045340e58f95e1792a2a7795 | 用户添加购物车时若未登录，且没有临时id，前端 生成并保存到Cookie中 |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie                                 |

注意：以上两个cookie至少得有一个！

**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 删除成功     | 全局统一返回结果 |
| 201    | 删除失败     |                  |
| 204    | No Content   |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 500    | 服务器异常   |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |


**响应示例**:

```javascript
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```





## 删除选中的购物车商品

**接口地址**:`/cart/checked`

**请求方式**:`DELETE`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:


**请求参数**:

暂无

**是否需要Cookie**：是

| cookie名称 | cookie值                         | 说明                                                         |
| ---------- | -------------------------------- | ------------------------------------------------------------ |
| userTempId | 66534120045340e58f95e1792a2a7795 | 用户添加购物车时若未登录，且没有临时id，前端 生成并保存到Cookie中 |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie                                 |

注意：以上两个cookie至少得有一个！


**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 删除成功     | 全局统一返回结果 |
| 201    | 删除失败     |                  |
| 204    | No Content   |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 500    | 服务器异常   |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |


**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 获取结算页数据

**接口地址**:`/order/auth/trade`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:


**请求参数**:

暂无

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |


**响应状态**:


| 状态码 | 说明                     | schema                       |
| ------ | ------------------------ | ---------------------------- |
| 200    | 请求成功, 返回结算页数据 | 全局统一返回结果«结算页数据» |
| 201    | 获取结算数据失败         |                              |
| 401    | Unauthorized             |                              |
| 403    | Forbidden                |                              |
| 404    | Not Found                |                              |
| 500    | 服务器异常               |                              |


**响应参数**:


| 参数名称                            | 参数说明                     | 类型               | schema         |
| ----------------------------------- | ---------------------------- | ------------------ | -------------- |
| code                                | 返回码                       | integer(int32)     | integer(int32) |
| data                                | 返回数据                     | 结算页数据         | 结算页数据     |
| &emsp;&emsp;detailArrayList         | 订单明细列表                 | array              | 订单条目       |
| &emsp;&emsp;&emsp;&emsp;createTime  | 订单条目创建时间             | string             |                |
| &emsp;&emsp;&emsp;&emsp;id          | 订单条目Id                   | integer            |                |
| &emsp;&emsp;&emsp;&emsp;imgUrl      | 订单条目商品图片url          | string             |                |
| &emsp;&emsp;&emsp;&emsp;orderId     | 订单Id                       | integer            |                |
| &emsp;&emsp;&emsp;&emsp;orderPrice  | 订单条目商品价格             | number             |                |
| &emsp;&emsp;&emsp;&emsp;skuId       | 订单条目商品skuId            | integer            |                |
| &emsp;&emsp;&emsp;&emsp;skuName     | 订单条目商品名称             | string             |                |
| &emsp;&emsp;&emsp;&emsp;skuNum      | 订单条目商品价格             | integer            |                |
| &emsp;&emsp;&emsp;&emsp;updateTime  | 订单条目更新时间             | string             |                |
| &emsp;&emsp;totalAmount             | 总金额                       | number(bigdecimal) |                |
| &emsp;&emsp;totalNum                | 订单条目数量                 | integer(int32)     |                |
| &emsp;&emsp;tradeNo                 | 订单流水号，防止重复提交订单 | string             |                |
| &emsp;&emsp;userAddressList         | 用户地址列表                 | array              | 用户地址       |
| &emsp;&emsp;&emsp;&emsp;consignee   | 收件人                       | string             |                |
| &emsp;&emsp;&emsp;&emsp;id          | 用户地址Id                   | integer            |                |
| &emsp;&emsp;&emsp;&emsp;isDefault   | 是否是默认                   | string             |                |
| &emsp;&emsp;&emsp;&emsp;phoneNum    | 联系方式                     | string             |                |
| &emsp;&emsp;&emsp;&emsp;userAddress | 用户地址                     | string             |                |
| &emsp;&emsp;&emsp;&emsp;userId      | 用户id                       | integer            |                |
| message                             | 返回消息                     | string             |                |
| ok                                  |                              | boolean            |                |


**响应示例**:

```javascript
{
	"code": 0,
	"data": {
		"detailArrayList": [
			{
				"createTime": "",
				"id": 0,
				"imgUrl": "",
				"orderId": 0,
				"orderPrice": 0,
				"skuId": 0,
				"skuName": "",
				"skuNum": 0,
				"updateTime": ""
			}
		],
		"totalAmount": 0,
		"totalNum": 0,
		"tradeNo": "",
		"userAddressList": [
			{
				"consignee": "",
				"id": 0,
				"isDefault": "",
				"phoneNum": "",
				"userAddress": "",
				"userId": 0
			}
		]
	},
	"message": "",
	"ok": true
}
```

## 提交订单

**接口地址**:`/order/auth/submitOrder`


**请求方式**:`POST`

**请求数据类型**:`application/x-www-form-urlencoded,application/json`

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |

**请求参数**:


| 参数名称                           | 参数说明             | 请求类型 | 是否必须 | 数据类型       | schema           |
| ---------------------------------- | -------------------- | -------- | -------- | -------------- | ---------------- |
| 秒杀下单参数                       | 秒杀下单参数         | body     | true     | 秒杀下单参数   | 秒杀下单参数     |
| &emsp;&emsp;consignee              | 收货人               |          | false    | string         |                  |
| &emsp;&emsp;consigneeTel           | 联系方式             |          | false    | string         |                  |
| &emsp;&emsp;deliveryAddress        | 收货地址             |          | false    | string         |                  |
| &emsp;&emsp;orderComment           | 订单评论，暂时不需要 |          | false    | string         |                  |
| &emsp;&emsp;orderDetailList        | 订单明细列表         |          | false    | array          | OrderDetailParam |
| &emsp;&emsp;&emsp;&emsp;id         |                      |          | false    | integer        |                  |
| &emsp;&emsp;&emsp;&emsp;imgUrl     |                      |          | false    | string         |                  |
| &emsp;&emsp;&emsp;&emsp;orderId    |                      |          | false    | integer        |                  |
| &emsp;&emsp;&emsp;&emsp;orderPrice |                      |          | false    | number         |                  |
| &emsp;&emsp;&emsp;&emsp;skuId      |                      |          | false    | integer        |                  |
| &emsp;&emsp;&emsp;&emsp;skuName    |                      |          | false    | string         |                  |
| &emsp;&emsp;&emsp;&emsp;skuNum     |                      |          | false    | integer        |                  |
| &emsp;&emsp;paymentWay             |                      |          | false    | string         |                  |
| &emsp;&emsp;userId                 | 用户id               |          | false    | integer(int64) |                  |
| tradeNo                            | tradeNo              | query    | false    | string         |                  |

**请求示例**:


```json
{
  "consignee": "",
  "consigneeTel": "",
  "deliveryAddress": "",
  "orderComment": "",
  "orderDetailList": [
    {
      "id": 0,
      "imgUrl": "",
      "orderId": 0,
      "orderPrice": 0,
      "skuId": 0,
      "skuName": "",
      "skuNum": 0
    }
  ],
  "paymentWay": "",
  "userId": 0
}
```

注意还有一个请求参数：

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | -------- | -------- | -------- | ------ |
| tradeNo  | tradeNo  | query    | false    | string   |        |

**响应状态**:


| 状态码 | 说明                 | schema                 |
| ------ | -------------------- | ---------------------- |
| 200    | 请求成功, 返回订单Id | 全局统一返回结果«long» |
| 201    | 订单提交失败         |                        |
| 401    | Unauthorized         |                        |
| 403    | Forbidden            |                        |
| 404    | Not Found            |                        |
| 500    | 服务器异常           |                        |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | integer(int64) | integer(int64) |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |


**响应示例**:

```javascript
{
	"code": 0,
	"data": 0,
	"message": "订单id",
	"ok": true
}
```


## 获取我的订单

**接口地址**:`/order/auth/{page}/{limit}`


**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:

**请求参数**:


| 参数名称 | 参数说明   | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | ---------- | -------- | -------- | -------------- | ------ |
| limit    | 请求页大小 | path     | true     | integer(int64) |        |
| page     | 请求页数   | path     | true     | integer(int64) |        |

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |

**响应状态**:


| 状态码 | 说明                   | schema                            |
| ------ | ---------------------- | --------------------------------- |
| 200    | 请求成功, 返回分页订单 | 全局统一返回结果«IPage«订单信息»» |
| 201    | 订单提交失败           |                                   |
| 401    | Unauthorized           |                                   |
| 403    | Forbidden              |                                   |
| 404    | Not Found              |                                   |
| 500    | 服务器异常             |                                   |


**响应参数**:


| 参数名称                                       | 参数说明                    | 类型            | schema          |
| ---------------------------------------------- | --------------------------- | --------------- | --------------- |
| code                                           | 返回码                      | integer(int32)  | integer(int32)  |
| data                                           | 返回数据                    | IPage«订单信息» | IPage«订单信息» |
| &emsp;&emsp;current                            |                             | integer(int64)  |                 |
| &emsp;&emsp;hitCount                           |                             | boolean         |                 |
| &emsp;&emsp;pages                              |                             | integer(int64)  |                 |
| &emsp;&emsp;records                            |                             | array           | 订单信息        |
| &emsp;&emsp;&emsp;&emsp;consignee              | 收货人                      | string          |                 |
| &emsp;&emsp;&emsp;&emsp;consigneeTel           | 收件人电话                  | string          |                 |
| &emsp;&emsp;&emsp;&emsp;createTime             | 订单创建时间                | string          |                 |
| &emsp;&emsp;&emsp;&emsp;deliveryAddress        | 送货地址                    | string          |                 |
| &emsp;&emsp;&emsp;&emsp;expireTime             | 失效时间                    | string          |                 |
| &emsp;&emsp;&emsp;&emsp;id                     | 订单Id                      | integer         |                 |
| &emsp;&emsp;&emsp;&emsp;orderComment           | 订单备注                    | string          |                 |
| &emsp;&emsp;&emsp;&emsp;orderDetailList        | 订单商品条目集合            | array           | 订单条目        |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;createTime | 订单条目创建时间            | string          |                 |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id         | 订单条目Id                  | integer         |                 |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;imgUrl     | 订单条目商品图片url         | string          |                 |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;orderId    | 订单Id                      | integer         |                 |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;orderPrice | 订单条目商品价格            | number          |                 |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;skuId      | 订单条目商品skuId           | integer         |                 |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;skuName    | 订单条目商品名称            | string          |                 |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;skuNum     | 订单条目商品价格            | integer         |                 |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;updateTime | 订单条目更新时间            | string          |                 |
| &emsp;&emsp;&emsp;&emsp;orderStatus            | 订单状态                    | string          |                 |
| &emsp;&emsp;&emsp;&emsp;orderStatusName        | 订单状态名称                | string          |                 |
| &emsp;&emsp;&emsp;&emsp;orderType              | 订单类型（普通订单          | 秒杀订单）      | string          |
| &emsp;&emsp;&emsp;&emsp;originalTotalAmount    | 原价金额                    | number          |                 |
| &emsp;&emsp;&emsp;&emsp;outTradeNo             | 订单交易编号（第三方支付用) | string          |                 |
| &emsp;&emsp;&emsp;&emsp;parentOrderId          | 父订单编号                  | integer         |                 |
| &emsp;&emsp;&emsp;&emsp;paymentWay             | 付款方式                    | string          |                 |
| &emsp;&emsp;&emsp;&emsp;refundableTime         | 可退款日期（签收后30天）    | string          |                 |
| &emsp;&emsp;&emsp;&emsp;totalAmount            | 总金额                      | number          |                 |
| &emsp;&emsp;&emsp;&emsp;trackingNo             | 物流单编号                  | string          |                 |
| &emsp;&emsp;&emsp;&emsp;tradeBody              | 订单描述(第三方支付用)      | string          |                 |
| &emsp;&emsp;&emsp;&emsp;updateTime             | 订单更新时间                | string          |                 |
| &emsp;&emsp;&emsp;&emsp;userId                 | 用户id                      | integer         |                 |
| &emsp;&emsp;&emsp;&emsp;wareId                 | 订单对应的发货仓库Id        | string          |                 |
| &emsp;&emsp;searchCount                        |                             | boolean         |                 |
| &emsp;&emsp;size                               |                             | integer(int64)  |                 |
| &emsp;&emsp;total                              |                             | integer(int64)  |                 |
| message                                        | 返回消息                    | string          |                 |
| ok                                             |                             | boolean         |                 |

**响应示例**:  data属性为IPage<OrderInfoDTO>对象

```json
{
	"code": 0,
	"data": {
		"current": 0,
		"hitCount": true,
		"pages": 0,
		"records": [
			{
				"consignee": "",
				"consigneeTel": "",
				"createTime": "",
				"deliveryAddress": "",
				"expireTime": "",
				"id": 0,
				"orderComment": "",
				"orderDetailList": [
					{
						"createTime": "",
						"id": 0,
						"imgUrl": "",
						"orderId": 0,
						"orderPrice": 0,
						"skuId": 0,
						"skuName": "",
						"skuNum": 0,
						"updateTime": ""
					}
				],
				"orderStatus": "",
				"orderStatusName": "",
				"orderType": "",
				"originalTotalAmount": 0,
				"outTradeNo": "",
				"parentOrderId": 0,
				"paymentWay": "",
				"refundableTime": "",
				"totalAmount": 0,
				"trackingNo": "",
				"tradeBody": "",
				"updateTime": "",
				"userId": 0,
				"wareId": ""
			}
		],
		"searchCount": true,
		"size": 0,
		"total": 0
	},
	"message": "",
	"ok": true
}
```

## 获取支付页面数据

**接口地址**:`/pay/auth`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |

**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | -------- | -------- | -------- | -------------- | ------ |
| orderId  | 订单Id   | query    | true     | integer(int64) |        |


**响应状态**:


| 状态码 | 说明                 | schema                     |
| ------ | -------------------- | -------------------------- |
| 200    | 请求成功, 返回订单Id | 全局统一返回结果«订单信息» |
| 201    | 订单提交失败         |                            |
| 401    | Unauthorized         |                            |
| 403    | Forbidden            |                            |
| 404    | Not Found            |                            |
| 500    | 服务器异常           |                            |


**响应参数**:


| 参数名称                           | 参数说明                    | 类型               | schema         |
| ---------------------------------- | --------------------------- | ------------------ | -------------- |
| code                               | 返回码                      | integer(int32)     | integer(int32) |
| data                               | 返回数据                    | 订单信息           | 订单信息       |
| &emsp;&emsp;consignee              | 收货人                      | string             |                |
| &emsp;&emsp;consigneeTel           | 收件人电话                  | string             |                |
| &emsp;&emsp;createTime             | 订单创建时间                | string(date-time)  |                |
| &emsp;&emsp;deliveryAddress        | 送货地址                    | string             |                |
| &emsp;&emsp;expireTime             | 失效时间                    | string(date-time)  |                |
| &emsp;&emsp;id                     | 订单Id                      | integer(int64)     |                |
| &emsp;&emsp;orderComment           | 订单备注                    | string             |                |
| &emsp;&emsp;orderDetailList        | 订单商品条目集合            | array              | 订单条目       |
| &emsp;&emsp;&emsp;&emsp;createTime | 订单条目创建时间            | string             |                |
| &emsp;&emsp;&emsp;&emsp;id         | 订单条目Id                  | integer            |                |
| &emsp;&emsp;&emsp;&emsp;imgUrl     | 订单条目商品图片url         | string             |                |
| &emsp;&emsp;&emsp;&emsp;orderId    | 订单Id                      | integer            |                |
| &emsp;&emsp;&emsp;&emsp;orderPrice | 订单条目商品价格            | number             |                |
| &emsp;&emsp;&emsp;&emsp;skuId      | 订单条目商品skuId           | integer            |                |
| &emsp;&emsp;&emsp;&emsp;skuName    | 订单条目商品名称            | string             |                |
| &emsp;&emsp;&emsp;&emsp;skuNum     | 订单条目商品价格            | integer            |                |
| &emsp;&emsp;&emsp;&emsp;updateTime | 订单条目更新时间            | string             |                |
| &emsp;&emsp;orderStatus            | 订单状态                    | string             |                |
| &emsp;&emsp;orderStatusName        | 订单状态名称                | string             |                |
| &emsp;&emsp;orderType              | 订单类型（普通订单          | 秒杀订单）         | string         |
| &emsp;&emsp;originalTotalAmount    | 原价金额                    | number(bigdecimal) |                |
| &emsp;&emsp;outTradeNo             | 订单交易编号（第三方支付用) | string             |                |
| &emsp;&emsp;parentOrderId          | 父订单编号                  | integer(int64)     |                |
| &emsp;&emsp;paymentWay             | 付款方式                    | string             |                |
| &emsp;&emsp;refundableTime         | 可退款日期（签收后30天）    | string(date-time)  |                |
| &emsp;&emsp;totalAmount            | 总金额                      | number(bigdecimal) |                |
| &emsp;&emsp;trackingNo             | 物流单编号                  | string             |                |
| &emsp;&emsp;tradeBody              | 订单描述(第三方支付用)      | string             |                |
| &emsp;&emsp;updateTime             | 订单更新时间                | string(date-time)  |                |
| &emsp;&emsp;userId                 | 用户id                      | integer(int64)     |                |
| &emsp;&emsp;wareId                 | 订单对应的发货仓库Id        | string             |                |
| message                            | 返回消息                    | string             |                |
| ok                                 |                             | boolean            |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"consignee": "",
		"consigneeTel": "",
		"createTime": "",
		"deliveryAddress": "",
		"expireTime": "",
		"id": 0,
		"orderComment": "",
		"orderDetailList": [
			{
				"createTime": "",
				"id": 0,
				"imgUrl": "",
				"orderId": 0,
				"orderPrice": 0,
				"skuId": 0,
				"skuName": "",
				"skuNum": 0,
				"updateTime": ""
			}
		],
		"orderStatus": "",
		"orderStatusName": "",
		"orderType": "",
		"originalTotalAmount": 0,
		"outTradeNo": "",
		"parentOrderId": 0,
		"paymentWay": "",
		"refundableTime": "",
		"totalAmount": 0,
		"trackingNo": "",
		"tradeBody": "",
		"updateTime": "",
		"userId": 0,
		"wareId": ""
	},
	"message": "",
	"ok": true
}
```

## 获取订单对应的支付二维码

**接口地址**:`/pay/alipay/submit/{orderId}`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**接口描述**:

**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | -------- | -------- | -------- | -------------- | ------ |
| orderId  | 订单Id   | path     | true     | integer(int64) |        |

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |

**响应状态**:


| 状态码 | 说明                   | schema |
| ------ | ---------------------- | ------ |
| 200    | 请求成功, 返回form表单 |        |
| 201    | 获取form表单失败       |        |
| 401    | Unauthorized           |        |
| 403    | Forbidden              |        |
| 404    | Not Found              |        |
| 500    | 服务器异常             |        |

**响应参数**:

表单字符串


**响应示例**:

```javascript
// 响应为表单格式，可嵌入页面，具体以返回的结果为准
<form name="submit_form" method="post" action="https://openapi.alipay.com/gateway.do?charset=UTF-8&method=alipay.trade.page.pay&sign=k0w1DePFqNMQWyGBwOaEsZEJuaIEQufjoPLtwYBYgiX%2FRSkBFY38VuhrNumXpoPY9KgLKtm4nwWz4DEQpGXOOLaqRZg4nDOGOyCmwHmVSV5qWKDgWMiW%2BLC2f9Buil%2BEUdE8CFnWhM8uWBZLGUiCrAJA14hTjVt4BiEyiPrtrMZu0o6%2FXsBu%2Fi6y4xPR%2BvJ3KWU8gQe82dIQbowLYVBuebUMc79Iavr7XlhQEFf%2F7WQcWgdmo2pnF4tu0CieUS7Jb0FfCwV%2F8UyrqFXzmCzCdI2P5FlMIMJ4zQp%2BTBYsoTVK6tg12stpJQGa2u3%2BzZy1r0KNzxcGLHL%2BwWRTx%2FCU%2Fg%3D%3D&notify_url=http%3A%2F%2F114.55.81.185%2Fopendevtools%2Fnotify%2Fdo%2Fbf70dcb4-13c9-4458-a547-3a5a1e8ead04&version=1.0&app_id=2014100900013222&sign_type=RSA&timestamp=2021-02-02+14%3A11%3A40&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json">
<input type="submit" value="提交" style="display:none" >
</form>
<script>document.forms[0].submit();</script>
```

## 支付同步回调

**接口地址**:`/pay/alipay/callback/return`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明         | schema |
| ------ | ------------ | ------ |
| 200    | OK           |        |
| 201    | 发生异常     |        |
| 302    | 重定向响应   |        |
| 401    | Unauthorized |        |
| 403    | Forbidden    |        |
| 404    | Not Found    |        |


**响应参数**:

暂无

## 处理支付宝异步回调

**接口地址**:`/pay/alipay/callback/notify`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称  | 参数说明         | 请求类型 | 是否必须 | 数据类型 | schema |
| --------- | ---------------- | -------- | -------- | -------- | ------ |
| paramsMap | 异步回调通知参数 | query    | true     | object   |        |


**响应状态**:


| 状态码  | 说明                   | schema |
| ------- | ---------------------- | ------ |
| 200     | OK                     |        |
| 201     | Created                |        |
| 401     | Unauthorized           |        |
| 403     | Forbidden              |        |
| 404     | Not Found              |        |
| fail    | 异步通知接收并处理失败 |        |
| success | 异步通知接收并处理成功 |        |


**响应参数**:


暂无

## 获取秒杀活动中的所有秒杀商品

**接口地址**:`/seckill`


**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded``


**接口描述**:


**请求参数**:

暂无


**响应状态**:


| 状态码 | 说明             | schema                           |
| ------ | ---------------- | -------------------------------- |
| 200    | 获取秒杀商品列表 | 全局统一返回结果«List«秒杀商品»» |
| 201    | 获取失败         |                                  |
| 401    | Unauthorized     |                                  |
| 403    | Forbidden        |                                  |
| 404    | Not Found        |                                  |
| 500    | 服务器异常       |                                  |


**响应参数**:


| 参数名称                  | 参数说明                           | 类型               | schema         |
| ------------------------- | ---------------------------------- | ------------------ | -------------- |
| code                      | 返回码                             | integer(int32)     | integer(int32) |
| data                      | 返回数据                           | array              | 秒杀商品       |
| &emsp;&emsp;checkTime     | 秒杀商品审核时间                   | string(date-time)  |                |
| &emsp;&emsp;costPrice     | 秒杀商品秒杀价格                   | number(bigdecimal) |                |
| &emsp;&emsp;endTime       | 秒杀商品所参与的秒杀活动的结束时间 | string(date-time)  |                |
| &emsp;&emsp;id            | 秒杀商品Id                         | integer(int64)     |                |
| &emsp;&emsp;num           | 秒杀商品已售卖数量                 | integer(int32)     |                |
| &emsp;&emsp;price         | 秒杀商品原价                       | number(bigdecimal) |                |
| &emsp;&emsp;skuDefaultImg | 秒杀商品默认图片url                | string             |                |
| &emsp;&emsp;skuDesc       | 秒杀商品描述字符串                 | string             |                |
| &emsp;&emsp;skuId         | 秒杀商品sku Id                     | integer(int64)     |                |
| &emsp;&emsp;skuName       | 秒杀商品名称                       | string             |                |
| &emsp;&emsp;spuId         | 秒杀商品spu Id                     | integer(int64)     |                |
| &emsp;&emsp;startTime     | 秒杀商品所参与的秒杀活动的开始时间 | string(date-time)  |                |
| &emsp;&emsp;status        | 秒杀商品审核状态                   | string             |                |
| &emsp;&emsp;stockCount    | 秒杀商品库存                       | integer(int32)     |                |
| message                   | 返回消息                           | string             |                |
| ok                        |                                    | boolean            |                |


**响应示例**:

```javascript
{
	"code": 0,
	"data": [
		{
			"checkTime": "",
			"costPrice": 0,
			"endTime": "",
			"id": 0,
			"num": 0,
			"price": 0,
			"skuDefaultImg": "",
			"skuDesc": "",
			"skuId": 0,
			"skuName": "",
			"spuId": 0,
			"startTime": "",
			"status": "",
			"stockCount": 0
		}
	],
	"message": "",
	"ok": true
}
```

## 获取秒杀商品详情页数据

**接口地址**:`/seckill/{skuId}`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:

**请求参数**:


| 参数名称 | 参数说明            | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | ------------------- | -------- | -------- | -------------- | ------ |
| skuId    | 详情数据对应的SkuId | path     | true     | integer(int64) |        |

**响应状态**:


| 状态码 | 说明                 | schema                     |
| ------ | -------------------- | -------------------------- |
| 200    | 获取秒杀商品信息成功 | 全局统一返回结果«秒杀商品» |
| 201    | 获取失败             |                            |
| 401    | Unauthorized         |                            |
| 403    | Forbidden            |                            |
| 404    | Not Found            |                            |
| 500    | 服务器异常           |                            |


**响应参数**:


| 参数名称                  | 参数说明                           | 类型               | schema         |
| ------------------------- | ---------------------------------- | ------------------ | -------------- |
| code                      | 返回码                             | integer(int32)     | integer(int32) |
| data                      | 返回数据                           | 秒杀商品           | 秒杀商品       |
| &emsp;&emsp;checkTime     | 秒杀商品审核时间                   | string(date-time)  |                |
| &emsp;&emsp;costPrice     | 秒杀商品秒杀价格                   | number(bigdecimal) |                |
| &emsp;&emsp;endTime       | 秒杀商品所参与的秒杀活动的结束时间 | string(date-time)  |                |
| &emsp;&emsp;id            | 秒杀商品Id                         | integer(int64)     |                |
| &emsp;&emsp;num           | 秒杀商品已售卖数量                 | integer(int32)     |                |
| &emsp;&emsp;price         | 秒杀商品原价                       | number(bigdecimal) |                |
| &emsp;&emsp;skuDefaultImg | 秒杀商品默认图片url                | string             |                |
| &emsp;&emsp;skuDesc       | 秒杀商品描述字符串                 | string             |                |
| &emsp;&emsp;skuId         | 秒杀商品sku Id                     | integer(int64)     |                |
| &emsp;&emsp;skuName       | 秒杀商品名称                       | string             |                |
| &emsp;&emsp;spuId         | 秒杀商品spu Id                     | integer(int64)     |                |
| &emsp;&emsp;startTime     | 秒杀商品所参与的秒杀活动的开始时间 | string(date-time)  |                |
| &emsp;&emsp;status        | 秒杀商品审核状态                   | string             |                |
| &emsp;&emsp;stockCount    | 秒杀商品库存                       | integer(int32)     |                |
| message                   | 返回消息                           | string             |                |
| ok                        |                                    | boolean            |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {
		"checkTime": "",
		"costPrice": 0,
		"endTime": "",
		"id": 0,
		"num": 0,
		"price": 0,
		"skuDefaultImg": "",
		"skuDesc": "",
		"skuId": 0,
		"skuName": "",
		"spuId": 0,
		"startTime": "",
		"status": "",
		"stockCount": 0
	},
	"message": "",
	"ok": true
}
```

## 获取秒杀下单码

**接口地址**:`/seckill/auth/getSeckillSkuIdStr/{skuId}`


**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**接口描述**:


**请求参数**:


| 参数名称 | 参数说明            | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | ------------------- | -------- | -------- | -------------- | ------ |
| skuId    | 秒杀下单商品的skuId | path     | true     | integer(int64) |        |

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |

**响应状态**:


| 状态码 | 说明                       | schema                   |
| ------ | -------------------------- | ------------------------ |
| 200    | 获取下单码成功，返回下单码 | 全局统一返回结果«string» |
| 201    | 获取失败                   |                          |
| 401    | Unauthorized               |                          |
| 403    | Forbidden                  |                          |
| 404    | Not Found                  |                          |
| 500    | 服务器异常                 |                          |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | string         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": "",
	"message": "下单码",
	"ok": true
}
```

## 秒杀接口

**接口地址**:`/seckill/auth/seckillOrder/{skuId}`

**请求方式**:`POST`

**请求数据类型**:`application/x-www-form-urlencoded`

**接口描述**:

**请求参数**:


| 参数名称 | 参数说明            | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | ------------------- | -------- | -------- | -------------- | ------ |
| skuId    | 秒杀下单商品的skuId | path     | true     | integer(int64) |        |
| skuIdStr | 秒杀下单码          | query    | true     | string         |        |

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |

**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | 秒杀成功     | 全局统一返回结果 |
| 201    | 秒杀失败     |                  |
| 217    | 请求非法     |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |
| 500    | 服务器异常   |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```

## 查询秒杀结果

**接口地址**:`/seckill/auth/checkOrder/{skuId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`

**接口描述**:


**请求参数**:


| 参数名称 | 参数说明            | 请求类型 | 是否必须 | 数据类型       | schema |
| -------- | ------------------- | -------- | -------- | -------------- | ------ |
| skuId    | 秒杀下单商品的skuId | path     | true     | integer(int64) |        |

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |

**响应状态**:


| 状态码 | 说明         | schema           |
| ------ | ------------ | ---------------- |
| 200    | OK           | 全局统一返回结果 |
| 211    | 秒杀排队中   |                  |
| 213    | 秒杀商品售罄 |                  |
| 215    | 秒杀成功     |                  |
| 218    | 秒杀下单成功 |                  |
| 401    | Unauthorized |                  |
| 403    | Forbidden    |                  |
| 404    | Not Found    |                  |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | object         |                |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"message": "",
	"ok": true
}
```



## 秒杀商品结算页


**接口地址**:`/seckill/auth/trade`


**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`


**接口描述**:


**请求参数**:

暂无

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |


**响应状态**:


| 状态码 | 说明                     | schema                       |
| ------ | ------------------------ | ---------------------------- |
| 200    | 获取秒杀商品结算信息成功 | 全局统一返回结果«结算页数据» |
| 201    | 获取失败                 |                              |
| 401    | Unauthorized             |                              |
| 403    | Forbidden                |                              |
| 404    | Not Found                |                              |
| 500    | 服务器异常               |                              |


**响应参数**:


| 参数名称                            | 参数说明                     | 类型               | schema         |
| ----------------------------------- | ---------------------------- | ------------------ | -------------- |
| code                                | 返回码                       | integer(int32)     | integer(int32) |
| data                                | 返回数据                     | 结算页数据         | 结算页数据     |
| &emsp;&emsp;detailArrayList         | 订单明细列表                 | array              | 订单条目       |
| &emsp;&emsp;&emsp;&emsp;createTime  | 订单条目创建时间             | string             |                |
| &emsp;&emsp;&emsp;&emsp;id          | 订单条目Id                   | integer            |                |
| &emsp;&emsp;&emsp;&emsp;imgUrl      | 订单条目商品图片url          | string             |                |
| &emsp;&emsp;&emsp;&emsp;orderId     | 订单Id                       | integer            |                |
| &emsp;&emsp;&emsp;&emsp;orderPrice  | 订单条目商品价格             | number             |                |
| &emsp;&emsp;&emsp;&emsp;skuId       | 订单条目商品skuId            | integer            |                |
| &emsp;&emsp;&emsp;&emsp;skuName     | 订单条目商品名称             | string             |                |
| &emsp;&emsp;&emsp;&emsp;skuNum      | 订单条目商品价格             | integer            |                |
| &emsp;&emsp;&emsp;&emsp;updateTime  | 订单条目更新时间             | string             |                |
| &emsp;&emsp;totalAmount             | 总金额                       | number(bigdecimal) |                |
| &emsp;&emsp;totalNum                | 订单条目数量                 | integer(int32)     |                |
| &emsp;&emsp;tradeNo                 | 订单流水号，防止重复提交订单 | string             |                |
| &emsp;&emsp;userAddressList         | 用户地址列表                 | array              | 用户地址       |
| &emsp;&emsp;&emsp;&emsp;consignee   | 收件人                       | string             |                |
| &emsp;&emsp;&emsp;&emsp;id          | 用户地址Id                   | integer            |                |
| &emsp;&emsp;&emsp;&emsp;isDefault   | 是否是默认                   | string             |                |
| &emsp;&emsp;&emsp;&emsp;phoneNum    | 联系方式                     | string             |                |
| &emsp;&emsp;&emsp;&emsp;userAddress | 用户地址                     | string             |                |
| &emsp;&emsp;&emsp;&emsp;userId      | 用户id                       | integer            |                |
| message                             | 返回消息                     | string             |                |
| ok                                  |                              | boolean            |                |


**响应示例**:

```javascript
{
	"code": 0,
	"data": {
		"detailArrayList": [
			{
				"createTime": "",
				"id": 0,
				"imgUrl": "",
				"orderId": 0,
				"orderPrice": 0,
				"skuId": 0,
				"skuName": "",
				"skuNum": 0,
				"updateTime": ""
			}
		],
		"totalAmount": 0,
		"totalNum": 0,
		"tradeNo": "",
		"userAddressList": [
			{
				"consignee": "",
				"id": 0,
				"isDefault": "",
				"phoneNum": "",
				"userAddress": "",
				"userId": 0
			}
		]
	},
	"message": "",
	"ok": true
}
```


## 秒杀下单

**接口地址**:`/seckill/auth/submitOrder`


**请求方式**:`POST`

**请求数据类型**: application/json`

**接口描述**:


**请求参数**:


| 参数名称                           | 参数说明             | 请求类型 | 是否必须 | 数据类型       | schema           |
| ---------------------------------- | -------------------- | -------- | -------- | -------------- | ---------------- |
| 秒杀下单参数                       | 秒杀下单参数         | body     | true     | 秒杀下单参数   | 秒杀下单参数     |
| &emsp;&emsp;consignee              | 收货人               |          | false    | string         |                  |
| &emsp;&emsp;consigneeTel           | 联系方式             |          | false    | string         |                  |
| &emsp;&emsp;deliveryAddress        | 收货地址             |          | false    | string         |                  |
| &emsp;&emsp;orderComment           | 订单评论，暂时不需要 |          | false    | string         |                  |
| &emsp;&emsp;orderDetailList        | 订单明细列表         |          | false    | array          | OrderDetailParam |
| &emsp;&emsp;&emsp;&emsp;id         |                      |          | false    | integer        |                  |
| &emsp;&emsp;&emsp;&emsp;imgUrl     |                      |          | false    | string         |                  |
| &emsp;&emsp;&emsp;&emsp;orderId    |                      |          | false    | integer        |                  |
| &emsp;&emsp;&emsp;&emsp;orderPrice |                      |          | false    | number         |                  |
| &emsp;&emsp;&emsp;&emsp;skuId      |                      |          | false    | integer        |                  |
| &emsp;&emsp;&emsp;&emsp;skuName    |                      |          | false    | string         |                  |
| &emsp;&emsp;&emsp;&emsp;skuNum     |                      |          | false    | integer        |                  |
| &emsp;&emsp;paymentWay             |                      |          | false    | string         |                  |
| &emsp;&emsp;userId                 | 用户id               |          | false    | integer(int64) |                  |

**请求示例**:


```javascript
{
  "consignee": "",
  "consigneeTel": "",
  "deliveryAddress": "",
  "orderComment": "",
  "orderDetailList": [
    {
      "id": 0,
      "imgUrl": "",
      "orderId": 0,
      "orderPrice": 0,
      "skuId": 0,
      "skuName": "",
      "skuNum": 0
    }
  ],
  "paymentWay": "",
  "userId": 0
}
```

**是否携带cookie**：是

| cookie名称 | cookie值                         | 说明                         |
| ---------- | -------------------------------- | ---------------------------- |
| token      | b18808a85e3b435f8c4e72cff0b7e200 | 用户登录成功之后返回的cookie |


**响应状态**:


| 状态码 | 说明                     | schema                 |
| ------ | ------------------------ | ---------------------- |
| 200    | 秒杀下单成功，返回订单Id | 全局统一返回结果«long» |
| 201    | 秒杀下单失败             |                        |
| 401    | Unauthorized             |                        |
| 403    | Forbidden                |                        |
| 404    | Not Found                |                        |
| 500    | 服务器异常               |                        |


**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 返回码   | integer(int32) | integer(int32) |
| data     | 返回数据 | integer(int64) | integer(int64) |
| message  | 返回消息 | string         |                |
| ok       |          | boolean        |                |

**响应示例**:

```json
{
	"code": 0,
	"data": 0,
	"message": "",
	"ok": true
}
```

