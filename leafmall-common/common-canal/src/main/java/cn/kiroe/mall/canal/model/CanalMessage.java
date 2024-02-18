package cn.kiroe.mall.canal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Canal 消息
 *
 */
@NoArgsConstructor
@Data
public class CanalMessage {

    @JsonProperty("data")
    private List<Map<String,Object>> data;
    @JsonProperty("database")
    private String database;
    @JsonProperty("es")
    private Long es;
    @JsonProperty("gtid")
    private String gtid;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("isDdl")
    private Boolean isDdl;
    @JsonProperty("old")
    private List<Map<String,Object>> old;
    @JsonProperty("pkNames")
    private List<String> pkNames;
    @JsonProperty("sql")
    private String sql;
    @JsonProperty("table")
    private String table;
    @JsonProperty("ts")
    private Long ts;
    @JsonProperty("type")
    private String type;
}