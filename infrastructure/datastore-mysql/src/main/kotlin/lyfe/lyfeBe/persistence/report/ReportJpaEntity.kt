package lyfe.lyfeBe.persistence.report

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportTarget
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(name = "report")
class ReportJpaEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    val reportTarget: ReportTarget,

    @field:NotNull
    val reportTargetId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", foreignKey = ForeignKey(name = "fk_reporter_id"))
    @field:NotNull
    val reporter: UserJpaEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id", foreignKey = ForeignKey(name = "fk_reported_user_id"))
    @field:NotNull
    val reportedUser : UserJpaEntity,

    @Embedded
    val cancellation: Cancellation = Cancellation(),

    @Embedded
    val baseEntity: BaseEntity = BaseEntity()

) {
    fun cancelReport() {
        cancellation.cancel(Instant.now())
    }

    fun toDomain(): Report =
        Report(
            id = id,
            reportTarget = reportTarget,
            reportTargetId = reportTargetId,
            reporter = reporter.toDomain(),
            reportedUser = reportedUser.toDomain(),
            isCanceled = cancellation.isCanceled,
            canceledAt = cancellation.canceledAt,
            createdAt = baseEntity.createdAt,
            updatedAt = baseEntity.updatedAt
        )

    companion object {
        fun from(report: Report): ReportJpaEntity =
            ReportJpaEntity(
                id = report.id,
                reportTarget = report.reportTarget,
                reportTargetId = report.reportTargetId,
                reporter = UserJpaEntity.from(report.reporter),
                reportedUser = UserJpaEntity.from(report.reportedUser),
                cancellation = Cancellation(
                    isCanceled = report.isCanceled,
                    canceledAt = report.canceledAt
                ),
                baseEntity = BaseEntity(
                    createdAt = report.createdAt,
                    updatedAt = report.updatedAt
                )
            )
    }
}

@Embeddable
class Cancellation(
    var isCanceled: Boolean = false,
    var canceledAt: Instant? = null
) {
    fun cancel(whenCanceled: Instant) {
        if (!isCanceled) {
            isCanceled = true
            canceledAt = whenCanceled
        }
    }
}