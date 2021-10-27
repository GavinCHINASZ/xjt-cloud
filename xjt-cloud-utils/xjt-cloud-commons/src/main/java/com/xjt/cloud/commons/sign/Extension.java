package com.xjt.cloud.commons.sign;

/**
 *
 */
public class Extension {
    private String oid;

    private boolean critical;

    private byte[] value;

    public String getOid() {
        return oid;
    }

    public byte[] getValue() {
        return value;
    }
    public boolean isCritical() {
        return critical;
    }
}
