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

import org.apache.commons.collections.CollectionUtils
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.code.edu.model.EducationLevel
import org.openurp.edu.base.code.model.CourseType
import org.openurp.edu.base.web.ProjectSupport
import org.openurp.edu.fee.model.CreditFeeDefault

class CreditFeeDefaultAction extends RestfulAction[CreditFeeDefault] with ProjectSupport {

	override def indexSetting(): Unit = {
		put("levels", getCodes(classOf[EducationLevel]))
		super.indexSetting()
	}

	override def editSetting(entity: CreditFeeDefault): Unit = {
		put("levels", getCodes(classOf[EducationLevel]))
		put("courseTypes", getCodes(classOf[CourseType]))
		super.editSetting(entity)
	}

	def checkAjax: View = {
		val spanId = intId("span")
		val builder = OqlBuilder.from(classOf[CreditFeeDefault], "cfd")
		getInt("id").foreach(id => {
			builder.where("cfd.id != :id", id)
		})
		builder.where("cfd.level.id = :spanId", spanId)
		intId("type") match {
			case typeId => builder.where("cfd.courseType.id is null or cfd.courseType.id = :typeId", typeId)
		}
		val a = entityDao.search(builder)
		put("isOk", entityDao.search(builder).isEmpty)
		forward()
	}

	def print: View = {
		put("creditFeeDefaults", entityDao.getAll(classOf[CreditFeeDefault]))
		forward()
	}
}