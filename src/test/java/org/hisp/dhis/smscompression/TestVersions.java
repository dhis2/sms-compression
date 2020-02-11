package org.hisp.dhis.smscompression;

import static org.junit.Assert.assertEquals;

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

import org.apache.commons.io.IOUtils;
import org.hisp.dhis.smscompression.models.AggregateDatasetSMSSubmission;
import org.hisp.dhis.smscompression.models.EnrollmentSMSSubmission;
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

public class TestVersions
{
    SMSMetadata meta;

    SMSSubmissionWriter writer;

    SMSSubmissionReader reader;

    public String compressSubm( SMSSubmission subm, int version )
        throws Exception
    {
        byte[] compressSubm = writer.compress( subm, version );
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
    public void testEncDecSimpleEventVersion1()
    {
        try
        {
            SimpleEventSMSSubmission origSubm = TestUtils.createSimpleEventSubmission();
            String comp64 = compressSubm( origSubm, 1 );
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
    public void testEncDecAggregateDatasetVersion1()
    {
        try
        {
            AggregateDatasetSMSSubmission origSubm = TestUtils.createAggregateDatasetSubmission();
            String comp64 = compressSubm( origSubm, 1 );
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
    public void testEncDecEnrollmentVersion1()
    {
        try
        {
            EnrollmentSMSSubmission origSubm = TestUtils.createEnrollmentSubmissionNoEvents();
            String comp64 = compressSubm( origSubm, 1 );
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
    public void testEncDecTrackerEventVersion1()
    {
        try
        {
            TrackerEventSMSSubmission origSubm = TestUtils.createTrackerEventSubmission();
            String comp64 = compressSubm( origSubm, 1 );
            TrackerEventSMSSubmission decSubm = (TrackerEventSMSSubmission) decompressSubm( comp64 );

            TestUtils.checkSubmissionsAreEqual( origSubm, decSubm );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    @Test
    public void testWriteUnknownVersion()
    {
        try
        {
            compressSubm( TestUtils.createTrackerEventSubmission(), 0 );
        }
        catch ( Exception e )
        {
            assertEquals( e.getClass(), SMSCompressionException.class );
            assertEquals( e.getMessage(), "Version 0 of TrackerEventSMSSubmission is not supported" );
            return;
        }

        Assert.fail( "Expected unknown version exception not found" );
    }

    @Test
    public void testWriteFutureVersion()
    {
        SMSSubmission subm = TestUtils.createTrackerEventSubmission();
        int futureVer = subm.getCurrentVersion() + 1;

        try
        {
            compressSubm( subm, futureVer );
        }
        catch ( Exception e )
        {
            assertEquals( e.getClass(), SMSCompressionException.class );
            assertEquals( e.getMessage(),
                String.format( "Version %d of TrackerEventSMSSubmission is not supported", futureVer ) );
            return;
        }

        Assert.fail( "Expected unknown version exception not found" );
    }

    @Test
    public void testWriteUnknownVersionRelationship()
    {
        try
        {
            compressSubm( TestUtils.createRelationshipSubmission(), 0 );
        }
        catch ( Exception e )
        {
            assertEquals( e.getClass(), SMSCompressionException.class );
            assertEquals( e.getMessage(), "Version 0 of RelationshipSMSSubmission is not supported" );
            return;
        }

        Assert.fail( "Expected unknown version exception not found" );
    }

    @Test
    public void testWriteFutureVersionRelationship()
    {
        SMSSubmission subm = TestUtils.createRelationshipSubmission();
        int futureVer = subm.getCurrentVersion() + 1;

        try
        {
            compressSubm( subm, futureVer );
        }
        catch ( Exception e )
        {
            assertEquals( e.getClass(), SMSCompressionException.class );
            assertEquals( e.getMessage(),
                String.format( "Version %d of RelationshipSMSSubmission is not supported", futureVer ) );
            return;
        }

        Assert.fail( "Expected unknown version exception not found" );
    }
}
