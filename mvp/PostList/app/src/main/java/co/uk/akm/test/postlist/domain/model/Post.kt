package co.uk.akm.test.postlist.domain.model

/**
 * This class is not necessary. It is used simply to illustrate the principle of converting a data
 * entity into a domain model classes. In a more complex application, unlike our case, these two
 * classes could be different.
 */
data class Post(val id: Int, val title: String, val userId: Int)