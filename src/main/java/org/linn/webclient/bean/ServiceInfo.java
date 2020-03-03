package org.linn.webclient.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by linn  20/3/2 16:06
 */
@Data
@Builder
@NoArgsConstructor //无参构造函数
@AllArgsConstructor //全参构造函数
public class ServiceInfo {

    private String url;
}
