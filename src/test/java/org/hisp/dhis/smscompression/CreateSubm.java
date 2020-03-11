package org.hisp.dhis.smscompression;

import java.util.ArrayList;

import org.hisp.dhis.smscompression.SMSConsts.SMSEnrollmentStatus;
import org.hisp.dhis.smscompression.SMSConsts.SMSEventStatus;
import org.hisp.dhis.smscompression.models.AggregateDatasetSMSSubmission;
import org.hisp.dhis.smscompression.models.DeleteSMSSubmission;
import org.hisp.dhis.smscompression.models.EnrollmentSMSSubmission;
import org.hisp.dhis.smscompression.models.GeoPoint;
import org.hisp.dhis.smscompression.models.RelationshipSMSSubmission;
import org.hisp.dhis.smscompression.models.SMSAttributeValue;
import org.hisp.dhis.smscompression.models.SMSDataValue;
import org.hisp.dhis.smscompression.models.SimpleEventSMSSubmission;
import org.hisp.dhis.smscompression.models.TrackerEventSMSSubmission;

public class CreateSubm
{
    public static DeleteSMSSubmission createDeleteSubmission()
    {
        DeleteSMSSubmission subm = new DeleteSMSSubmission();

        subm.setUserID( "GOLswS44mh8" ); // Tom Wakiki (system)
        subm.setEvent( "Jpr20TLJ7Z1" ); // Generated UID of test event
        subm.setSubmissionID( 1 );

        return subm;
    }

    public static RelationshipSMSSubmission createRelationshipSubmission()
    {
        RelationshipSMSSubmission subm = new RelationshipSMSSubmission();

        subm.setUserID( "GOLswS44mh8" ); // Tom Wakiki (system)
        subm.setRelationshipType( "XdP5nraLPZ0" ); // Sibling_a-to-b_(Person-Person)
        subm.setRelationship( "uf3svrmpzOj" ); // Generated UID for new
                                               // relationship
        subm.setFrom( "qv0j4JBXQX0" ); // Gloria Murray (Person)
        subm.setTo( "LSEjy8nA3kY" ); // Jerald Wilson (Person)
        subm.setSubmissionID( 1 );

        return subm;
    }

    public static SimpleEventSMSSubmission createSimpleEventSubmission()
    {
        SimpleEventSMSSubmission subm = new SimpleEventSMSSubmission();

        subm.setUserID( "GOLswS44mh8" ); // Tom Wakiki (system)
        subm.setOrgUnit( "DiszpKrYNg8" ); // Ngelehun CHC
        subm.setEventProgram( "lxAQ7Zs9VYR" ); // Antenatal Care Visit
        subm.setEventStatus( SMSEventStatus.COMPLETED );
        subm.setAttributeOptionCombo( "HllvX50cXC0" ); // Default catOptionCombo
        subm.setEvent( "l7M1gUFK37v" ); // New UID
        subm.setEventDate( TestUtils.getNowWithoutMillis() );
        subm.setDueDate( TestUtils.getNowWithoutMillis() );
        subm.setCoordinates( new GeoPoint( 8.4844694f, -13.2364332f ) );
        ArrayList<SMSDataValue> values = new ArrayList<>();
        values.add( new SMSDataValue( "HllvX50cXC0", "sWoqcoByYmD", "true" ) ); // WHOMCH
                                                                                // Smoking
        values.add( new SMSDataValue( "HllvX50cXC0", "Ok9OQpitjQr", "false" ) ); // WHOMCH
                                                                                 // Smoking
                                                                                 // cessation
                                                                                 // counselling
                                                                                 // provided
        values.add( new SMSDataValue( "HllvX50cXC0", "vANAXwtLwcT", "14" ) ); // WHOMCH
                                                                              // Hemoglobin
                                                                              // value
        subm.setValues( values );
        subm.setSubmissionID( 1 );

        return subm;
    }

    public static AggregateDatasetSMSSubmission createAggregateDatasetSubmission()
    {
        AggregateDatasetSMSSubmission subm = new AggregateDatasetSMSSubmission();

        subm.setUserID( "GOLswS44mh8" ); // Tom Wakiki (system)
        subm.setOrgUnit( "DiszpKrYNg8" ); // Ngelehun CHC
        subm.setDataSet( "Nyh6laLdBEJ" ); // IDSR Weekly
        subm.setComplete( true );
        subm.setAttributeOptionCombo( "HllvX50cXC0" );
        subm.setPeriod( "2019W16" );
        ArrayList<SMSDataValue> values = new ArrayList<>();
        values.add( new SMSDataValue( "HllvX50cXC0", "UsSUX0cpKsH", "0" ) ); // Cholera
        values.add( new SMSDataValue( "HllvX50cXC0", "HS9zqaBdOQ4", "-65535" ) ); // Plague
        values.add( new SMSDataValue( "HllvX50cXC0", "noIzB569hTM", "12345678" ) ); // Yellow
                                                                                    // Fever
        values.add( new SMSDataValue( "HllvX50cXC0", "vq2qO3eTrNi", "-24.5" ) ); // Malaria
        values.add( new SMSDataValue( "HllvX50cXC0", "YazgqXbizv1", "0.12345678" ) ); // Measles
        subm.setValues( values );
        subm.setSubmissionID( 1 );

        return subm;
    }

    public static EnrollmentSMSSubmission createEnrollmentSubmission()
    {
        EnrollmentSMSSubmission subm = new EnrollmentSMSSubmission();

        subm.setUserID( "GOLswS44mh8" ); // Tom Wakiki (system)
        subm.setOrgUnit( "DiszpKrYNg8" ); // Ngelehun CHC
        subm.setTrackerProgram( "IpHINAT79UW" ); // Child Programme
        subm.setTrackedEntityType( "nEenWmSyUEp" ); // Person
        subm.setTrackedEntityInstance( "T2bRuLEGoVN" ); // Newly generated UID
        subm.setEnrollment( "p7M1gUFK37W" ); // Newly generated UID
        subm.setEnrollmentDate( TestUtils.getNowWithoutMillis() );
        subm.setIncidentDate( TestUtils.getNowWithoutMillis() );
        subm.setCoordinates( new GeoPoint( 8.4844694f, -13.2364332f ) );
        subm.setEnrollmentStatus( SMSEnrollmentStatus.ACTIVE );
        ArrayList<SMSAttributeValue> values = new ArrayList<>();
        values.add( new SMSAttributeValue( "w75KJ2mc4zz", "Harold" ) ); // First
                                                                        // Name
        values.add( new SMSAttributeValue( "zDhUuAYrxNC", "Smith" ) ); // Last
                                                                       // Name
        values.add( new SMSAttributeValue( "FO4sWYJ64LQ", "Sydney" ) ); // City
        values.add( new SMSAttributeValue( "VqEFza8wbwA", "The Opera House" ) ); // Address
        values.add( new SMSAttributeValue( "lZGmxYbs97q", "987123" ) ); // Unique
                                                                        // ID
        subm.setValues( values );
        subm.setSubmissionID( 1 );

        subm.setEvents( TestUtils.createEventList() );

        return subm;
    }

    public static TrackerEventSMSSubmission createTrackerEventSubmission()
    {
        TrackerEventSMSSubmission subm = new TrackerEventSMSSubmission();

        subm.setUserID( "GOLswS44mh8" ); // Tom Wakiki (system)
        subm.setOrgUnit( "DiszpKrYNg8" ); // Ngelehun CHC
        subm.setProgramStage( "A03MvHHogjR" ); // Birth
        subm.setAttributeOptionCombo( "HllvX50cXC0" ); // Default catOptionCombo
        subm.setEnrollment( "DacGG5vK1K6" ); // Test Person
        subm.setEvent( "r7M1gUFK37v" ); // New UID
        subm.setEventStatus( SMSEventStatus.COMPLETED );
        subm.setEventDate( TestUtils.getNowWithoutMillis() );
        subm.setDueDate( TestUtils.getNowWithoutMillis() );
        subm.setCoordinates( new GeoPoint( 8.4844694f, -13.2364332f ) );
        ArrayList<SMSDataValue> values = new ArrayList<>();
        values.add( new SMSDataValue( "HllvX50cXC0", "a3kGcGDCuk6", "10" ) ); // Apgar
                                                                              // score

        values.add( new SMSDataValue( "HllvX50cXC0", "wQLfBvPrXqq", "Others" ) ); // ARV
                                                                                  // at
                                                                                  // birth
        values.add( new SMSDataValue( "HllvX50cXC0", "X8zyunlgUfM", "Exclusive" ) ); // Infant
                                                                                     // feeding
        subm.setValues( values );
        subm.setSubmissionID( 1 );

        return subm;
    }
}
