package pea.example.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import pea.app.gatling.PeaSimulation

import scala.concurrent.duration._

class BasicSimulation extends PeaSimulation {

  override val description: String =
    """
      |这个是官方提供的一个示例, 就是由十几个请求组成的一个场景. 并发数为1.
      |""".stripMargin

  val httpProtocol = http
    .baseUrl("http://computer-database.gatling.io") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Scenario Name") // A scenario is a chain of requests and pauses
    .exec(http("request_1")
      .get("/"))
    .pause(7) // Note that Gatling has recorder real time pauses
    .exec(http("request_2")
      .get("/computers?f=macbook"))
    .pause(2)
    .exec(http("request_3")
      .get("/computers/6"))
    .pause(3)
    .exec(http("request_4")
      .get("/"))
    .pause(2)
    .exec(http("request_5")
      .get("/computers?p=1"))
    .pause(670 milliseconds)
    .exec(http("request_6")
      .get("/computers?p=2"))
    .pause(629 milliseconds)
    .exec(http("request_7")
      .get("/computers?p=3"))
    .pause(734 milliseconds)
    .exec(http("request_8")
      .get("/computers?p=4"))
    .pause(5)
    .exec(http("request_9")
      .get("/computers/new"))
    .pause(1)
    .exec(http("request_10") // Here's an example of a POST request
      .post("/computers")
      .formParam("""name""", """Beautiful Computer""") // Note the triple double quotes: used in Scala for protecting a whole chain of characters (no need for backslash)
      .formParam("""introduced""", """2012-05-31""")
      .formParam("""discontinued""", """""")
      .formParam("""company""", """37"""))

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
