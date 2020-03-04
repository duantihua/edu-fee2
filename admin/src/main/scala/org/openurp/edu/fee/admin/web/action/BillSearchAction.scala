/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.fee.admin.web.action

import org.beangle.data.dao.{LimitQuery, OqlBuilder, QueryPage}
import org.beangle.data.transfer.exporter.ExportSetting
import org.beangle.webmvc.api.annotation.ignore
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.code.edu.model.EducationLevel
import org.openurp.edu.base.web.ProjectSupport
import org.openurp.edu.fee.model.{Bill, FeeType}

class BillSearchAction extends RestfulAction[Bill] with ProjectSupport {

  override def indexSetting(): Unit = {
    put("feeTypes", getCodes(classOf[FeeType]))
    put("levels", getCodes(classOf[EducationLevel]))
    put("currentSemester", getCurrentSemester)
  }

  override protected def getQueryBuilder: OqlBuilder[Bill] = {
    val query = super.getQueryBuilder
    getBoolean("paid") foreach { payed =>
      if (payed) {
        query.where("bill.payed > 0")
      } else {
        query.where("bill.payed <= 0")
      }
    }
    query
  }

  @ignore
  override def configExport(setting: ExportSetting): Unit = {
    val query = getQueryBuilder.limit(1, 500)
    setting.context.put("items", new QueryPage(query.build().asInstanceOf[LimitQuery[Bill]], entityDao))
  }
}
