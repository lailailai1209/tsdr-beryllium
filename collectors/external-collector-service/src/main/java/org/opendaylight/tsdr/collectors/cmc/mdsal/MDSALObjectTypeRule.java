/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tsdr.collectors.cmc.mdsal;

import org.datasand.codec.AttributeDescriptor;
import org.datasand.codec.observers.ITypeAttributeObserver;
/**
 * @author - Sharon Aicler (saichler@cisco.com)
 */
public class MDSALObjectTypeRule implements ITypeAttributeObserver{

    @Override
    public boolean isTypeAttribute(AttributeDescriptor ad) {
        return (ad.getReturnType().getName().indexOf(".rev")!=-1);
    }
}
