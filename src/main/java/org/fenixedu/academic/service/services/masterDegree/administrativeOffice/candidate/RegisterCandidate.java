/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Core.
 *
 * FenixEdu Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Core.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.academic.service.services.masterDegree.administrativeOffice.candidate;

import static org.fenixedu.academic.predicate.AccessControl.check;

import org.fenixedu.academic.domain.candidacy.CandidacySituationType;
import org.fenixedu.academic.domain.student.Registration;
import org.fenixedu.academic.domain.util.workflow.StateBean;
import org.fenixedu.academic.domain.util.workflow.StateMachine;
import org.fenixedu.academic.dto.administrativeOffice.candidacy.RegisterCandidacyBean;
import org.fenixedu.academic.predicate.RolePredicates;
import org.joda.time.YearMonthDay;

import pt.ist.fenixframework.Atomic;

public class RegisterCandidate {

    @Atomic
    public static void run(RegisterCandidacyBean candidacyBean) {
        check(RolePredicates.MASTER_DEGREE_ADMINISTRATIVE_OFFICE_PREDICATE);

        StateMachine.execute(candidacyBean.getCandidacy().getActiveCandidacySituation(), new StateBean(
                CandidacySituationType.REGISTERED.name()));

        final Registration registration = candidacyBean.getCandidacy().getRegistration();
        registration.setStartDate(candidacyBean.getStartDate() != null ? candidacyBean.getStartDate() : new YearMonthDay());
        registration.setEnrolmentModelForCurrentExecutionYear(candidacyBean.getEnrolmentModel());
    }

}