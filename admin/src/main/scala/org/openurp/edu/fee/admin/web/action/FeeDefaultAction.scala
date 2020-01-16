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

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.code.edu.model.EducationLevel
import org.openurp.edu.base.model.Major
import org.openurp.edu.base.web.ProjectSupport
import org.openurp.edu.fee.model.{FeeDefault, FeeType}

class FeeDefaultAction extends RestfulAction[FeeDefault] with ProjectSupport {

	override def indexSetting(): Unit = {
		put("levels", getCodes(classOf[EducationLevel]))
		put("departments", getDeparts)
		put("majors", findInProject(classOf[Major]))
		super.indexSetting()
	}

	override def editSetting(entity: FeeDefault): Unit = {
		put("feeTypes", getCodes(classOf[FeeType]))
		put("levels", getCodes(classOf[EducationLevel]))
		put("departments", getDeparts)
		put("majors", findInProject(classOf[Major]))
		super.editSetting(entity)
	}

	/**
	 * 打印
	 *
	 * @return
	 */
	def printReview: View = {
		put("feeDefaults", entityDao.search(OqlBuilder.from(classOf[FeeDefault], "feeDefault")))
		forward()
	}

}
