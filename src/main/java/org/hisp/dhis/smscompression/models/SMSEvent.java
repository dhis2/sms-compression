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
import java.util.Objects;

import org.hisp.dhis.smscompression.SMSCompressionException;
import org.hisp.dhis.smscompression.SMSConsts.MetadataType;
import org.hisp.dhis.smscompression.SMSConsts.SMSEventStatus;
import org.hisp.dhis.smscompression.SMSSubmissionReader;
import org.hisp.dhis.smscompression.SMSSubmissionWriter;

public class SMSEvent
{
    protected UID orgUnit;

    protected UID programStage;

    protected SMSEventStatus eventStatus;

    protected UID attributeOptionCombo;

    protected UID event;

    protected Date eventDate;

    protected Date dueDate;

    protected GeoPoint coordinates;

    protected List<SMSDataValue> values;

    public UID getOrgUnit()
    {
        return orgUnit;
    }

    public void setOrgUnit( String orgUnit )
    {
        this.orgUnit = new UID( orgUnit, MetadataType.ORGANISATION_UNIT );
    }

    public UID getProgramStage()
    {
        return programStage;
    }

    public void setProgramStage( String programStage )
    {
        this.programStage = new UID( programStage, MetadataType.PROGRAM_STAGE );
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

    public void setAttributeOptionCombo( String attributeOptionCombo )
    {
        this.attributeOptionCombo = new UID( attributeOptionCombo, MetadataType.CATEGORY_OPTION_COMBO );
    }

    public UID getEvent()
    {
        return event;
    }

    public void setEvent( String event )
    {
        this.event = new UID( event, MetadataType.EVENT );
    }

    public Date getEventDate()
    {
        return eventDate;
    }

    public void setEventDate( Date eventDate )
    {
        this.eventDate = eventDate;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public void setDueDate( Date dueDate )
    {
        this.dueDate = dueDate;
    }

    public GeoPoint getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates( GeoPoint coordinates )
    {
        this.coordinates = coordinates;
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
        SMSEvent e = (SMSEvent) o;

        return Objects.equals( orgUnit, e.orgUnit ) && Objects.equals( programStage, e.programStage )
            && Objects.equals( eventStatus, e.eventStatus )
            && Objects.equals( attributeOptionCombo, e.attributeOptionCombo ) && event.equals( e.event )
            && Objects.equals( eventDate, e.eventDate ) && Objects.equals( dueDate, e.dueDate )
            && Objects.equals( coordinates, e.coordinates ) && Objects.equals( values, e.values );
    }

    public void writeEvent( SMSSubmissionWriter writer, int version )
        throws SMSCompressionException
    {
        if ( version != 2 )
        {
            throw new SMSCompressionException( versionError( version ) );
        }

        writer.writeID( orgUnit );
        writer.writeID( programStage );
        writer.writeEventStatus( eventStatus );
        writer.writeID( attributeOptionCombo );
        writer.writeID( event );
        writer.writeDate( eventDate );
        writer.writeDate( dueDate );
        writer.writeGeoPoint( coordinates );
        boolean hasValues = (values != null && !values.isEmpty());
        writer.writeBool( hasValues );
        if ( hasValues )
        {
            writer.writeDataValues( values );
        }
    }

    public void readEvent( SMSSubmissionReader reader, int version )
        throws SMSCompressionException
    {
        if ( version != 2 )
        {
            throw new SMSCompressionException( versionError( version ) );
        }

        this.orgUnit = reader.readID( MetadataType.ORGANISATION_UNIT );
        this.programStage = reader.readID( MetadataType.PROGRAM_STAGE );
        this.eventStatus = reader.readEventStatus();
        this.attributeOptionCombo = reader.readID( MetadataType.CATEGORY_OPTION_COMBO );
        this.event = reader.readID( MetadataType.EVENT );
        this.eventDate = reader.readDate();
        this.dueDate = reader.readDate();
        this.coordinates = reader.readGeoPoint();
        boolean hasValues = reader.readBool();
        this.values = hasValues ? reader.readDataValues() : null;
    }

    public int getCurrentVersion()
    {
        return 2;
    }

    private String versionError( int version )
    {
        return String.format( "Version %d of %s is not supported", version, this.getClass().getSimpleName() );
    }
}
