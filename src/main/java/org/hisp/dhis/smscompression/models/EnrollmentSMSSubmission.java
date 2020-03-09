package org.hisp.dhis.smscompression.models;

import java.util.ArrayList;

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
import org.hisp.dhis.smscompression.SMSConsts.SMSEnrollmentStatus;
import org.hisp.dhis.smscompression.SMSConsts.SubmissionType;
import org.hisp.dhis.smscompression.SMSSubmissionReader;
import org.hisp.dhis.smscompression.SMSSubmissionWriter;

public class EnrollmentSMSSubmission
    extends
    SMSSubmission
{
    protected UID orgUnit;

    protected UID trackerProgram;

    protected UID trackedEntityType;

    protected UID trackedEntityInstance;

    protected UID enrollment;

    protected Date enrollmentDate;

    protected SMSEnrollmentStatus enrollmentStatus;

    protected Date incidentDate;

    protected GeoPoint coordinates;

    protected List<SMSAttributeValue> values;

    protected List<SMSEvent> events;

    public UID getOrgUnit()
    {
        return orgUnit;
    }

    public void setOrgUnit( String orgUnit )
    {
        this.orgUnit = new UID( orgUnit, MetadataType.ORGANISATION_UNIT );
    }

    public UID getTrackerProgram()
    {
        return trackerProgram;
    }

    public void setTrackerProgram( String trackerProgram )
    {
        this.trackerProgram = new UID( trackerProgram, MetadataType.PROGRAM );
    }

    public UID getTrackedEntityType()
    {
        return trackedEntityType;
    }

    public void setTrackedEntityType( String trackedEntityType )
    {
        this.trackedEntityType = new UID( trackedEntityType, MetadataType.TRACKED_ENTITY_TYPE );
    }

    public UID getTrackedEntityInstance()
    {
        return trackedEntityInstance;
    }

    public void setTrackedEntityInstance( String trackedEntityInstance )
    {
        this.trackedEntityInstance = new UID( trackedEntityInstance, MetadataType.TRACKED_ENTITY_INSTANCE );
    }

    public UID getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment( String enrollment )
    {
        this.enrollment = new UID( enrollment, MetadataType.ENROLLMENT );
    }

    public Date getEnrollmentDate()
    {
        return enrollmentDate;
    }

    public void setEnrollmentDate( Date enrollmentDate )
    {
        this.enrollmentDate = enrollmentDate;
    }

    public SMSEnrollmentStatus getEnrollmentStatus()
    {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus( SMSEnrollmentStatus enrollmentStatus )
    {
        this.enrollmentStatus = enrollmentStatus;
    }

    public Date getIncidentDate()
    {
        return incidentDate;
    }

    public void setIncidentDate( Date incidentDate )
    {
        this.incidentDate = incidentDate;
    }

    public GeoPoint getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates( GeoPoint coordinates )
    {
        this.coordinates = coordinates;
    }

    public List<SMSAttributeValue> getValues()
    {
        return values;
    }

    public void setValues( List<SMSAttributeValue> values )
    {
        this.values = values;
    }

    public List<SMSEvent> getEvents()
    {
        return events;
    }

    public void setEvents( List<SMSEvent> events )
    {
        this.events = events;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( !super.equals( o ) )
        {
            return false;
        }
        EnrollmentSMSSubmission subm = (EnrollmentSMSSubmission) o;

        return Objects.equals( orgUnit, subm.orgUnit ) && Objects.equals( trackerProgram, subm.trackerProgram )
            && Objects.equals( trackedEntityType, subm.trackedEntityType )
            && Objects.equals( trackedEntityInstance, subm.trackedEntityInstance )
            && Objects.equals( enrollment, subm.enrollment ) && Objects.equals( enrollmentDate, subm.enrollmentDate )
            && Objects.equals( enrollmentStatus, subm.enrollmentStatus )
            && Objects.equals( incidentDate, subm.incidentDate ) && Objects.equals( coordinates, subm.coordinates )
            && Objects.equals( values, subm.values ) && Objects.equals( events, subm.events );
    }

    @Override
    public void writeSubm( SMSSubmissionWriter writer, int version )
        throws SMSCompressionException
    {
        switch ( version )
        {
        case 1:
            writeSubmV1( writer, version );
            break;
        case 2:
            writeSubmV2( writer, version );
            break;
        default:
            throw new SMSCompressionException( versionError( version ) );
        }
    }

    private void writeSubmV1( SMSSubmissionWriter writer, int version )
        throws SMSCompressionException
    {
        writer.writeID( orgUnit );
        writer.writeID( trackerProgram );
        writer.writeID( trackedEntityType );
        writer.writeID( trackedEntityInstance );
        writer.writeID( enrollment );
        writer.writeNonNullableDate( enrollmentDate );
        writer.writeAttributeValues( values );
    }

    private void writeSubmV2( SMSSubmissionWriter writer, int version )
        throws SMSCompressionException
    {
        writer.writeID( orgUnit );
        writer.writeID( trackerProgram );
        writer.writeID( trackedEntityType );
        writer.writeID( trackedEntityInstance );
        writer.writeID( enrollment );
        writer.writeDate( enrollmentDate );
        writer.writeEnrollmentStatus( enrollmentStatus );
        writer.writeDate( incidentDate );
        writer.writeGeoPoint( coordinates );
        boolean hasValues = (values != null && !values.isEmpty());
        writer.writeBool( hasValues );
        if ( hasValues )
        {
            writer.writeAttributeValues( values );
        }
        writer.writeEvents( events, version );
    }

    @Override
    public void readSubm( SMSSubmissionReader reader, int version )
        throws SMSCompressionException
    {
        switch ( version )
        {
        case 1:
            readSubmV1( reader, version );
            break;
        case 2:
            readSubmV2( reader, version );
            break;
        default:
            throw new SMSCompressionException( versionError( version ) );
        }
    }

    public void readSubmV1( SMSSubmissionReader reader, int version )
        throws SMSCompressionException
    {
        this.orgUnit = reader.readID( MetadataType.ORGANISATION_UNIT );
        this.trackerProgram = reader.readID( MetadataType.PROGRAM );
        this.trackedEntityType = reader.readID( MetadataType.TRACKED_ENTITY_TYPE );
        this.trackedEntityInstance = reader.readID( MetadataType.TRACKED_ENTITY_INSTANCE );
        this.enrollment = reader.readID( MetadataType.ENROLLMENT );
        this.enrollmentDate = reader.readNonNullableDate();
        this.values = reader.readAttributeValues();
        this.events = new ArrayList<SMSEvent>();
    }

    public void readSubmV2( SMSSubmissionReader reader, int version )
        throws SMSCompressionException
    {
        this.orgUnit = reader.readID( MetadataType.ORGANISATION_UNIT );
        this.trackerProgram = reader.readID( MetadataType.PROGRAM );
        this.trackedEntityType = reader.readID( MetadataType.TRACKED_ENTITY_TYPE );
        this.trackedEntityInstance = reader.readID( MetadataType.TRACKED_ENTITY_INSTANCE );
        this.enrollment = reader.readID( MetadataType.ENROLLMENT );
        this.enrollmentDate = reader.readDate();
        this.enrollmentStatus = reader.readEnrollmentStatus();
        this.incidentDate = reader.readDate();
        this.coordinates = reader.readGeoPoint();
        boolean hasValues = reader.readBool();
        this.values = hasValues ? reader.readAttributeValues() : new ArrayList<SMSAttributeValue>();
        this.events = reader.readEvents( version );
    }

    @Override
    public int getCurrentVersion()
    {
        return 2;
    }

    @Override
    public SubmissionType getType()
    {
        return SMSConsts.SubmissionType.ENROLLMENT;
    }

}
