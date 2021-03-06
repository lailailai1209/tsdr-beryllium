/*
 * Copyright (c) 2015 Dell Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.tsdr.persistence.cassandra.command;

import java.util.List;
import org.apache.karaf.shell.commands.Command;
import org.opendaylight.tsdr.spi.command.AbstractListMetricsCommand;
import org.opendaylight.tsdr.spi.util.FormatUtil;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrlogrecord.input.TSDRLogRecord;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrmetricrecord.input.TSDRMetricRecord;

/*
 * Updated by Sharon Aicler (saichler@cisco.com) for cassandra impl.
 */
/**
*This class implement the functionality of the tsdr:list command from Karaf
*console.
*
*It takes the arguments in the command input and query the cassandra data store
*for the records that satisfy the criteria.
*
*Since there is no paging support, the maximum number of records would be 1000.
*
* @author <a href="mailto:yuling_c@dell.com">YuLing Chen</a>
* @author <a href="mailto:saichler@cisco.com">Sharon Aicler</a>
* Created: Aug, 2015
**/
@Command(scope = "tsdr", name = "list", description = "Lists recent 1000 metrics(default) or returns time specified metrics")
public class ListMetricsCommand  extends AbstractListMetricsCommand {
    /**
     * Format and print out the result of the metrics on Karaf console.
     */
    @Override
    protected String listMetrics(List<TSDRMetricRecord> metrics) {
        StringBuilder buffer = new StringBuilder();
        for (TSDRMetricRecord metric : metrics) {
            buffer.append(FormatUtil.getTSDRMetricKeyWithTimeStamp(metric));
            buffer.append("[").append(metric.getMetricValue()).append("]\n");
        }
        return buffer.toString();
    }

    @Override
    protected String listLogs(List<TSDRLogRecord> logs) {
        StringBuilder buffer = new StringBuilder();
        for (TSDRLogRecord log : logs) {
            buffer.append(FormatUtil.getTSDRLogKeyWithTimeStamp(log));
            buffer.append("[").append(log.getRecordFullText()).append("]\n");
        }
        return buffer.toString();
    }
}
