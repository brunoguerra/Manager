package com.ajurasz.util.sql.dialect;

import org.hibernate.dialect.MySQL5Dialect;

import java.sql.Types;

/**
 * source: http://stackoverflow.com/questions/8667965/found-bit-expected-boolean-after-hibernate-4-upgrade
 */
public class MySQL5BitBooleanDialect extends MySQL5Dialect {
    public MySQL5BitBooleanDialect() {
        super();
        registerColumnType(Types.BOOLEAN, "bit");
    }
}
