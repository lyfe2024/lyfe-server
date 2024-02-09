package initTest.lyfe.lyfeBe.test.report.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportCreate
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus

class ReportTest :BehaviorSpec({
    Given("ReportCreate, User, ReportTarget 객체가 초기화되었을 때") {
        val reportCreate = ReportCreate(
            ReportTarget.BOARD,
            1L,
        )
        val user = User(
            1L,
            "testName",
            "testEmail",
            "testPassword",
            "",
            SocialType.GOOGLE,
            "",
            true,
            true,
            Role.USER,
            UserStatus.ACTIVE
        )
        val reportedUser = User(
            1L,
            "testName",
            "testEmail",
            "testPassword",
            "",
            SocialType.GOOGLE,
            "",
            true,
            true,
            Role.USER,
            UserStatus.ACTIVE
        )
        When("ReportCreate, User, ReportTarget 객체를 사용하여 새 Report 객체를 생성했을 때") {
            val report = Report.from(reportCreate, user, reportedUser)
            Then("생성된 Report 객체의 속성이 reportCreate와 일치") {
                report.reportTarget shouldBe reportCreate.reportTarget
                report.reportTargetId shouldBe reportCreate.reportTargetId
            }
        }
    }
})