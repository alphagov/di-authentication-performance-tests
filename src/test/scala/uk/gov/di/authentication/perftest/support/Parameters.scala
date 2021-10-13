package uk.gov.di.authentication.perftest.support

object Parameters {
  def doubleParameter(propertyName: String, default: Double): Double =
    try {
      System.getProperty(propertyName).toDouble
    } catch { case _ => default }
}
