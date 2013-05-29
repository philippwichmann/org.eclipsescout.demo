/*******************************************************************************
 * Copyright (c) 2013 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.minicrm.server.services;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.demo.minicrm.shared.services.IStandardOutlineService;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.outlines.pages.searchform.CompanySearchFormData;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.outlines.pages.searchform.PersonSearchFormData;

public class StandardOutlineService extends AbstractService implements IStandardOutlineService {

  @Override
  public Object[][] getCompanyTableData(CompanySearchFormData formData) throws ProcessingException {
    StringBuilder statement = new StringBuilder();
    statement.append(
        "SELECT COMPANY_NR, " +
            "       SHORT_NAME, " +
            "       NAME, " +
            "       TYPE_UID " +
            " FROM COMPANY " +
            " WHERE 1 = 1 ");
    if (!StringUtility.isNullOrEmpty(formData.getShortName().getValue())) {
      statement.append("AND UPPER(SHORT_NAME) LIKE UPPER(:shortName || '%') ");
    }
    if (!StringUtility.isNullOrEmpty(formData.getName().getValue())) {
      statement.append("AND UPPER(NAME) LIKE UPPER(:name || '%')");
    }
    return SQL.select(statement.toString(), formData);
  }

  @Override
  public Object[][] getPersonTableData(PersonSearchFormData formData) throws ProcessingException {

    StringBuilder statement = new StringBuilder();
    statement.append("SELECT PERSON_NR, LAST_NAME, FIRST_NAME FROM PERSON WHERE 1=1 ");

    if (!StringUtility.isNullOrEmpty(formData.getFirstName().getValue())) {
      statement.append("AND UPPER(FIRST_NAME) LIKE UPPER(:firstName || '%') ");
    }
    if (!StringUtility.isNullOrEmpty(formData.getLastName().getValue())) {
      statement.append("AND UPPER(LAST_NAME) LIKE UPPER(:lastName || '%') ");
    }
    if (formData.getEmployer().getValue() != null) {
      statement.append("AND COMPANY_NR = :employer ");
    }
    else if (formData.getEmployerType().getValue() != null) {
      statement.append("AND COMPANY_NR IN (SELECT COMPANY_NR FROM COMPANY WHERE TYPE_UID = :employerType) ");
    }
    return SQL.select(statement.toString(), formData);
  }
}
