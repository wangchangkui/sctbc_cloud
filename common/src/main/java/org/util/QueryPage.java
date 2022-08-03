package org.util;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:04:00
 */
@Data
public class QueryPage <T,M>{

    private T query;

    private Page<M> page;
}
