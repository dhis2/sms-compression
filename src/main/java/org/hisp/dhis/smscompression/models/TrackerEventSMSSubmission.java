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
import org.hisp.dhis.smscompression.SMSConsts;
import org.hisp.dhis.smscompression.SMSConsts.MetadataType;
import org.hisp.dhis.smscompression.SMSConsts.SMSEventStatus;
import org.hisp.dhis.smscompression.SMSConsts.SubmissionType;
import org.hisp.dhis.smscompression.SMSSubmissionReader;
import org.hisp.dhis.smscompression.SMSSubmissionWriter;

public class TrackerEventSMSSubmission
    extends
    SMSSubmission
{
    protected UID orgUnit;

    protected UID programStage;

    protected SMSEventStatus eventStatus;

    protected UID attributeOptionCombo;

    protected UID enrollment;

    protected UID event;

    protected Date eventDate;

    protected Date dueDate;

    protected GeoPoint coordinates;

    protected List<SMSDataValue> values;

    /* Getters and Setters */

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

    public UID getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment( String enrollment )
    {
        this.enrollment = new UID( enrollment, MetadataType.ENROLLMENT );
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
        if ( !super.equals( o ) )
        {
            return false;
        }
        TrackerEventSMSSubmission subm = (TrackerEventSMSSubmission) o;

        return Objects.equals( orgUnit, subm.orgUnit ) && Objects.equals( programStage, subm.programStage )
            && Objects.equals( eventStatus, subm.eventStatus )
            && Objects.equals( attributeOptionCombo, subm.attributeOptionCombo ) && enrollment.equals( subm.enrollment )
            && Objects.equals( event, subm.event ) && Objects.equals( eventDate, subm.eventDate )
            && Objects.equals( dueDate, subm.dueDate ) && Objects.equals( coordinates, subm.coordinates )
            && Objects.equals( values, subm.values );
    }

    /* Implementation of abstract methods */

    @Override
    public void writeSubm( SMSSubmissionWriter writer, int version )
        throws SMSCompressionException
    {
        switch ( version )
        {
        case 1:
            writeSubmV1( writer );
            break;
        case 2:
            writeSubmV2( writer );
            break;
        default:
            throw new SMSCompressionException( versionError( version ) );
        }
    }

    private void writeSubmV1( SMSSubmissionWriter writer )
        throws SMSCompressionException
    {
        writer.writeID( orgUnit );
        writer.writeID( programStage );
        writer.writeEventStatus( eventStatus );
        writer.writeID( attributeOptionCombo );
        writer.writeID( enrollment );
        writer.writeID( event );
        writer.writeNonNullableDate( eventDate );
        writer.writeDataValues( values );
    }

    private void writeSubmV2( SMSSubmissionWriter writer )
        throws SMSCompressionException
    {
        writer.writeID( orgUnit );
        writer.writeID( programStage );
        writer.writeEventStatus( eventStatus );
        writer.writeID( attributeOptionCombo );
        writer.writeID( enrollment );
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

    @Override
    public void readSubm( SMSSubmissionReader reader, int version )
        throws SMSCompressionException
    {
        switch ( version )
        {
        case 1:
            readSubmV1( reader );
            break;
        case 2:
            readSubmV2( reader );
            break;
        default:
            throw new SMSCompressionException( versionError( version ) );
        }
    }

    private void readSubmV1( SMSSubmissionReader reader )
        throws SMSCompressionException
    {
        this.orgUnit = reader.readID( MetadataType.ORGANISATION_UNIT );
        this.programStage = reader.readID( MetadataType.PROGRAM_STAGE );
        this.eventStatus = reader.readEventStatus();
        this.attributeOptionCombo = reader.readID( MetadataType.CATEGORY_OPTION_COMBO );
        this.enrollment = reader.readID( MetadataType.ENROLLMENT );
        this.event = reader.readID( MetadataType.EVENT );
        this.eventDate = reader.readNonNullableDate();
        this.values = reader.readDataValues();
    }

    private void readSubmV2( SMSSubmissionReader reader )
        throws SMSCompressionException
    {
        this.orgUnit = reader.readID( MetadataType.ORGANISATION_UNIT );
        this.programStage = reader.readID( MetadataType.PROGRAM_STAGE );
        this.eventStatus = reader.readEventStatus();
        this.attributeOptionCombo = reader.readID( MetadataType.CATEGORY_OPTION_COMBO );
        this.enrollment = reader.readID( MetadataType.ENROLLMENT );
        this.event = reader.readID( MetadataType.EVENT );
        this.eventDate = reader.readDate();
        this.dueDate = reader.readDate();
        this.coordinates = reader.readGeoPoint();
        boolean hasValues = reader.readBool();
        this.values = hasValues ? reader.readDataValues() : null;
    }

    @Override
    public int getCurrentVersion()
    {
        return 2;
    }

    @Override
    public SubmissionType getType()
    {
        return SMSConsts.SubmissionType.TRACKER_EVENT;
    }
}
