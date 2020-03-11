package org.hisp.dhis.smscompression;

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

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.hisp.dhis.smscompression.models.AggregateDatasetSMSSubmission;
import org.hisp.dhis.smscompression.models.EnrollmentSMSSubmission;
import org.hisp.dhis.smscompression.models.SMSEvent;
import org.hisp.dhis.smscompression.models.SMSMetadata;
import org.hisp.dhis.smscompression.models.SMSSubmission;
import org.hisp.dhis.smscompression.models.SMSSubmissionHeader;
import org.hisp.dhis.smscompression.models.SimpleEventSMSSubmission;
import org.hisp.dhis.smscompression.models.TrackerEventSMSSubmission;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class TestEmptyVals
{
    SMSMetadata meta;

    SMSSubmissionWriter writer;

    SMSSubmissionReader reader;

    public String compressSubm( SMSSubmission subm )
        throws Exception
    {
        byte[] compressSubm = writer.compress( subm );
        String comp64 = TestUtils.encBase64( compressSubm );
        TestUtils.printBase64Subm( comp64, subm.getClass() );
        return comp64;
    }

    public SMSSubmission decompressSubm( String comp64 )
        throws Exception
    {
        byte[] decSubmBytes = TestUtils.decBase64( comp64 );
        SMSSubmissionHeader header = reader.readHeader( decSubmBytes );
        Assert.assertNotNull( header );
        return reader.readSubmission( decSubmBytes, meta );
    }

    @Before
    public void init()
        throws Exception
    {
        Gson gson = new Gson();
        String metadataJson = IOUtils.toString( new FileReader( "src/test/resources/metadata.json" ) );
        meta = gson.fromJson( metadataJson, SMSMetadata.class );
        writer = new SMSSubmissionWriter( meta );
        reader = new SMSSubmissionReader();
    }

    @After
    public void cleanup()
    {

    }

    @Test
    public void testEncDecAggregateDatasetNulls()
    {
        try
        {
            AggregateDatasetSMSSubmission origSubm = CreateSubm.createAggregateDatasetSubmission();
            origSubm.setValues( null );
            String comp64 = compressSubm( origSubm );
            AggregateDatasetSMSSubmission decSubm = (AggregateDatasetSMSSubmission) decompressSubm( comp64 );

            TestUtils.checkSubmissionsAreEqual( origSubm, decSubm );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    @Test
    public void testEncDecSimpleEventNulls()
    {
        try
        {
            SimpleEventSMSSubmission origSubm = CreateSubm.createSimpleEventSubmission();
            origSubm.setEventDate( null );
            origSubm.setDueDate( null );
            origSubm.setCoordinates( null );
            origSubm.setValues( null );
            String comp64 = compressSubm( origSubm );
            SimpleEventSMSSubmission decSubm = (SimpleEventSMSSubmission) decompressSubm( comp64 );

            TestUtils.checkSubmissionsAreEqual( origSubm, decSubm );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    @Test
    public void testEncDecEnrollmentNulls()
    {
        try
        {
            EnrollmentSMSSubmission origSubm = CreateSubm.createEnrollmentSubmission();
            origSubm.setEnrollmentDate( null );
            origSubm.setIncidentDate( null );
            origSubm.setCoordinates( null );
            origSubm.setValues( null );
            origSubm.setEvents( null );
            String comp64 = compressSubm( origSubm );
            EnrollmentSMSSubmission decSubm = (EnrollmentSMSSubmission) decompressSubm( comp64 );

            TestUtils.checkSubmissionsAreEqual( origSubm, decSubm );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    @Test
    public void testEncDecEnrollmentNullEvents()
    {
        try
        {
            EnrollmentSMSSubmission origSubm = CreateSubm.createEnrollmentSubmission();
            List<SMSEvent> blankEvents = new ArrayList<>();
            for ( SMSEvent e : origSubm.getEvents() )
            {
                e.setEventDate( null );
                e.setDueDate( null );
                e.setCoordinates( null );
                e.setValues( null );
                blankEvents.add( e );
            }
            origSubm.setEvents( blankEvents );
            String comp64 = compressSubm( origSubm );
            EnrollmentSMSSubmission decSubm = (EnrollmentSMSSubmission) decompressSubm( comp64 );

            TestUtils.checkSubmissionsAreEqual( origSubm, decSubm );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    @Test
    public void testEncDecTrackerEventNulls()
    {
        try
        {
            TrackerEventSMSSubmission origSubm = CreateSubm.createTrackerEventSubmission();
            origSubm.setEventDate( null );
            origSubm.setDueDate( null );
            origSubm.setCoordinates( null );
            origSubm.setValues( null );
            String comp64 = compressSubm( origSubm );
            TrackerEventSMSSubmission decSubm = (TrackerEventSMSSubmission) decompressSubm( comp64 );

            TestUtils.checkSubmissionsAreEqual( origSubm, decSubm );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

}
