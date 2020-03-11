package org.hisp.dhis.smscompression;

import java.util.ArrayList;

import org.hisp.dhis.smscompression.SMSConsts.SMSEventStatus;
import org.hisp.dhis.smscompression.models.EnrollmentSMSSubmission;
import org.hisp.dhis.smscompression.models.SMSAttributeValue;
import org.hisp.dhis.smscompression.models.SMSDataValue;
import org.hisp.dhis.smscompression.models.SimpleEventSMSSubmission;
import org.hisp.dhis.smscompression.models.TrackerEventSMSSubmission;

public class CreateSubmV1
{
    public static EnrollmentSMSSubmission createEnrollmentSubmissionV1()
    {
        EnrollmentSMSSubmission subm = new EnrollmentSMSSubmission();

        subm.setUserID( "GOLswS44mh8" ); // Tom Wakiki (system)
        subm.setOrgUnit( "DiszpKrYNg8" ); // Ngelehun CHC
        subm.setTrackerProgram( "IpHINAT79UW" ); // Child Programme
        subm.setTrackedEntityType( "nEenWmSyUEp" ); // Person
        subm.setTrackedEntityInstance( "T2bRuLEGoVN" ); // Newly generated UID
        subm.setEnrollment( "p7M1gUFK37W" ); // Newly generated UID
        subm.setEnrollmentDate( TestUtils.getNowWithoutMillis() );
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

        return subm;
    }

    public static SimpleEventSMSSubmission createSimpleEventSubmissionV1()
    {
        SimpleEventSMSSubmission subm = new SimpleEventSMSSubmission();

        subm.setUserID( "GOLswS44mh8" ); // Tom Wakiki (system)
        subm.setOrgUnit( "DiszpKrYNg8" ); // Ngelehun CHC
        subm.setEventProgram( "lxAQ7Zs9VYR" ); // Antenatal Care Visit
        subm.setEventStatus( SMSEventStatus.COMPLETED );
        subm.setAttributeOptionCombo( "HllvX50cXC0" ); // Default catOptionCombo
        subm.setEvent( "l7M1gUFK37v" ); // New UID
        subm.setEventDate( TestUtils.getNowWithoutMillis() );
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

    public static TrackerEventSMSSubmission createTrackerEventSubmissionV1()
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
