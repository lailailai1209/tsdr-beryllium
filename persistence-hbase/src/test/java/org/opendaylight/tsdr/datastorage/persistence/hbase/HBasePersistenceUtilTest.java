/*
 * Copyright (c) 2016 xFlow Research Inc. and others.  All rights reserved
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tsdr.datastorage.persistence.hbase;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.tsdr.persistence.hbase.HBaseEntity;
import org.opendaylight.tsdr.persistence.hbase.HBasePersistenceUtil;
import org.opendaylight.tsdr.spi.model.TSDRConstants;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.DataCategory;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.TSDRLog;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.TSDRMetric;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrlogrecord.input.TSDRLogRecord;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrlogrecord.input.TSDRLogRecordBuilder;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrmetricrecord.input.TSDRMetricRecord;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrmetricrecord.input.TSDRMetricRecordBuilder;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.tsdrlog.RecordAttributes;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.tsdrrecord.RecordKeys;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.tsdrrecord.RecordKeysBuilder;

import junit.framework.Assert;

public class HBasePersistenceUtilTest {
    public HBasePersistenceUtil hBasePersistenceUtil = null;

    @Before
    public void setUp() {
        hBasePersistenceUtil = new HBasePersistenceUtil();
    }

    @Test
    public void testGetEntityFromMetricStats() {
        String timeStamp = (new Long((new Date()).getTime())).toString();
        List<RecordKeys> recordKeys = new ArrayList<RecordKeys>();
        RecordKeys recordKey = new RecordKeysBuilder()
            .setKeyName(TSDRConstants.FLOW_TABLE_KEY_NAME)
            .setKeyValue("table1").build();
        recordKeys.add(recordKey);
        TSDRMetricRecordBuilder builder1 = new TSDRMetricRecordBuilder();
        TSDRMetric tsdrMetric1 =   builder1.setMetricName("PacketsMatched")
                .setMetricValue(new BigDecimal(Double.parseDouble("20000000")))
                .setNodeID("node1")
                .setRecordKeys(recordKeys)
                .setTSDRDataCategory(DataCategory.FLOWTABLESTATS)
                .setTimeStamp(new Long(timeStamp)).build();
        hBasePersistenceUtil.getEntityFromMetricStats(null, DataCategory.FLOWTABLESTATS);
        hBasePersistenceUtil.getEntityFromMetricStats(builder1.setMetricName("PMD").setMetricValue(null).setNodeID("node2").build(), DataCategory.FLOWTABLESTATS);
        hBasePersistenceUtil.getEntityFromMetricStats(builder1.setMetricName("PMD").setMetricValue(new BigDecimal(Double.parseDouble("20000000"))).setNodeID("node2").setTimeStamp(null).build(), DataCategory.FLOWTABLESTATS);
        HBaseEntity entity = null;
        entity = hBasePersistenceUtil.getEntityFromMetricStats(tsdrMetric1, DataCategory.FLOWTABLESTATS);
        assertTrue(entity != null);
    }

    @Test
    public void testGetEntityFromLogRecord() {
        String timeStamp = (new Long((new Date()).getTime())).toString();
        List<RecordKeys> recordKeys = new ArrayList<RecordKeys>();
        RecordKeys recordKey1 = new RecordKeysBuilder()
            .setKeyName(DataCategory.SYSLOG.name())
            .setKeyValue("log1").build();
        recordKeys.add(recordKey1);
        TSDRLogRecordBuilder builder1 = new TSDRLogRecordBuilder();
        List<RecordAttributes> value = new ArrayList<RecordAttributes>();
        value.add(mock(RecordAttributes.class));
        value.add(mock(RecordAttributes.class));
        TSDRLog tsdrLog1 =   builder1.setIndex(1)
            .setRecordFullText("su root failed for lonvick")
            .setNodeID("node1.example.com")
            .setRecordKeys(recordKeys)
            .setTSDRDataCategory(DataCategory.SYSLOG)
            .setRecordAttributes(value)
            .setTimeStamp(new Long(timeStamp)).build();
        hBasePersistenceUtil.getEntityFromLogRecord(null, DataCategory.SYSLOG);
        hBasePersistenceUtil.getEntityFromLogRecord(builder1.setNodeID("node1.example.com").setTimeStamp(null).build(), DataCategory.SYSLOG);
        HBaseEntity entity = null;
        entity = hBasePersistenceUtil.getEntityFromLogRecord((TSDRLogRecord)tsdrLog1, DataCategory.SYSLOG);
        assertTrue(entity != null);
    }

    @After
    public void tearDown() {
        hBasePersistenceUtil = null;
    }
}
