package org.linn.webclient.entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by linn  20/3/2 13:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;

    private String name;

    private int age;

}
