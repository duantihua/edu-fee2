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
package org.openurp.edu.fee.model

import org.beangle.data.orm.MappingModule

class DefaultMapping extends MappingModule {

	def binding(): Unit = {
		defaultIdGenerator("auto_increment")

		bind[FeeDefault]

		bind[CreditFeeDefault]

		bind[FeeType]

		bind[Bill] declare { e =>
			index("idx_bill_std", false, e.std)
		}

		bind[Order] declare { e =>
			e.code is(length(50), unique)
			e.payUrl is length(500)
			index("idx_order_bill_id", true, e.bill)
		}
	}

}
