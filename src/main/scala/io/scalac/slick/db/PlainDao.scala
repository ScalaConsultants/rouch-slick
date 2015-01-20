package io.scalac.slick.db

import io.scalac.slick.db.Dao.SaleRecord

import scala.slick.driver.JdbcDriver.backend.Database.dynamicSession
import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation

object PlainDao extends Dao with DbProvider {

  def fetchSales(minTotal: BigDecimal): Seq[SaleRecord] = db.withDynSession {
    sql"""SELECT purchaser.name, supplier.name, product.name, sale.total
          FROM sale JOIN purchaser ON sale.purchaser_id = purchaser.id
                    JOIN product ON sale.product_id = product.id
                    JOIN supplier ON product.supplier_id = supplier.id
          WHERE sale.total >= $minTotal"""
    .as[(String, String, String, BigDecimal)].list.map(SaleRecord.tupled)
  }
}
