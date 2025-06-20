package org.lw.vms;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
public class Test {
    @org.junit.jupiter.api.Test
    public void testBasicAssertions() {
//        assertEquals(4, 2 + 3); // 验证 2+2 是否等于 4
        assertNull(new Object()); // 验证新对象是否不为 null
    }
}
