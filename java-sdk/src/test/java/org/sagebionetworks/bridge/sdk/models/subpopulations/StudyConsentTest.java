package org.sagebionetworks.bridge.sdk.models.subpopulations;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class StudyConsentTest {
    
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(StudyConsent.class).suppress(Warning.NONFINAL_FIELDS).allFieldsShouldBeUsed().verify();
    }
    
}
