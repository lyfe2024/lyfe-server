package lyfe.lyfeBe.domain.user.model

import jakarta.persistence.*
import lyfe.lyfeBe.common.model.BaseEntity
import lyfe.lyfeBe.domain.feedback.model.Feedback
import lyfe.lyfeBe.domain.notification.model.NotificationHistory
import lyfe.lyfeBe.domain.picture.model.PictureComment
import lyfe.lyfeBe.domain.picture.model.PictureWhisky
import lyfe.lyfeBe.domain.picture.model.TopicPicture
import lyfe.lyfeBe.domain.worry.model.TopicWorry
import lyfe.lyfeBe.domain.worry.model.WorryComment
import lyfe.lyfeBe.domain.worry.model.WorryWhisky
import java.time.Instant

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long = 0,
    var email: String,
    var hashedPassword: String,
    var nickname: String,
    var notificationConsent: Boolean,
    var fcmRegistration: Boolean,
    var profileImage: String? = null,
    var withdrawnAt: Instant? = null,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var feedbackList: MutableList<Feedback> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var notificationHistoryList: MutableList<NotificationHistory> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var topicPictureList: MutableList<TopicPicture> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var pictureCommentList: MutableList<PictureComment> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var pictureWhiskyList: MutableList<PictureWhisky> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var topicWorryList: MutableList<TopicWorry> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var worryCommentList: MutableList<WorryComment> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var worryWhiskyList: MutableList<WorryWhisky> = mutableListOf(),

    ) : BaseEntity() {
}