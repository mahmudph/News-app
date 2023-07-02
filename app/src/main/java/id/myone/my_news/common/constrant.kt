/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.common



object Constraint {
    const val HOST_URL = "https://newsapi.org/"
    const val PAGER_SIZE = 10
    const val ARTICLE_TABLE = "tbl_articles"
    const val PAGING_EXAMPLE_DB_NAME = "db_paging_example"
    const val ARTICLE_REMOTE_KEY_TABLE = "tbl_article_remote_keys"
    const val FAVORITE_ARTICLE_TABLE = "tbl_favorite"

    object Config {
        const val newsLanguage = "NEWS_LANGUAGE"
        const val sendEmailVerification = "SHOULD_SEND_EMAIL_VERIFICATION"
        const val shouldVerifiedAccount = "SHOULD_VERIFIED_ACCOUNT_IN_LOGIN"
        const val workManagerIntervalInHour = "WORK_MANAGER_INTERVAL_IN_HOUR"
        const val deeplinkHostUri = "DEEP_LINK_HOST"
    }

    object Analytic {
        /**
         * login type
         */
        const val login = "login"
        const val googleSignIn = "google-sign-in"
        const val emailPasswordCredential = "email-password-credential"

        const val changeNewsLanguage = "change-language-news"
        const val forgotPassword = "forgot-password"
        const val addToFavorite = "add-favorite-news"
    }

    const val defaultAvatarUri = "https://e7.pngegg.com/pngimages/799/987/png-clipart-computer-icons-avatar-icon-design-avatar-heroes-computer-wallpaper-thumbnail.png"
}