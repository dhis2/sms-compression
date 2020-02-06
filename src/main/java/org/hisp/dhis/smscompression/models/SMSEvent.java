package org.hisp.dhis.smscompression.models;

/*
 * Copyright (c) 2004-2019, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.Date;
import java.util.List;

import org.hisp.dhis.smscompression.SMSCompressionException;
import org.hisp.dhis.smscompression.SMSConsts.MetadataType;
import org.hisp.dhis.smscompression.SMSConsts.SMSEventStatus;
import org.hisp.dhis.smscompression.SMSSubmissionReader;
import org.hisp.dhis.smscompression.SMSSubmissionWriter;

public class SMSEvent
{
    protected UID programStage;

    protected SMSEventStatus eventStatus;

    protected UID attributeOptionCombo;

    protected UID event;

    protected Date timestamp;

    protected List<SMSDataValue> values;

    public UID getProgramStage()
    {
        return programStage;
    }

    public void setProgramStage( UID programStage )
    {
        this.programStage = programStage;
    }

    public SMSEventStatus getEventStatus()
    {
        return eventStatus;
    }

    public void setEventStatus( SMSEventStatus eventStatus )
    {
        this.eventStatus = eventStatus;
    }

    public UID getAttributeOptionCombo()
    {
        return attributeOptionCombo;
    }

    public void setAttributeOptionCombo( UID attributeOptionCombo )
    {
        this.attributeOptionCombo = attributeOptionCombo;
    }

    public UID getEvent()
    {
        return event;
    }

    public void setEvent( UID event )
    {
        this.event = event;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp( Date timestamp )
    {
        this.timestamp = timestamp;
    }

    public List<SMSDataValue> getValues()
    {
        return values;
    }

    public void setValues( List<SMSDataValue> values )
    {
        this.values = values;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }
        SMSEvent event = (SMSEvent) o;

        return programStage.equals( event.programStage ) && eventStatus == event.eventStatus
            && attributeOptionCombo.equals( event.attributeOptionCombo ) && event.equals( event.event )
            && timestamp.equals( event.timestamp ) && values.equals( event.values );
    }

    public void writeEvent( SMSSubmissionWriter writer )
        throws SMSCompressionException
    {
        writer.writeID( programStage );
        writer.writeEventStatus( eventStatus );
        writer.writeID( attributeOptionCombo );
        writer.writeID( event );
        writer.writeDate( timestamp );
        writer.writeDataValues( values );
    }

    public void readEvent( SMSSubmissionReader reader )
        throws SMSCompressionException
    {
        this.programStage = reader.readID( MetadataType.PROGRAM_STAGE );
        this.eventStatus = reader.readEventStatus();
        this.attributeOptionCombo = reader.readID( MetadataType.CATEGORY_OPTION_COMBO );
        this.event = reader.readID( MetadataType.EVENT );
        this.timestamp = reader.readDate();
        this.values = reader.readDataValues();
    }
}
