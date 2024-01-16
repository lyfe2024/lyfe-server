//package lyfe.lyfeBe.persistence.feedback
//
//import lyfe.lyfeBe.feedback.Feedback
//import lyfe.lyfeBe.persistence.user.UserMapper
//
//object FeedbackMapper {
//
//    fun mapToDomainEntity(feedback: FeedbackJpaEntity): Feedback =
//        Feedback(
//            id = feedback.id,
//            content = feedback.content,
//            checked = feedback.checked,
//            createdAt = feedback.createdAt,
//            user = UserMapper.mapToDomainEntity(feedback.user),
//        )
//
//    fun mapToJpaEntity(feedback: Feedback): FeedbackJpaEntity =
//        FeedbackJpaEntity(
//            id = feedback.id,
//            content = feedback.content,
//            checked = feedback.checked,
//            createdAt = feedback.createdAt,
//            user = UserMapper.mapToJpaEntity(feedback.user),
//        )
//}